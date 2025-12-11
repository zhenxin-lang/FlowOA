package org.openoa.engine.bpmnconf.service.flowcontrol;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.RuntimeServiceImpl;
import org.activiti.engine.impl.interceptor.CommandExecutor;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openoa.base.util.ProcessDefinitionUtils;
import org.openoa.base.util.SpringBeanUtils;
import org.openoa.common.mapper.BpmVariableMultiplayerMapper;
import org.openoa.common.service.BpmVariableMultiplayerServiceImpl;
import org.openoa.engine.bpmnconf.service.impl.ActHiTaskinstServiceImpl;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DefaultTaskFlowControlServiceTest {

    @Mock
    private ProcessEngine processEngine;
    @Mock
    private RuntimeServiceImpl runtimeService;
    @Mock
    private TaskService taskService;
    @Mock
    private BpmVariableMultiplayerServiceImpl bpmVariableMultiplayerService;
    @Mock
    private BpmVariableMultiplayerMapper bpmVariableMultiplayerMapper;
    @Mock
    private ProcessInstanceQuery processInstanceQuery;
    @Mock
    private ProcessInstance processInstance;
    @Mock
    private CommandExecutor commandExecutor;
    @Mock
    private TaskQuery taskQuery;
    @Mock
    private ActHiTaskinstServiceImpl actHiTaskinstService;

    private DefaultTaskFlowControlService defaultTaskFlowControlService;

    private final String PROCESS_ID = "proc-inst-123";
    private final String PROCESS_DEF_ID = "proc-def-123";

    @BeforeEach
    void setUp() {
        when(processEngine.getRuntimeService()).thenReturn(runtimeService);
        when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQuery);
        when(processInstanceQuery.processInstanceId(anyString())).thenReturn(processInstanceQuery);
        when(processInstanceQuery.singleResult()).thenReturn(processInstance);
        when(processInstance.getProcessDefinitionId()).thenReturn(PROCESS_DEF_ID);
    }

    @Test
    void testConstructor_Success() {
        try (MockedStatic<ProcessDefinitionUtils> processDefinitionUtilsMock = Mockito.mockStatic(ProcessDefinitionUtils.class)) {
            ProcessDefinitionEntity processDefinitionEntity = mock(ProcessDefinitionEntity.class);
            processDefinitionUtilsMock.when(() -> ProcessDefinitionUtils.getProcessDefinition(processEngine, PROCESS_DEF_ID))
                    .thenReturn(processDefinitionEntity);

            defaultTaskFlowControlService = new DefaultTaskFlowControlService(processEngine, PROCESS_ID, bpmVariableMultiplayerService);
            assertNotNull(defaultTaskFlowControlService);
        }
    }

    @Test
    void testMoveTo_Success() throws Exception {
        try (MockedStatic<ProcessDefinitionUtils> processDefinitionUtilsMock = Mockito.mockStatic(ProcessDefinitionUtils.class);
             MockedStatic<SpringBeanUtils> springBeanUtilsMock = Mockito.mockStatic(SpringBeanUtils.class)) {

            // --- Constructor Setup ---
            ProcessDefinitionEntity processDefinitionEntity = mock(ProcessDefinitionEntity.class);
            processDefinitionUtilsMock.when(() -> ProcessDefinitionUtils.getProcessDefinition(processEngine, PROCESS_DEF_ID))
                    .thenReturn(processDefinitionEntity);
            defaultTaskFlowControlService = new DefaultTaskFlowControlService(processEngine, PROCESS_ID, bpmVariableMultiplayerService);

            // --- Method Call Setup ---
            when(processEngine.getTaskService()).thenReturn(taskService);
            when(taskService.createTaskQuery()).thenReturn(taskQuery);
            when(taskQuery.processInstanceId(PROCESS_ID)).thenReturn(taskQuery);
            when(taskQuery.active()).thenReturn(taskQuery);

            TaskEntity task = mock(TaskEntity.class);
            when(task.getTaskDefinitionKey()).thenReturn("current-node");
            when(task.getProcessDefinitionId()).thenReturn(PROCESS_DEF_ID);
            when(task.getId()).thenReturn("task-1");
            when(task.getExecutionId()).thenReturn("exec-1");
            List<Task> tasks = new ArrayList<>();
            tasks.add(task);
            when(taskQuery.list()).thenReturn(tasks);

            ActivityImpl activity = mock(ActivityImpl.class);
            when(activity.getId()).thenReturn("target-node");
            processDefinitionUtilsMock.when(() -> ProcessDefinitionUtils.getActivity(processEngine, PROCESS_DEF_ID, "target-node"))
                    .thenReturn(activity);

            // moveTov2 Logic Mocks
            Map<String, Object> variables = new HashMap<>();
            variables.put("processNumber", "PN-001");
            variables.put("formCode", "FORM-001");
            variables.put("startUser", "user-1");
            when(taskService.getVariables("task-1")).thenReturn(variables);

            when(bpmVariableMultiplayerService.queryVariableNameByElementId("PN-001", "target-node"))
                    .thenReturn("assigneeList");
            when(bpmVariableMultiplayerService.getBaseMapper()).thenReturn(bpmVariableMultiplayerMapper);
            List<org.openoa.base.vo.BaseIdTranStruVo> assignees = new ArrayList<>();
            org.openoa.base.vo.BaseIdTranStruVo assigneeVo = new org.openoa.base.vo.BaseIdTranStruVo();
            assigneeVo.setId("user-1");
            assignees.add(assigneeVo);
            when(bpmVariableMultiplayerMapper.getAssigneeByElementId("PN-001", "target-node"))
                    .thenReturn(assignees);

            // Command Executor
            when(runtimeService.getCommandExecutor()).thenReturn(commandExecutor);

            // SpringBeanUtils Mocks
            springBeanUtilsMock.when(() -> SpringBeanUtils.getBean(ActHiTaskinstServiceImpl.class))
                    .thenReturn(actHiTaskinstService);
            when(actHiTaskinstService.queryRecordsByProcInstId(PROCESS_ID)).thenReturn(Collections.emptyList());

            // --- Execution ---
            assertDoesNotThrow(() -> defaultTaskFlowControlService.moveTo("current-node", "target-node"));
        }
    }
}