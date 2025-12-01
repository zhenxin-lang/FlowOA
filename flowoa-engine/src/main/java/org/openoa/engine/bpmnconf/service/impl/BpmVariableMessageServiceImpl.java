package org.openoa.engine.bpmnconf.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.openoa.base.entity.BpmVariableMessage;
import org.openoa.engine.bpmnconf.mapper.BpmVariableMessageMapper;
import org.openoa.engine.bpmnconf.service.interf.repository.BpmVariableMessageService;
import org.springframework.stereotype.Repository;


@Repository
public class BpmVariableMessageServiceImpl extends ServiceImpl<BpmVariableMessageMapper, BpmVariableMessage> implements BpmVariableMessageService {

}
