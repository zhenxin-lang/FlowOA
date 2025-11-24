package org.openoa.base.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BpmnNodeButtonConfBaseVo implements Serializable {
    /**
     * start page button list
     */
    private List<Integer> startPage;

    /**
     * approval page button list
     */
    private List<Integer> approvalPage;

    /**
     * view page button list
     */
    private List<Integer> viewPage;
}
