package org.openoa.engine.bpmnconf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openoa.base.entity.BpmFlowrunEntrust;
import org.openoa.base.entity.UserEntrust;
import org.openoa.base.util.MultiTenantUtil;
import org.openoa.base.util.SecurityUtils;
import org.openoa.base.vo.BpmFlowrunEntrustVo;
import org.openoa.engine.bpmnconf.mapper.BpmFlowrunEntrustMapper;
import org.openoa.engine.utils.AFWrappers;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("BpmFlowrunEntrustServiceImpl 单元测试")
class BpmFlowrunEntrustServiceImplTest {

    @Mock
    private BpmFlowrunEntrustMapper bpmFlowrunEntrustMapper;

    @InjectMocks
    private BpmFlowrunEntrustServiceImpl bpmFlowrunEntrustService;

    @Test
    @DisplayName("测试 addFlowrunEntrust - 验证参数组装与插入")
    void testAddFlowrunEntrust_WithParams_Success() {
        try (MockedStatic<MultiTenantUtil> tenantUtilMock = Mockito.mockStatic(MultiTenantUtil.class)) {
            // Given
            String actual = "user1";
            String actualName = "User One";
            String original = "user2";
            String originalName = "User Two";
            String runTaskId = "task1";
            Integer type = 1;
            String procInstId = "proc:123";
            String procKey = "key1";
            String tenantId = "tenant1";

            tenantUtilMock.when(MultiTenantUtil::getCurrentTenantId).thenReturn(tenantId);
            when(bpmFlowrunEntrustMapper.insert(any(BpmFlowrunEntrust.class))).thenReturn(1);

            // When
            bpmFlowrunEntrustService.addFlowrunEntrust(actual, actualName, original, originalName, runTaskId, type, procInstId, procKey);

            // Then
            verify(bpmFlowrunEntrustMapper, times(1)).insert(argThat(entity ->
                    entity.getActual().equals(actual) &&
                            entity.getOriginal().equals(original) &&
                            entity.getRuntaskid().equals(runTaskId) &&
                            entity.getProcDefId().equals(procKey) &&
                            entity.getTenantId().equals(tenantId)
            ));
        }
    }

    @Test
    @DisplayName("测试 getBpmEntrust - 验证有效日期内的委托")
    void testGetBpmEntrust_ValidDate() {
        // Given
        String receiverId = "user1";
        String processKey = "process:1:1";

        UserEntrust mockEntrust = new UserEntrust();
        // Set time range covering "now" (ignoring seconds logic in impl for now as it uses SimpleDateFormat day precision)
        // Implementation logic:
        // 1. Current date (now) is parsed to yyyy-MM-dd
        // 2. Logic compares timestamps.
        // Let's ensure mockEntrust has valid range.
        long now = System.currentTimeMillis();
        mockEntrust.setBeginTime(new Date(now - 86400000L)); // Yesterday
        mockEntrust.setEndTime(new Date(now + 86400000L));   // Tomorrow

        when(bpmFlowrunEntrustMapper.getBpmEntrust(eq(receiverId), eq("process"))).thenReturn(mockEntrust);

        // When
        UserEntrust result = bpmFlowrunEntrustService.getBpmEntrust(receiverId, processKey);

        // Then
        assertNotNull(result);
        assertEquals(mockEntrust, result);
    }

    @Test
    @DisplayName("测试 getBpmEntrust - 无时间限制的委托")
    void testGetBpmEntrust_NoTimeLimit() {
        // Given
        String receiverId = "user1";
        String processKey = "process:1:1";
        UserEntrust mockEntrust = new UserEntrust();
        mockEntrust.setBeginTime(null);
        mockEntrust.setEndTime(null);

        when(bpmFlowrunEntrustMapper.getBpmEntrust(anyString(), anyString())).thenReturn(mockEntrust);

        // When
        UserEntrust result = bpmFlowrunEntrustService.getBpmEntrust(receiverId, processKey);

        // Then
        assertNotNull(result);
    }

    @Test
    @DisplayName("测试 editFlowrunEntrustState - 验证状态更新")
    void testEditFlowrunEntrustState_Success() {
        try (MockedStatic<AFWrappers> afWrappersMock = Mockito.mockStatic(AFWrappers.class);
             MockedStatic<SecurityUtils> securityUtilsMock = Mockito.mockStatic(SecurityUtils.class)) {

            // Given
            String procInstId = "proc:123";
            String currentUserId = "user1";
            securityUtilsMock.when(SecurityUtils::getLogInEmpIdSafe).thenReturn(currentUserId);

            // Mock Query
            LambdaQueryWrapper<BpmFlowrunEntrust> queryWrapper = mock(LambdaQueryWrapper.class);
            afWrappersMock.when(AFWrappers::lambdaTenantQuery).thenReturn(queryWrapper);
            when(queryWrapper.eq(any(), any())).thenReturn(queryWrapper);

            // Mock List
            BpmFlowrunEntrust entrust = new BpmFlowrunEntrust();
            entrust.setId(1);
            entrust.setIsView(0);
            when(bpmFlowrunEntrustMapper.selectList(any())).thenReturn(Collections.singletonList(entrust));

            // When
            bpmFlowrunEntrustService.editFlowrunEntrustState(procInstId);

            // Then
            verify(bpmFlowrunEntrustMapper, times(1)).updateById(argThat(entity -> entity.getIsView() == 1));
        }
    }

    @Test
    @DisplayName("测试 findFlowrunEntrustByProcessInstanceId - 验证查询逻辑")
    void testFindFlowrunEntrustByProcessInstanceId_Success() {
        try (MockedStatic<AFWrappers> afWrappersMock = Mockito.mockStatic(AFWrappers.class)) {
            // Given
            BpmFlowrunEntrustVo vo = new BpmFlowrunEntrustVo();
            vo.setType(1);
            vo.setRuninfoid("run:123");

            // Mock Query
            LambdaQueryWrapper<BpmFlowrunEntrust> queryWrapper = mock(LambdaQueryWrapper.class);
            afWrappersMock.when(AFWrappers::lambdaTenantQuery).thenReturn(queryWrapper);
            when(queryWrapper.eq(any(), any())).thenReturn(queryWrapper);

            when(bpmFlowrunEntrustMapper.selectList(any())).thenReturn(Collections.singletonList(new BpmFlowrunEntrust()));

            // When
            List<BpmFlowrunEntrust> result = bpmFlowrunEntrustService.findFlowrunEntrustByProcessInstanceId(vo);

            // Then
            assertNotNull(result);
            assertEquals(1, result.size());
        }
    }
}
