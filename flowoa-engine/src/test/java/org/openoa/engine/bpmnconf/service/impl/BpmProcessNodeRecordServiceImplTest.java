package org.openoa.engine.bpmnconf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openoa.base.entity.BpmProcessNodeRecord;
import org.openoa.engine.bpmnconf.mapper.BpmProcessNodeRecordMapper;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("BpmProcessNodeRecordServiceImpl 单元测试")
class BpmProcessNodeRecordServiceImplTest {

    @Mock
    private BpmProcessNodeRecordMapper bpmProcessNodeRecordMapper;

    @InjectMocks
    private BpmProcessNodeRecordServiceImpl bpmProcessNodeRecordService;

    @Test
    @DisplayName("测试 addBpmProcessNodeRecord - 成功插入")
    void testAddBpmProcessNodeRecord_Success() {
        // Given
        BpmProcessNodeRecord record = new BpmProcessNodeRecord();
        record.setProcessInstanceId("proc:123");
        record.setTaskId("task:456");

        when(bpmProcessNodeRecordMapper.insert(any(BpmProcessNodeRecord.class))).thenReturn(1);

        // When
        boolean result = bpmProcessNodeRecordService.addBpmProcessNodeRecord(record);

        // Then
        assertTrue(result);
        verify(bpmProcessNodeRecordMapper, times(1)).insert(record);
    }

    @Test
    @DisplayName("测试 getBpmProcessNodeRecord - 查询到记录")
    void testGetBpmProcessNodeRecord_Found() {
        // Given
        BpmProcessNodeRecord query = new BpmProcessNodeRecord();
        query.setProcessInstanceId("proc:123");
        query.setTaskId("task:456");

        BpmProcessNodeRecord existingRecord = new BpmProcessNodeRecord();
        existingRecord.setId(1L);
        existingRecord.setProcessInstanceId("proc:123");
        existingRecord.setTaskId("task:456");

        when(bpmProcessNodeRecordMapper.selectList(any(QueryWrapper.class)))
                .thenReturn(Collections.singletonList(existingRecord));

        // When
        BpmProcessNodeRecord result = bpmProcessNodeRecordService.getBpmProcessNodeRecord(query);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(bpmProcessNodeRecordMapper).selectList(any(QueryWrapper.class));
    }

    @Test
    @DisplayName("测试 getBpmProcessNodeRecord - 未查询到记录")
    void testGetBpmProcessNodeRecord_NotFound() {
        // Given
        BpmProcessNodeRecord query = new BpmProcessNodeRecord();
        query.setProcessInstanceId("proc:123");
        query.setTaskId("task:456");

        when(bpmProcessNodeRecordMapper.selectList(any(QueryWrapper.class)))
                .thenReturn(Collections.emptyList());

        // When
        BpmProcessNodeRecord result = bpmProcessNodeRecordService.getBpmProcessNodeRecord(query);

        // Then
        assertNull(result);
        verify(bpmProcessNodeRecordMapper).selectList(any(QueryWrapper.class));
    }
}
