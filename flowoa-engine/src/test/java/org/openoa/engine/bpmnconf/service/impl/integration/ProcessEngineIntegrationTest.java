package org.openoa.engine.bpmnconf.service.impl.integration;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.junit.jupiter.api.Test;
import org.openoa.engine.conf.engineconfig.ActivitiConfig;
import org.openoa.engine.conf.engineconfig.DataSourceProcessEngineAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = ProcessEngineIntegrationTest.TestConfig.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
public class ProcessEngineIntegrationTest {

    @Configuration
    @Import({
            DataSourceAutoConfiguration.class,
            DataSourceTransactionManagerAutoConfiguration.class,
            DataSourceProcessEngineAutoConfiguration.DataSourceProcessEngineConfiguration.class,
            ActivitiConfig.class
    })
    static class TestConfig {
    }

    @Autowired
    private ProcessEngine processEngine;

    @Autowired
    private RepositoryService repositoryService;

    @Test
    public void testProcessEngineInitialization() {
        assertNotNull(processEngine, "ProcessEngine should be initialized");
        assertNotNull(repositoryService, "RepositoryService should be initialized");
        System.out.println("Process Engine Name: " + processEngine.getName());
    }

    @Test
    public void testDeployProcess() {
        String bpmnXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><definitions xmlns=\"http://www.omg.org/spec/BPMN/20100524/MODEL\" targetNamespace=\"http://www.activiti.org/test\"><process id=\"testProcess\" name=\"Test Process\"><startEvent id=\"start\" /><sequenceFlow id=\"flow1\" sourceRef=\"start\" targetRef=\"end\" /><endEvent id=\"end\" /></process></definitions>";

        Deployment deployment = repositoryService.createDeployment()
                .addString("test.bpmn20.xml", bpmnXml)
                .name("Integration Test Deployment")
                .deploy();

        assertNotNull(deployment.getId(), "Deployment ID should not be null");

        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .deploymentId(deployment.getId())
                .singleResult();
        
        assertNotNull(processDefinition, "Process Definition should be found");
        assertEquals("testProcess", processDefinition.getKey());
        assertEquals("Test Process", processDefinition.getName());
    }
}
