package org.openoa.engine.bpmnconf.service.biz.personnelinfoprovider;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.openoa.base.exception.AFBizException;
import org.openoa.base.exception.BusinessErrorEnum;
import org.openoa.base.vo.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Slf4j
@Service("udrPersonnelProvider")
public class UDRPersonnelProvider extends AbstractMissingAssignNodeAssigneeVoProvider{

    @Override
    public List<BpmnNodeParamsAssigneeVo> getAssigneeList(BpmnNodeVo bpmnNodeVo, BpmnStartConditionsVo startConditionsVo) {
        Long id = bpmnNodeVo.getId();
        List<BaseIdTranStruVo> assignees=new ArrayList<>();
        BusinessDataVo businessDataVo = startConditionsVo.getBusinessDataVo();
        String startUserId = startConditionsVo.getStartUserId();
        BaseIdTranStruVo udrAssigneeProperty = bpmnNodeVo.getProperty().getUdrAssigneeProperty();
        String udrValueJson = bpmnNodeVo.getProperty().getUdrValueJson();
        if(udrAssigneeProperty==null){
            throw new AFBizException(BusinessErrorEnum.PARAMS_NOT_COMPLETE.getCodeStr(),"udrAssigneeProperty missing");
        }
        if(udrAssigneeProperty.getId().equalsIgnoreCase("zdysp1")){
            assignees= Lists.newArrayList(BaseIdTranStruVo.builder().id("1").name("张三").build());
        }else if(udrAssigneeProperty.getId().equalsIgnoreCase("zdysp2")){
            assignees= Lists.newArrayList(BaseIdTranStruVo.builder().id("1").name("张三").build());
        }else{//可以继续增加逻辑
            assignees= Lists.newArrayList(BaseIdTranStruVo.builder().id("1").name("张三").build());
        }
        return  super.provideAssigneeList(bpmnNodeVo,assignees);
    }
}
