package org.openoa.base.vo;

import lombok.Data;

@Data
public class ThirdPartyAccountApplyVo extends BusinessDataVo {
    private Integer AccountType;
    private String AccountOwnerName;
    private String remark;
}
