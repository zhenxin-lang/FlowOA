package org.openoa.base.constant.enums;

import lombok.Getter;


public enum ApprovalFormCodeEnum {

    ;

    @Getter
    private String formCode;

    @Getter
    private String desc;

    ApprovalFormCodeEnum(String formCode, String desc) {
        this.formCode = formCode;
        this.desc = desc;
    }

    public static ApprovalFormCodeEnum getEnumByCode(String formCode) {
        for (ApprovalFormCodeEnum item : ApprovalFormCodeEnum.values()) {
            if (item.formCode.equals(formCode)) {
                return item;
            }
        }
        return null;
    }

    public static boolean exist(String formCode) {
        for (ApprovalFormCodeEnum item : ApprovalFormCodeEnum.values()) {
            if (item.formCode.equals(formCode)) {
                return true;
            }
        }
        return false;
    }

}
