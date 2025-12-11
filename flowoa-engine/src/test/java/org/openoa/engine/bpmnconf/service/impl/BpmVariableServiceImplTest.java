package org.openoa.engine.bpmnconf.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openoa.base.util.SpringBeanUtils;
import org.openoa.base.vo.BaseIdTranStruVo;
import org.openoa.common.entity.BpmVariableMultiplayer;
import org.openoa.common.entity.BpmVariableMultiplayerPersonnel;
import org.openoa.common.mapper.BpmVariableMultiplayerMapper;
import org.openoa.common.service.BpmVariableMultiplayerPersonnelServiceImpl;
import org.openoa.engine.bpmnconf.mapper.BpmVariableMapper;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("BpmVariableServiceImpl 单元测试")
class BpmVariableServiceImplTest {

    @Mock
    private BpmVariableMapper bpmVariableMapper;

    @Mock
    private BpmVariableMultiplayerMapper bpmVariableMultiplayerMapper;

    @InjectMocks
    private BpmVariableServiceImpl bpmVariableService;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(bpmVariableService, "baseMapper", bpmVariableMapper);
    }

    @Test
    @DisplayName("测试 getAssigneeNameByProcessNumAndElementId - 成功获取")
    void testGetAssigneeNameByProcessNumAndElementId() {
        // Given
        String processNum = "PROC_001";
        String elementId = "NODE_01";
        BaseIdTranStruVo assignee = BaseIdTranStruVo.builder().id("user1").name("User One").build();
        
        when(bpmVariableMultiplayerMapper.getAssigneeByElementId(processNum, elementId))
                .thenReturn(Collections.singletonList(assignee));

        // When
        Map<String, String> result = bpmVariableService.getAssigneeNameByProcessNumAndElementId(processNum, elementId);

        // Then
        assertNotNull(result);
        assertEquals("User One", result.get("user1"));
    }

    @Test
    @DisplayName("测试 getVarNameByProcessNumberAndElementId - 成功获取")
    void testGetVarNameByProcessNumberAndElementId() {
        // Given
        String processNum = "PROC_001";
        String elementId = "NODE_01";
        String expectedVarName = "var_test";

        when(bpmVariableMultiplayerMapper.getVarNameByElementId(processNum, elementId))
                .thenReturn(expectedVarName);

        // When
        String result = bpmVariableService.getVarNameByProcessNumberAndElementId(processNum, elementId);

        // Then
        assertEquals(expectedVarName, result);
    }

    @Test
    @DisplayName("测试 addNodeAssignees - 验证静态Bean获取与保存逻辑")
    void testAddNodeAssignees_Success() {
        try (MockedStatic<SpringBeanUtils> springBeanUtilsMock = Mockito.mockStatic(SpringBeanUtils.class)) {
            // Given
            String processNum = "PROC_001";
            String elementId = "NODE_01";
            List<BaseIdTranStruVo> assignees = new ArrayList<>();
            assignees.add(BaseIdTranStruVo.builder().id("u1").name("n1").build());

            // Mock DB returns
            BpmVariableMultiplayer multiplayer = new BpmVariableMultiplayer();
            multiplayer.setId(100L);
            when(bpmVariableMapper.querymultiplayersbyprocesselementid(processNum, elementId))
                    .thenReturn(Collections.singletonList(multiplayer));

            // Mock Service Bean
            BpmVariableMultiplayerPersonnelServiceImpl personnelService = mock(BpmVariableMultiplayerPersonnelServiceImpl.class);
            springBeanUtilsMock.when(() -> SpringBeanUtils.getBean(BpmVariableMultiplayerPersonnelServiceImpl.class))
                    .thenReturn(personnelService);

            // When
            bpmVariableService.addNodeAssignees(processNum, elementId, assignees);

            // Then
            verify(personnelService).saveBatch(argThat(collection -> {
                List<BpmVariableMultiplayerPersonnel> list = new ArrayList<>(collection);
                return list.size() == 1 &&
                       list.get(0).getVariableMultiplayerId().equals(100L) &&
                       list.get(0).getAssignee().equals("u1");
            }));
        }
    }

    @Test
    @DisplayName("测试 updateAssignee - 单人节点更新成功")
    void testUpdateAssignee_SingleSuccess() {
        // Given
        String processNum = "PROC_001";
        String elementId = "NODE_01";
        String assignee = "oldUser";
        BaseIdTranStruVo newUser = BaseIdTranStruVo.builder().id("newUser").name("New User").build();

        when(bpmVariableMapper.updateSingle(processNum, elementId, assignee, newUser.getId(), newUser.getName()))
                .thenReturn(1);

        // When
        bpmVariableService.updateAssignee(processNum, elementId, assignee, newUser);

        // Then
        verify(bpmVariableMapper).updateSingle(anyString(), anyString(), anyString(), anyString(), anyString());
        verify(bpmVariableMapper, never()).updateMultiPlayer(anyString(), anyString(), anyString(), anyString(), anyString());
    }

    @Test
    @DisplayName("测试 updateAssignee - 单人失败尝试多人更新")
    void testUpdateAssignee_MultiSuccess() {
        // Given
        String processNum = "PROC_001";
        String elementId = "NODE_01";
        String assignee = "oldUser";
        BaseIdTranStruVo newUser = BaseIdTranStruVo.builder().id("newUser").name("New User").build();

        when(bpmVariableMapper.updateSingle(anyString(), anyString(), anyString(), anyString(), anyString()))
                .thenReturn(0);
        when(bpmVariableMapper.updateMultiPlayer(processNum, elementId, assignee, newUser.getId(), newUser.getName()))
                .thenReturn(1);

        // When
        bpmVariableService.updateAssignee(processNum, elementId, assignee, newUser);

        // Then
        verify(bpmVariableMapper).updateMultiPlayer(anyString(), anyString(), anyString(), anyString(), anyString());
    }
}
