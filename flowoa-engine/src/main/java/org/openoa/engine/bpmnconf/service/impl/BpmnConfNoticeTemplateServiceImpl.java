package org.openoa.engine.bpmnconf.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.openoa.base.entity.BpmnConfNoticeTemplate;
import org.openoa.engine.bpmnconf.mapper.BpmnConfNoticeTemplateMapper;
import org.openoa.engine.bpmnconf.service.interf.repository.BpmnConfNoticeTemplateService;
import org.springframework.stereotype.Repository;


@Repository
public class BpmnConfNoticeTemplateServiceImpl extends ServiceImpl<BpmnConfNoticeTemplateMapper, BpmnConfNoticeTemplate> implements BpmnConfNoticeTemplateService {

}
