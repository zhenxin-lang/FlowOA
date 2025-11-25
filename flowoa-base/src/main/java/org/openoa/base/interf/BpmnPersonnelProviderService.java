package org.openoa.base.interf;

import org.openoa.base.vo.BpmnNodeParamsAssigneeVo;
import org.openoa.base.vo.BpmnNodeVo;
import org.openoa.base.vo.BpmnStartConditionsVo;

import java.util.List;


public interface BpmnPersonnelProviderService {
    /**
     * 单人的也返回集合,取的时候再做处理
     * @param bpmnNodeVo
     * @return
     */
    public List<BpmnNodeParamsAssigneeVo> getAssigneeList(BpmnNodeVo bpmnNodeVo, BpmnStartConditionsVo startConditionsVo);
}
