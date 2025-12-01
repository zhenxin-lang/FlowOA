package org.openoa.engine.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OutSideBpmApplicationVo {

    /**
     * 第三方业务id
     */
    private Long thirdId;

    /**
     * 第三方业务名称
     */
    private String thirdName;

    /**
     * 第三方业务code
     */
    private String thirdCode;

    /**
     * 第三方业务备注
     */
    private String thirdRemark;

    /**
     * 流程名称
     */
    private String processName;

    /**
     * 流程key
     */
    private String processKey;
}
