package org.openoa.engine.bpmnconf.adp.bpmnnodeadp;

import org.openoa.base.interf.AdaptorService;
import org.openoa.base.vo.BpmnNodeVo;
import org.openoa.base.vo.PersonnelRuleVO;


public interface BpmnNodeAdaptor extends AdaptorService {

    /**
     * format BpmnNodeVo
     *
     * @param bpmnNodeVo
     * @return
     */
    void formatToBpmnNodeVo(BpmnNodeVo bpmnNodeVo);
    PersonnelRuleVO formaFieldAttributeInfoVO();
    /**
     * edit bpmn node info
     *
     * @param bpmnNodeVo
     */
    void editBpmnNode(BpmnNodeVo bpmnNodeVo);

}