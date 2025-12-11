package org.openoa.engine.bpmnconf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openoa.base.entity.BpmProcessAppApplication;
import org.openoa.base.exception.AFBizException;
import org.openoa.engine.bpmnconf.mapper.BpmProcessAppApplicationMapper;
import org.openoa.engine.vo.BpmProcessAppApplicationVo;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("BpmProcessAppApplicationServiceImpl 单元测试")
class BpmProcessAppApplicationServiceImplTest {

    @Mock
    private BpmProcessAppApplicationMapper bpmProcessAppApplicationMapper;

    @InjectMocks
    private BpmProcessAppApplicationServiceImpl bpmProcessAppApplicationService;

    @Test
    @DisplayName("测试 addBpmProcessAppApplication - 新增成功")
    void testAddBpmProcessAppApplication_Add_Success() {
        // Given
        BpmProcessAppApplicationVo vo = new BpmProcessAppApplicationVo();
        vo.setTitle("Test App");
        vo.setBusinessCode("BUS_001");
        vo.setRoute("route/to/app");
        vo.setEffectiveSource("icon.png");

        // Mock duplication check: count returns 0
        when(bpmProcessAppApplicationMapper.selectCount(any(QueryWrapper.class))).thenReturn(0L);
        when(bpmProcessAppApplicationMapper.insert(any(BpmProcessAppApplication.class))).thenReturn(1);

        // When
        boolean result = bpmProcessAppApplicationService.addBpmProcessAppApplication(vo);

        // Then
        assertTrue(result);
        verify(bpmProcessAppApplicationMapper).insert(argThat(entity ->
                entity.getTitle().equals("Test App") &&
                entity.getProcessKey().startsWith("BUS_001_")
        ));
    }

    @Test
    @DisplayName("测试 addBpmProcessAppApplication - 新增失败(名称重复)")
    void testAddBpmProcessAppApplication_Add_Duplicate() {
        // Given
        BpmProcessAppApplicationVo vo = new BpmProcessAppApplicationVo();
        vo.setTitle("Test App");

        // Mock duplication check: count returns 1
        when(bpmProcessAppApplicationMapper.selectCount(any(QueryWrapper.class))).thenReturn(1L);

        // When & Then
        assertThrows(AFBizException.class, () -> bpmProcessAppApplicationService.addBpmProcessAppApplication(vo));
        verify(bpmProcessAppApplicationMapper, never()).insert(any(BpmProcessAppApplication.class));
    }

    @Test
    @DisplayName("测试 addBpmProcessAppApplication - 修改成功")
    void testAddBpmProcessAppApplication_Update_Success() {
        // Given
        BpmProcessAppApplicationVo vo = new BpmProcessAppApplicationVo();
        vo.setId(1);
        vo.setTitle("Updated App");
        vo.setRoute("route/to/app");

        when(bpmProcessAppApplicationMapper.updateById(any(BpmProcessAppApplication.class))).thenReturn(1);

        // When
        boolean result = bpmProcessAppApplicationService.addBpmProcessAppApplication(vo);

        // Then
        assertTrue(result);
        verify(bpmProcessAppApplicationMapper).updateById(argThat(entity ->
                entity.getId().equals(1) &&
                entity.getTitle().equals("Updated App")
        ));
    }

    @Test
    @DisplayName("测试 deleteAppIcon - 逻辑删除")
    void testDeleteAppIcon_Success() {
        // Given
        Long id = 100L;
        BpmProcessAppApplication mockApp = new BpmProcessAppApplication();
        mockApp.setId(100);
        mockApp.setIsDel(0);

        when(bpmProcessAppApplicationMapper.selectById(100L)).thenReturn(mockApp);
        when(bpmProcessAppApplicationMapper.updateById(any(BpmProcessAppApplication.class))).thenReturn(1);

        // When
        boolean result = bpmProcessAppApplicationService.deleteAppIcon(id);

        // Then
        assertTrue(result);
        verify(bpmProcessAppApplicationMapper).updateById(argThat(entity ->
                entity.getId().equals(100) &&
                entity.getIsDel().equals(1)
        ));
    }

    @Test
    @DisplayName("测试 listProcessApplication - 列表查询")
    void testListProcessApplication_Success() {
        // Given
        BpmProcessAppApplication app = new BpmProcessAppApplication();
        app.setId(1);
        app.setTitle("App 1");
        app.setCreateTime(new java.util.Date());

        when(bpmProcessAppApplicationMapper.selectList(any(QueryWrapper.class)))
                .thenReturn(Collections.singletonList(app));

        // When
        List<BpmProcessAppApplicationVo> result = bpmProcessAppApplicationService.listProcessApplication();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("App 1", result.get(0).getProcessName());
    }
}
