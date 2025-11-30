package org.openoa.base.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OutSideBpmProcesses implements Serializable {

    /**
     * id
     */
    private Integer id;

    /**
     * name
     */
    private String name;

    /**
     * form code
     */
    private String formCode;

}
