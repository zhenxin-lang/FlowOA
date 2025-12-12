package org.openoa.engine.bpmnconf.service.impl.integration;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mybatis.spring.annotation.MapperScan;
import org.openoa.base.entity.BpmBusiness;
import org.openoa.base.util.SecurityUtils;
import org.openoa.base.vo.BusinessDataVo;
import org.openoa.engine.bpmnconf.mapper.BpmBusinessMapper;
import org.openoa.engine.bpmnconf.service.impl.BpmBusinessServiceImpl;
import org.openoa.engine.utils.AFWrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = BpmBusinessServiceIntegrationTest.TestConfig.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
public class BpmBusinessServiceIntegrationTest {

    // Define a minimal configuration for this test
    @Configuration
    @Import({
            DataSourceAutoConfiguration.class, // Load DataSource
            DataSourceTransactionManagerAutoConfiguration.class, // Load Transaction Manager
            MybatisPlusAutoConfiguration.class, // Load MyBatis Plus
            BpmBusinessServiceImpl.class // Load the Service under test
    })
    @MapperScan("org.openoa.engine.bpmnconf.mapper") // Scan only relevant Mappers
    static class TestConfig {
    }

    @Autowired
    private BpmBusinessServiceImpl bpmBusinessService;

    @Autowired
    private BpmBusinessMapper bpmBusinessMapper;

    @Test
    public void testEditProcessBusiness_RealDB() {
        // Mock SecurityUtils and MultiTenantUtil static methods
        try (MockedStatic<SecurityUtils> securityUtilsMock = Mockito.mockStatic(SecurityUtils.class);
             MockedStatic<org.openoa.base.util.MultiTenantUtil> multiTenantUtilMock = Mockito.mockStatic(org.openoa.base.util.MultiTenantUtil.class)) {
            
            // Mock SecurityUtils
            securityUtilsMock.when(SecurityUtils::getLogInEmpIdSafe).thenReturn("testUser");
            securityUtilsMock.when(SecurityUtils::getLogInEmpNameSafe).thenReturn("Test User");

            // Mock MultiTenantUtil
            multiTenantUtilMock.when(org.openoa.base.util.MultiTenantUtil::getCurrentTenantId).thenReturn("testTenant");
            multiTenantUtilMock.when(org.openoa.base.util.MultiTenantUtil::strictTenantMode).thenReturn(false);

            // Prepare Data
            String businessId = "TEST_BUS_001";
            // ... rest of the test code
            String processKey = "TESTPROCESS";
            String processNumber = "TESTPROCESS_TEST_BUS_001";
    
            BusinessDataVo vo = new BusinessDataVo();
            vo.setBusinessId(businessId);
            vo.setProcessKey(processKey);
            vo.setProcessNumber(processNumber);
    
            // Execute Service Method
            // This should insert a new record into bpm_business table
            boolean result = bpmBusinessService.editProcessBusiness(vo);
            assertTrue(result, "Service method should return true");
    
            // Verify with Mapper
            List<BpmBusiness> list = bpmBusinessMapper.selectList(
                    AFWrappers.<BpmBusiness>lambdaTenantQuery()
                            .eq(BpmBusiness::getProcessCode, processNumber)
            );
    
            assertNotNull(list);
            assertEquals(1, list.size(), "Should find exactly 1 record");
            BpmBusiness inserted = list.get(0);
            assertEquals(businessId, inserted.getBusinessId());
            assertEquals(processKey, inserted.getProcessKey());
            
            // Test idempotency (should not insert again if exists, logic in service)
            boolean result2 = bpmBusinessService.editProcessBusiness(vo);
            assertTrue(result2);
            
            List<BpmBusiness> list2 = bpmBusinessMapper.selectList(
                    AFWrappers.<BpmBusiness>lambdaTenantQuery()
                            .eq(BpmBusiness::getProcessCode, processNumber)
            );
            assertEquals(1, list2.size(), "Should still be 1 record after second call");
        }
    }
}