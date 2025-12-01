package org.openoa.engine.bpmnconf.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.openoa.base.entity.BpmnNodePersonnelConf;
import org.openoa.engine.bpmnconf.mapper.BpmnNodePersonnelConfMapper;
import org.openoa.engine.bpmnconf.service.interf.repository.BpmnNodePersonnelConfService;
import org.springframework.stereotype.Repository;

@Repository
public class BpmnNodePersonnelConfServiceImpl extends ServiceImpl<BpmnNodePersonnelConfMapper, BpmnNodePersonnelConf> implements BpmnNodePersonnelConfService {
}
