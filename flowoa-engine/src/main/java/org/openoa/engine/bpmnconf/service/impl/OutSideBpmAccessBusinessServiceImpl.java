package org.openoa.engine.bpmnconf.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.openoa.base.entity.OutSideBpmAccessBusiness;
import org.openoa.engine.bpmnconf.mapper.OutSideBpmAccessBusinessMapper;
import org.openoa.engine.bpmnconf.service.interf.repository.OutSideBpmAccessBusinessService;
import org.springframework.stereotype.Repository;



@Repository
public class OutSideBpmAccessBusinessServiceImpl extends ServiceImpl<OutSideBpmAccessBusinessMapper, OutSideBpmAccessBusiness> implements OutSideBpmAccessBusinessService {

}
