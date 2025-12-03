package org.openoa.engine.bpmnconf.adp.personneladp;

import org.openoa.base.service.empinfoprovider.BpmnEmployeeInfoProviderService;
import org.openoa.common.adaptor.AbstractBpmnPersonnelAdaptor;
import org.openoa.base.interf.BpmnPersonnelProviderService;
import org.openoa.base.constant.enums.PersonnelEnum;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class OutSidePersonnelAdaptor extends AbstractBpmnPersonnelAdaptor {
    public OutSidePersonnelAdaptor(BpmnEmployeeInfoProviderService bpmnEmployeeInfoProviderService,@Qualifier("outSidePersonnelProvider") BpmnPersonnelProviderService bpmnPersonnelProviderService) {
        super(bpmnEmployeeInfoProviderService, bpmnPersonnelProviderService);
    }

    @Override
    public void setSupportBusinessObjects() {
        addSupportBusinessObjects(PersonnelEnum.OUT_SIDE_ACCESS_PERSONNEL);
    }
}
