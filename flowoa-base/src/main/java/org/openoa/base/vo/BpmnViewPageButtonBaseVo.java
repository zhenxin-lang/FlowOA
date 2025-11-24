package org.openoa.base.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BpmnViewPageButtonBaseVo {
    /**
     * view page start user
     */
    private List<Integer> viewPageStart;

    /**
     * 查view page other
     */
    private List<Integer> viewPageOther;
}
