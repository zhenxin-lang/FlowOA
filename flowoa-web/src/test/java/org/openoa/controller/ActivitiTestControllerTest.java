package org.openoa.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openoa.base.util.MailUtils;
import org.openoa.base.vo.BaseIdTranStruVo;
import org.openoa.base.vo.MailInfo;
import org.openoa.engine.bpmnconf.common.TaskMgmtServiceImpl;
import org.openoa.mapper.StudentMapper;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ActivitiTestControllerTest {

    @Mock
    private MailUtils mailUtils;

    @Mock
    private TaskMgmtServiceImpl taskMgmtService;

    @Mock
    private StudentMapper studentMapper;

    @InjectMocks
    private ActivitiTest activitiTest;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(activitiTest).build();
    }

    @Test
    void testTestSendEmail() throws Exception {
        doNothing().when(mailUtils).sendMail(any(MailInfo.class));

        mockMvc.perform(post("/activiti/testSendEmail"))
                .andExpect(status().isOk());

        verify(mailUtils).sendMail(any(MailInfo.class));
    }

    @Test
    void testChangeFutureAssignee() throws Exception {
        doNothing().when(taskMgmtService).changeFutureAssignees(any(), any(), any());

        mockMvc.perform(post("/activiti/changefutureAssignees")
                        .param("executionId", "exec-1")
                        .param("variableName", "var-1")
                        .param("assignees", "user1,user2"))
                .andExpect(status().isOk());

        verify(taskMgmtService).changeFutureAssignees(any(), any(), any());
    }
}
