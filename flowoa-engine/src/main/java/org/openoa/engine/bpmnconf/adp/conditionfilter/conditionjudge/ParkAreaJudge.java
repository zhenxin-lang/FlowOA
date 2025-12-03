package org.openoa.engine.bpmnconf.adp.conditionfilter.conditionjudge;


public class ParkAreaJudge extends AbstractBinaryComparableJudge {
    @Override
    protected String fieldNameInDb() {
        return "parkArea";
    }

    @Override
    protected String fieldNameInStartConditions() {
        return "totalMoney";
    }
}
