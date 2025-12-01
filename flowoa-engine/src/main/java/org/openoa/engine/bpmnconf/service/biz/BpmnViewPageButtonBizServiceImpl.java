package org.openoa.engine.bpmnconf.service.biz;

import org.openoa.base.entity.BpmnViewPageButton;
import org.openoa.base.vo.BpmnConfVo;
import org.openoa.base.vo.BpmnViewPageButtonBaseVo;
import org.openoa.engine.bpmnconf.service.impl.BpmnViewPageButtonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

import static org.openoa.base.constant.enums.ViewPageTypeEnum.VIEW_PAGE_TYPE_OTHER;
import static org.openoa.base.constant.enums.ViewPageTypeEnum.VIEW_PAGE_TYPE_START;

@Service
public class BpmnViewPageButtonBizServiceImpl extends BizServiceImpl<BpmnViewPageButtonServiceImpl> {
    public void editBpmnViewPageButton(BpmnConfVo bpmnConfVo, Long confId) {
        BpmnViewPageButtonBaseVo viewPageButtons = bpmnConfVo.getViewPageButtons();
        if (ObjectUtils.isEmpty(viewPageButtons)) {
            return;
        }
        getService().deleteByConfId(confId);

        List<Integer> viewPageStarts = viewPageButtons.getViewPageStart();
        if (!ObjectUtils.isEmpty(viewPageStarts)) {
            List<BpmnViewPageButton> viewPageButtonList = viewPageStarts
                    .stream()
                    .map(start -> BpmnViewPageButton.buildViewPageButton(confId, start, VIEW_PAGE_TYPE_START.getCode()))
                    .collect(Collectors.toList());
            getService().saveBatch(viewPageButtonList);
        }

        List<Integer> viewPageOthers = viewPageButtons.getViewPageOther();

        if (!ObjectUtils.isEmpty(viewPageOthers)) {
            List<BpmnViewPageButton> viewPageButtonList = viewPageStarts
                    .stream()
                    .map(other -> BpmnViewPageButton.buildViewPageButton(confId, other, VIEW_PAGE_TYPE_OTHER.getCode()))
                    .collect(Collectors.toList());
            getService().saveBatch(viewPageButtonList);
        }
    }
}
