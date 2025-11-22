package org.openoa.base.constant.enums;

import lombok.Getter;

@Getter
public enum HrbpTypeEnum implements AfEnumBase{
    HRBP(0,"hrbp"),
    HRBP_LEADER(2,"hrbp leader")
    ;
    private final Integer code;
    private final String desc;

    HrbpTypeEnum(Integer code, String desc){
        this.code = code;
        this.desc = desc;
    }
}
