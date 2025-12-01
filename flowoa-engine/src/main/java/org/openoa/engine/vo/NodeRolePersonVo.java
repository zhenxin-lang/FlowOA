package org.openoa.engine.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.openoa.base.vo.BaseIdTranStruVo;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class NodeRolePersonVo {

    private String roleId;

    private String roleName;

    private List<BaseIdTranStruVo> userList;
}
