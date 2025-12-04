package org.openoa.engine.bpmnconf.adp.conditionfilter.nodetypeconditions;

import lombok.extern.slf4j.Slf4j;
import org.openoa.base.vo.BaseIdTranStruVo;
import org.openoa.base.vo.BpmnNodeConditionsConfBaseVo;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class BpmnNodeConditionsEmptyAdp extends BpmnNodeConditionsAdaptor {
    @Override
    public void setConditionsResps(BpmnNodeConditionsConfBaseVo bpmnNodeConditionsConfBaseVo) {

    }
}
