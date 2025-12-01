package org.openoa.engine.bpmnconf.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.openoa.base.entity.BpmnNodePersonnelEmplConf;
import org.openoa.engine.bpmnconf.mapper.BpmnNodePersonnelEmplConfMapper;
import org.openoa.engine.bpmnconf.service.interf.repository.BpmnNodePersonnelEmplConfService;
import org.springframework.stereotype.Repository;

@Repository
public class BpmnNodePersonnelEmplConfServiceImpl extends ServiceImpl<BpmnNodePersonnelEmplConfMapper, BpmnNodePersonnelEmplConf> implements BpmnNodePersonnelEmplConfService {
}
