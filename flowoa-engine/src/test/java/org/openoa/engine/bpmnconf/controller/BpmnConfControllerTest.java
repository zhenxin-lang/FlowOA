package org.openoa.engine.bpmnconf.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openoa.base.dto.PageDto;
import org.openoa.base.vo.BpmnConfVo;
import org.openoa.base.vo.BusinessDataVo;
import org.openoa.base.vo.ConfDetailRequestDto;
import org.openoa.base.vo.ResultAndPage;
import org.openoa.engine.bpmnconf.service.biz.BpmVerifyInfoBizServiceImpl;
import org.openoa.engine.bpmnconf.service.biz.BpmnConfBizServiceImpl;
import org.openoa.engine.bpmnconf.service.biz.ProcessApprovalServiceImpl;
import org.openoa.engine.bpmnconf.service.impl.BpmnNodeServiceImpl;
import org.openoa.engine.bpmnconf.service.impl.BpmnNodeToServiceImpl;
import org.openoa.engine.bpmnconf.service.interf.biz.BpmnConfBizService;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class BpmnConfControllerTest {

    @Mock
    private BpmnConfBizService bpmnConfBizService;
    @Mock
    private BpmnNodeToServiceImpl bpmnNodeToService;
    @Mock
    private ProcessApprovalServiceImpl processApprovalService;
    @Mock
    private BpmnConfBizServiceImpl bpmnConfCommonService;
    @Mock
    private BpmVerifyInfoBizServiceImpl bpmVerifyInfoBizService;
    @Mock
    private BpmnNodeServiceImpl testService;

    @InjectMocks
    private BpmnConfController bpmnConfController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(bpmnConfController).build();
    }

    @Test
    void testEdit() throws Exception {
        BpmnConfVo vo = new BpmnConfVo();
        vo.setBpmnCode("test-code");
        
        mockMvc.perform(post("/bpmnConf/edit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value("ok"));
    }

    @Test
    void testListPage() throws Exception {
        ConfDetailRequestDto dto = new ConfDetailRequestDto();
        dto.setPageDto(new PageDto());
        dto.setEntity(new BpmnConfVo());

        ResultAndPage resultAndPage = new ResultAndPage(java.util.Collections.emptyList(), new PageDto());
        when(bpmnConfBizService.selectPage(any(), any())).thenReturn(resultAndPage);

        mockMvc.perform(post("/bpmnConf/listPage")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void testDetail() throws Exception {
        BpmnConfVo vo = new BpmnConfVo();
        vo.setId(1L);
        vo.setBpmnName("Test Process");
        when(bpmnConfBizService.detail(1)).thenReturn(vo);

        mockMvc.perform(get("/bpmnConf/detail/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.bpmnName").value("Test Process"));
    }

    @Test
    void testButtonsOperation() throws Exception {
        BusinessDataVo businessDataVo = new BusinessDataVo();
        businessDataVo.setProcessNumber("PROC-001");
        
        when(processApprovalService.buttonsOperation(any(), any())).thenReturn(businessDataVo);

        mockMvc.perform(post("/bpmnConf/process/buttonsOperation")
                .contentType(MediaType.APPLICATION_JSON)
                .content("\"some-json-values\"")
                .param("formCode", "FORM-001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.processNumber").value("PROC-001"));
    }
}
