package org.openoa.engine.bpmnconf.adp.conditionfilter;

import org.openoa.base.vo.BpmnNodeConditionsConfBaseVo;
import org.openoa.base.vo.BpmnStartConditionsVo;


public interface ConditionJudge {

   boolean judge(String nodeId, BpmnNodeConditionsConfBaseVo conditionsConf, BpmnStartConditionsVo bpmnStartConditionsVo,int couDGroup);

}
