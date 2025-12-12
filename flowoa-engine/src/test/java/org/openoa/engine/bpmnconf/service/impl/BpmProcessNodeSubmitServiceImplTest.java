package org.openoa.engine.bpmnconf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openoa.base.entity.BpmProcessNodeSubmit;
import org.openoa.engine.bpmnconf.mapper.BpmProcessNodeSubmitMapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("BpmProcessNodeSubmitServiceImpl 单元测试")
class BpmProcessNodeSubmitServiceImplTest {

    @Mock
    private BpmProcessNodeSubmitMapper bpmProcessNodeSubmitMapper;

    @InjectMocks
    private BpmProcessNodeSubmitServiceImpl bpmProcessNodeSubmitService;

    @Test
    @DisplayName("测试 findBpmProcessNodeSubmit - 存在记录时返回最新一条")
    void testFindBpmProcessNodeSubmit_Found() {
        // Given
        String procInstId = "proc:123";
        BpmProcessNodeSubmit submit1 = new BpmProcessNodeSubmit();
        submit1.setId(1L);
        BpmProcessNodeSubmit submit2 = new BpmProcessNodeSubmit();
        submit2.setId(2L);
        
        // Mock returning a list (assuming mapper sorting works, we mock the result list order)
        List<BpmProcessNodeSubmit> list = new ArrayList<>();
        list.add(submit1); // First element
        list.add(submit2);

        when(bpmProcessNodeSubmitMapper.selectList(any(QueryWrapper.class))).thenReturn(list);

        // When
        BpmProcessNodeSubmit result = bpmProcessNodeSubmitService.findBpmProcessNodeSubmit(procInstId);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(bpmProcessNodeSubmitMapper).selectList(any(QueryWrapper.class));
    }

    @Test
    @DisplayName("测试 findBpmProcessNodeSubmit - 无记录时返回null")
    void testFindBpmProcessNodeSubmit_NotFound() {
        // Given
        String procInstId = "proc:123";
        when(bpmProcessNodeSubmitMapper.selectList(any(QueryWrapper.class))).thenReturn(Collections.emptyList());

        // When
        BpmProcessNodeSubmit result = bpmProcessNodeSubmitService.findBpmProcessNodeSubmit(procInstId);

        // Then
        assertNull(result);
    }

    @Test
    @DisplayName("测试 addProcessNode - 验证先删后增逻辑")
    void testAddProcessNode_Success() {
        // Given
        BpmProcessNodeSubmit submit = new BpmProcessNodeSubmit();
        submit.setProcessInstanceId("proc:123");
        submit.setState(1);

        when(bpmProcessNodeSubmitMapper.delete(any(QueryWrapper.class))).thenReturn(1);
        when(bpmProcessNodeSubmitMapper.insert(any(BpmProcessNodeSubmit.class))).thenReturn(1);

        // When
        boolean result = bpmProcessNodeSubmitService.addProcessNode(submit);

        // Then
        assertTrue(result);
        // Verify delete is called once
        verify(bpmProcessNodeSubmitMapper, times(1)).delete(any(QueryWrapper.class));
        // Verify insert is called once with the correct object
        verify(bpmProcessNodeSubmitMapper, times(1)).insert(submit);
    }

    @Test
    @DisplayName("测试 deleteProcessNode - 验证删除逻辑")
    void testDeleteProcessNode_Success() {
        // Given
        String procInstId = "proc:123";
        when(bpmProcessNodeSubmitMapper.delete(any(QueryWrapper.class))).thenReturn(1);

        // When
        boolean result = bpmProcessNodeSubmitService.deleteProcessNode(procInstId);

        // Then
        assertTrue(result);
        verify(bpmProcessNodeSubmitMapper).delete(any(QueryWrapper.class));
    }
}
