package org.openoa.engine.bpmnconf.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.openoa.base.entity.BpmVariableSequenceFlow;
import org.openoa.engine.bpmnconf.mapper.BpmVariableSequenceFlowMapper;
import org.openoa.engine.bpmnconf.service.interf.repository.BpmVariableSequenceFlowService;
import org.springframework.stereotype.Repository;


@Repository
public class BpmVariableSequenceFlowServiceImpl extends ServiceImpl<BpmVariableSequenceFlowMapper, BpmVariableSequenceFlow> implements BpmVariableSequenceFlowService {

}
