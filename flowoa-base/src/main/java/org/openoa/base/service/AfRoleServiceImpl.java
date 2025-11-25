package org.openoa.base.service;

import org.openoa.base.entity.Role;
import org.openoa.base.entity.User;
import org.openoa.base.mapper.RoleMapper;
import org.openoa.base.vo.BaseIdTranStruVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service("afRoleServiceImpl")
public class AfRoleServiceImpl implements AfRoleService{
    @Autowired
    private RoleMapper rolesMapper;
    @Override
    public List<BaseIdTranStruVo> queryRoleByIds(Collection<String> roleIds) {
        return rolesMapper.queryRoleByIds(roleIds);
    }
    @Override
    public List<BaseIdTranStruVo> queryUserByRoleIds(Collection<String> roleIds) {
        return rolesMapper.queryUserByRoleIds(roleIds);
    }
}
