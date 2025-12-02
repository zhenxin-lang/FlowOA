package org.openoa.engine.bpmnconf.service.tagparser;

import org.openoa.base.constant.enums.NodePropertyEnum;
import org.openoa.base.exception.AFBizException;
import org.openoa.base.util.SpringBeanUtils;
import org.openoa.common.adaptor.bpmnelementadp.BpmnElementAdaptor;
import org.openoa.engine.factory.TagParser;

import java.util.Collection;


public class BpmnElementAdaptorTagParser implements TagParser<BpmnElementAdaptor, NodePropertyEnum> {
    @Override
    public BpmnElementAdaptor parseTag(NodePropertyEnum data) {
        if(data==null){
            throw new AFBizException("provided data to find an element adaptor method is null");
        }

        Collection<BpmnElementAdaptor> elementAdaptors = SpringBeanUtils.getBeans(BpmnElementAdaptor.class);
        for (BpmnElementAdaptor elementAdaptor : elementAdaptors) {
            if(elementAdaptor.isSupportBusinessObject(data)){
                return elementAdaptor;
            }
        }
        return null;
    }
}
