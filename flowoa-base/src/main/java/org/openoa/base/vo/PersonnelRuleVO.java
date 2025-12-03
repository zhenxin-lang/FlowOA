package org.openoa.base.vo;

import lombok.Data;

import java.util.List;


@Data
public class PersonnelRuleVO {
    private String nodePropertyName;
    private Integer nodeProperty;
    private List<FieldAttributeInfoVO> fieldInfos;
}
