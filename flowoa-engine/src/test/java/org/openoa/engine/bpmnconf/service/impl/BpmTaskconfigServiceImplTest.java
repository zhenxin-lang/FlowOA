package org.openoa.engine.bpmnconf.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openoa.base.entity.BpmTaskconfig;
import org.openoa.base.vo.TaskMgmtVO;
import org.openoa.engine.bpmnconf.mapper.BpmTaskconfigMapper;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@DisplayName("BpmTaskconfigServiceImpl 单元测试")
class BpmTaskconfigServiceImplTest {

    @Mock
    private BpmTaskconfigMapper bpmTaskconfigMapper;

    @InjectMocks
    private BpmTaskconfigServiceImpl bpmTaskconfigService;

    @Test
    @DisplayName("测试 addBpmTaskconfig (带number参数) - 验证对象构建与插入")
    void testAddBpmTaskconfig_WithNumber_Success() {
        String procDefId = "proc:1:123";
        String taskDefKey = "task_1";
        Long userId = 1001L;
        Integer number = 5;

        when(bpmTaskconfigMapper.insert(any(BpmTaskconfig.class))).thenReturn(1);

        bpmTaskconfigService.addBpmTaskconfig(procDefId, taskDefKey, userId, number);

        ArgumentCaptor<BpmTaskconfig> captor = ArgumentCaptor.forClass(BpmTaskconfig.class);
        verify(bpmTaskconfigMapper, times(1)).insert(captor.capture());

        BpmTaskconfig captured = captor.getValue();
        assertEquals(procDefId, captured.getProcDefId());
        assertEquals(taskDefKey, captured.getTaskDefKey());
        assertEquals(userId, captured.getUserId());
        assertEquals(number, captured.getNumber());
    }

    @Test
    @DisplayName("测试 addBpmTaskconfig (无number参数) - 验证对象构建与插入")
    void testAddBpmTaskconfig_NoNumber_Success() {
        String procDefId = "proc:1:123";
        String taskDefKey = "task_1";
        Long userId = 1001L;

        when(bpmTaskconfigMapper.insert(any(BpmTaskconfig.class))).thenReturn(1);

        bpmTaskconfigService.addBpmTaskconfig(procDefId, taskDefKey, userId);

        ArgumentCaptor<BpmTaskconfig> captor = ArgumentCaptor.forClass(BpmTaskconfig.class);
        verify(bpmTaskconfigMapper, times(1)).insert(captor.capture());

        BpmTaskconfig captured = captor.getValue();
        assertEquals(procDefId, captured.getProcDefId());
        assertEquals(taskDefKey, captured.getTaskDefKey());
        assertEquals(userId, captured.getUserId());
        assertNull(captured.getNumber());
    }

    @Test
    @DisplayName("测试 findTaskCode - 验证查询透传")
    void testFindTaskCode_Success() {
        // Given
        String procDefId = "proc:1";
        String taskDefKey = "task_1";
        TaskMgmtVO mockVo = new TaskMgmtVO();
        when(bpmTaskconfigMapper.findTaskCode(procDefId, taskDefKey))
                .thenReturn(Collections.singletonList(mockVo));

        List<TaskMgmtVO> result = bpmTaskconfigService.findTaskCode(procDefId, taskDefKey);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(bpmTaskconfigMapper).findTaskCode(procDefId, taskDefKey);
    }

    @Test
    @DisplayName("测试 deleteByTask - 验证删除逻辑")
    void testDeleteByTask_Success() {
        String procDefId = "proc:1";
        String taskKey = "task_1";
        when(bpmTaskconfigMapper.deleteByTask(procDefId, taskKey)).thenReturn(1);

        Integer result = bpmTaskconfigService.deleteByTask(procDefId, taskKey);

        assertEquals(1, result);
        verify(bpmTaskconfigMapper).deleteByTask(procDefId, taskKey);
    }

    @Test
    @DisplayName("测试 findByAppRoute - 验证参数传递")
    void testFindByAppRoute_Success() {
        String processKey = "KEY_001";
        String taskKey = "Task_User";
        String routeType = "APP";
        Map<String, Object> mockMap = Collections.singletonMap("route", "/pages/index");

        when(bpmTaskconfigMapper.findByAppRoute(processKey, taskKey, routeType))
                .thenReturn(mockMap);

        Map<String, Object> result = bpmTaskconfigService.findByAppRoute(processKey, taskKey, routeType);

        assertNotNull(result);
        assertEquals("/pages/index", result.get("route"));
    }
}
