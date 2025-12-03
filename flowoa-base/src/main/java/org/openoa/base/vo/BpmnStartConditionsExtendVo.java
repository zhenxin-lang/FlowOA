package org.openoa.base.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class BpmnStartConditionsExtendVo extends BpmnStartConditionsVo implements Serializable {

    private static final long serialVersionUID = -7370591180959619868L;

    private String bpmnCode;
}
