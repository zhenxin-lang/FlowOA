package org.openoa.engine.bpmnconf.service.tagparser;

import org.openoa.base.constant.enums.PersonnelEnum;
import org.openoa.base.exception.AFBizException;
import org.openoa.base.util.SpringBeanUtils;
import org.openoa.common.adaptor.AbstractBpmnPersonnelAdaptor;
import org.openoa.engine.factory.TagParser;

import java.util.Collection;


public class PersonnelTagParser implements TagParser<AbstractBpmnPersonnelAdaptor, PersonnelEnum> {
    @Override
    public AbstractBpmnPersonnelAdaptor parseTag(PersonnelEnum data) {
        if(data==null){
            throw new AFBizException("provided data to find a personnel adaptor method is null");
        }
        Collection<AbstractBpmnPersonnelAdaptor> beans = SpringBeanUtils.getBeans(AbstractBpmnPersonnelAdaptor.class);
        for (AbstractBpmnPersonnelAdaptor bean : beans) {
            if(bean.isSupportBusinessObject(data)){
                return bean;
            }
        }
       return null;
    }
}
