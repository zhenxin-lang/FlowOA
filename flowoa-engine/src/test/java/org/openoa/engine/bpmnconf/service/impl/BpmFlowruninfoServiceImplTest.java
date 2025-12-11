package org.openoa.engine.bpmnconf.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openoa.base.entity.BpmFlowruninfo;
import org.openoa.base.util.SecurityUtils;
import org.openoa.engine.bpmnconf.mapper.BpmFlowruninfoMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * 单元测试类：BpmFlowruninfoServiceImpl
 *
 * @author FlowOA Test Engineer
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("BpmFlowruninfoServiceImpl 单元测试")
class BpmFlowruninfoServiceImplTest {

    @Mock
    private BpmFlowruninfoMapper bpmFlowruninfoMapper;

    @InjectMocks
    private BpmFlowruninfoServiceImpl bpmFlowruninfoService;

    @Test
    @DisplayName("测试 createFlowRunInfo - 正常创建")
    void testCreateFlowRunInfo_Success() throws Exception {
        try (MockedStatic<SecurityUtils> securityUtilsMock = Mockito.mockStatic(SecurityUtils.class)) {
            // Given
            String entryId = "entry:001";
            String processInstance = "123456";
            String userId = "user1";
            String userName = "User One";

            securityUtilsMock.when(SecurityUtils::getLogInEmpIdSafe).thenReturn(userId);
            securityUtilsMock.when(SecurityUtils::getLogInEmpNameSafe).thenReturn(userName);

            when(bpmFlowruninfoMapper.insert(any(BpmFlowruninfo.class))).thenReturn(1);

            // When
            bpmFlowruninfoService.createFlowRunInfo(entryId, processInstance);

            // Then
            verify(bpmFlowruninfoMapper).insert(argThat(entity ->
                    entity.getEntitykey().equals(entryId) &&
                            entity.getRuninfoid().equals(123456L) &&
                            entity.getCreateUserId().equals(userId) &&
                            entity.getCreateactor().equals(userName)
            ));
        }
    }

    @Test
    @DisplayName("测试 createFlowRunInfo(Overload) - 直接实体插入")
    void testCreateFlowRunInfo_Entity_Success() {
        // Given
        BpmFlowruninfo info = new BpmFlowruninfo();
        info.setRuninfoid(100L);

        when(bpmFlowruninfoMapper.insert(any(BpmFlowruninfo.class))).thenReturn(1);

        // When
        bpmFlowruninfoService.createFlowRunInfo(info);

        // Then
        verify(bpmFlowruninfoMapper).insert(info);
    }

    @Test
    @DisplayName("测试 getFlowruninfo - 查询详情")
    void testGetFlowruninfo_Success() {
        // Given
        Long runInfoId = 1L;
        BpmFlowruninfo mockInfo = new BpmFlowruninfo();
        mockInfo.setId(runInfoId);

        when(bpmFlowruninfoMapper.getFlowruninfo(runInfoId)).thenReturn(mockInfo);

        // When
        BpmFlowruninfo result = bpmFlowruninfoService.getFlowruninfo(runInfoId);

        // Then
        assertEquals(runInfoId, result.getId());
        verify(bpmFlowruninfoMapper).getFlowruninfo(runInfoId);
    }

    @Test
    @DisplayName("测试 deleteFlowruninfo - 删除")
    void testDeleteFlowruninfo_Success() {
        // Given
        Long id = 1L;
        when(bpmFlowruninfoMapper.deleteById(id)).thenReturn(1);

        // When
        bpmFlowruninfoService.deleteFlowruninfo(id);

        // Then
        verify(bpmFlowruninfoMapper).deleteById(id);
    }
}
