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
import org.openoa.base.entity.UserEntrust;
import org.openoa.base.util.DateUtil;
import org.openoa.base.util.MultiTenantUtil;
import org.openoa.base.util.SecurityUtils;
import org.openoa.base.vo.BaseIdTranStruVo;
import org.openoa.base.vo.DataVo;
import org.openoa.base.vo.Entrust;
import org.openoa.base.vo.IdsVo;
import org.openoa.engine.bpmnconf.mapper.UserEntrustMapper;
import org.openoa.engine.utils.AFWrappers;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@DisplayName("UserEntrustServiceImpl 单元测试")
class UserEntrustServiceImplTest {

    @Mock
    private UserEntrustMapper userEntrustMapper;

    @InjectMocks
    private UserEntrustServiceImpl userEntrustService;

    @Test
    @DisplayName("测试 getEntrustList - 验证查询逻辑")
    void testGetEntrustList_Success() {
        try (MockedStatic<SecurityUtils> securityUtilsMock = Mockito.mockStatic(SecurityUtils.class)) {
            // Given
            String userId = "user1";
            securityUtilsMock.when(SecurityUtils::getLogInEmpIdSafe).thenReturn(userId);

            List<Entrust> mockList = Collections.singletonList(new Entrust());
            when(userEntrustMapper.getEntrustListNew(userId)).thenReturn(mockList);

            // When
            List<Entrust> result = userEntrustService.getEntrustList();

            // Then
            assertNotNull(result);
            assertEquals(1, result.size());
            verify(userEntrustMapper).getEntrustListNew(userId);
        }
    }

    @Test
    @DisplayName("测试 updateEntrustList - 验证批量新增和更新")
    void testUpdateEntrustList_Success() {
        try (MockedStatic<SecurityUtils> securityUtilsMock = Mockito.mockStatic(SecurityUtils.class);
             MockedStatic<MultiTenantUtil> tenantUtilMock = Mockito.mockStatic(MultiTenantUtil.class)) {

            // Given
            String currentUserName = "TestUser";
            String tenantId = "tenant1";

            securityUtilsMock.when(SecurityUtils::getLogInEmpNameSafe).thenReturn(currentUserName);
            tenantUtilMock.when(MultiTenantUtil::getCurrentTenantId).thenReturn(tenantId);

            DataVo dataVo = new DataVo();
            dataVo.setReceiverId("receiver1");
            dataVo.setSender("sender1");
            
            // Case 1: Update existing (id > 0)
            IdsVo updateItem = new IdsVo();
            updateItem.setId(100);
            
            // Case 2: Insert new (id null, powerId present)
            IdsVo insertItem = new IdsVo();
            insertItem.setPowerId("power1");

            dataVo.setIds(Arrays.asList(updateItem, insertItem));

            // Mock existing record for update
            UserEntrust existingEntrust = new UserEntrust();
            existingEntrust.setId(100);
            existingEntrust.setPowerId("powerOld");
            when(userEntrustMapper.selectById(100)).thenReturn(existingEntrust);

            // When
            userEntrustService.updateEntrustList(dataVo);

            // Then
            // Verify Update
            verify(userEntrustMapper, times(1)).updateById(argThat(entity -> 
                entity.getId().equals(100) &&
                entity.getUpdateUser().equals(currentUserName) &&
                entity.getReceiverId().equals("receiver1")
            ));

            // Verify Insert
            verify(userEntrustMapper, times(1)).insert(argThat(entity -> 
                entity.getPowerId().equals("power1") &&
                entity.getCreateUser().equals(currentUserName) &&
                entity.getTenantId().equals(tenantId)
            ));
        }
    }

    @Test
    @DisplayName("测试 getEntrustEmployeeOnly - 验证委托生效逻辑(时间范围内)")
    void testGetEntrustEmployeeOnly_Effective() {
        try (MockedStatic<AFWrappers> afWrappersMock = Mockito.mockStatic(AFWrappers.class);
             MockedStatic<MultiTenantUtil> tenantUtilMock = Mockito.mockStatic(MultiTenantUtil.class);
             MockedStatic<DateUtil> dateUtilMock = Mockito.mockStatic(DateUtil.class)) {

            // Given
            String empId = "user1";
            String powerId = "power1";
            String tenantId = "tenant1";
            String receiverId = "receiver1";
            String receiverName = "Receiver One";

            tenantUtilMock.when(MultiTenantUtil::getCurrentTenantId).thenReturn(tenantId);
            tenantUtilMock.when(MultiTenantUtil::strictTenantMode).thenReturn(true);

            // Mock Query Wrapper
            LambdaQueryWrapper<UserEntrust> queryWrapper = mock(LambdaQueryWrapper.class);
            afWrappersMock.when(AFWrappers::lambdaTenantQuery).thenReturn(queryWrapper);
            when(queryWrapper.eq(any(), any())).thenReturn(queryWrapper);

            // Mock Data
            UserEntrust entrust = new UserEntrust();
            entrust.setTenantId(tenantId);
            entrust.setReceiverId(receiverId);
            entrust.setReceiverName(receiverName);
            // Set valid time range (cover "now")
            Date now = new Date();
            entrust.setBeginTime(new Date(now.getTime() - 10000)); 
            entrust.setEndTime(new Date(now.getTime() + 10000));

            when(userEntrustMapper.selectList(any())).thenReturn(Collections.singletonList(entrust));

            // Mock DateUtil to return the date as is (since logic uses DateUtil.getDayStart/End)
            // Note: The logic inside service calls DateUtil.getDayStart(u.getBeginTime()).getTime()
            // We just need to ensure the comparisons pass.
            dateUtilMock.when(() -> DateUtil.getDayStart(any())).thenAnswer(i -> i.getArgument(0));
            dateUtilMock.when(() -> DateUtil.getDayEnd(any())).thenAnswer(i -> i.getArgument(0));

            // When
            BaseIdTranStruVo result = userEntrustService.getEntrustEmployeeOnly(empId, "User One", powerId);

            // Then
            assertEquals(receiverId, result.getId());
            assertEquals(receiverName, result.getName());
        }
    }
    
    @Test
    @DisplayName("测试 getEntrustEmployeeOnly - 无委托记录返回本人")
    void testGetEntrustEmployeeOnly_NoEntrust() {
         try (MockedStatic<AFWrappers> afWrappersMock = Mockito.mockStatic(AFWrappers.class);
             MockedStatic<MultiTenantUtil> tenantUtilMock = Mockito.mockStatic(MultiTenantUtil.class)) {
            
            // Given
            String empId = "user1";
            String empName = "User One";
            String powerId = "power1";
            
            tenantUtilMock.when(MultiTenantUtil::getCurrentTenantId).thenReturn("tenant1");
            
             // Mock Query Wrapper
            LambdaQueryWrapper<UserEntrust> queryWrapper = mock(LambdaQueryWrapper.class);
            afWrappersMock.when(AFWrappers::lambdaTenantQuery).thenReturn(queryWrapper);
            when(queryWrapper.eq(any(), any())).thenReturn(queryWrapper);
            
            when(userEntrustMapper.selectList(any())).thenReturn(Collections.emptyList());
            
            // When
            BaseIdTranStruVo result = userEntrustService.getEntrustEmployeeOnly(empId, empName, powerId);
            
            // Then
            assertEquals(empId, result.getId());
            assertEquals(empName, result.getName());
         }
    }
}
