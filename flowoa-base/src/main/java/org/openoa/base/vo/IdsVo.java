package org.openoa.base.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.io.Serializable;

@Data
public class IdsVo implements Serializable {
    private Integer id;
    private String powerId;

}
