package org.openoa.engine.bpmnconf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openoa.base.entity.BpmBusiness;
import org.openoa.base.util.SecurityUtils;
import org.openoa.base.vo.BusinessDataVo;
import org.openoa.engine.bpmnconf.mapper.BpmBusinessMapper;
import org.openoa.engine.utils.AFWrappers;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BpmBusinessServiceImplTest {

    @Mock
    private BpmBusinessMapper bpmBusinessMapper;

    @InjectMocks
    private BpmBusinessServiceImpl bpmBusinessService;

    @Test
    void testEditProcessBusiness_New() {
        try (MockedStatic<AFWrappers> afWrappersMock = Mockito.mockStatic(AFWrappers.class);
             MockedStatic<SecurityUtils> securityUtilsMock = Mockito.mockStatic(SecurityUtils.class)) {

            // Mock AFWrappers
            LambdaQueryWrapper<BpmBusiness> queryWrapper = mock(LambdaQueryWrapper.class);
            // Use a raw type cast to avoid generic issues with Mockito's when/thenReturn for static generic methods
            afWrappersMock.when(AFWrappers::lambdaTenantQuery).thenReturn(queryWrapper);
            when(queryWrapper.eq(any(), any())).thenReturn(queryWrapper);

            // Mock SecurityUtils
            securityUtilsMock.when(SecurityUtils::getLogInEmpIdSafe).thenReturn("testUser");
            securityUtilsMock.when(SecurityUtils::getLogInEmpNameSafe).thenReturn("Test User");

            // Mock Mapper
            when(bpmBusinessMapper.selectList(any())).thenReturn(Collections.emptyList());
            when(bpmBusinessMapper.insert(any(BpmBusiness.class))).thenReturn(1);

            // Prepare Data
            BusinessDataVo vo = new BusinessDataVo();
            vo.setBusinessId("123");
            vo.setProcessKey("TEST_KEY");
            vo.setProcessNumber("TEST_KEY_123");

            // Execute
            boolean result = bpmBusinessService.editProcessBusiness(vo);

            // Verify
            assertTrue(result);
            verify(bpmBusinessMapper).insert(any(BpmBusiness.class));
        }
    }

    @Test
    void testGetBpmBusinessCount() {
        try (MockedStatic<AFWrappers> afWrappersMock = Mockito.mockStatic(AFWrappers.class)) {
             // Mock AFWrappers
            LambdaQueryWrapper<BpmBusiness> queryWrapper = mock(LambdaQueryWrapper.class);
            afWrappersMock.when(AFWrappers::lambdaTenantQuery).thenReturn(queryWrapper);
            when(queryWrapper.eq(any(), any())).thenReturn(queryWrapper);
            
            // Mock Mapper
             when(bpmBusinessMapper.selectList(any())).thenReturn(Collections.singletonList(new BpmBusiness()));

             // Execute
             Integer count = bpmBusinessService.getBpmBusinessCount(1);

             // Verify
             assertEquals(1, count);
        }
    }
}
