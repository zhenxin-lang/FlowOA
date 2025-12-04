package org.openoa.engine.bpmnconf.service.biz;

import org.openoa.base.entity.BpmnConf;
import org.openoa.engine.bpmnconf.service.impl.BpmnConfServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MybisService extends BizServiceImpl<BpmnConfServiceImpl> {
    public void getit() {
        List<BpmnConf> list = getService().list();
    }
}
