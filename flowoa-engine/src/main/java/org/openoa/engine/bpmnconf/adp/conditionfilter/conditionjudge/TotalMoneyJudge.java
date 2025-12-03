package org.openoa.engine.bpmnconf.adp.conditionfilter.conditionjudge;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TotalMoneyJudge extends AbstractBinaryComparableJudge {
    @Override
    protected String fieldNameInDb() {
        return "totalMoney";
    }

    @Override
    protected String fieldNameInStartConditions() {
        return "totalMoney";
    }
}
