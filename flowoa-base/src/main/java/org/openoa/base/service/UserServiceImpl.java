package org.openoa.base.service;

import com.google.common.collect.Lists;
import org.openoa.base.entity.DetailedUser;
import org.openoa.base.mapper.UserMapper;
import org.openoa.base.vo.BaseIdTranStruVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;


@Service("afUserService")
public class UserServiceImpl implements AfUserService{
    @Autowired
    UserMapper userMapper;
    @Override
    public List<BaseIdTranStruVo> queryByNameFuzzy(String userName) {
        List<BaseIdTranStruVo> users = userMapper.queryByNameFuzzy(userName);
        return users;
    }

    @Override
    public List<BaseIdTranStruVo> queryCompanyByNameFuzzy(String companyName) {
        List<BaseIdTranStruVo> baseIdTranStruVos = userMapper.queryCompanyByNameFuzzy(companyName);
        return baseIdTranStruVos;
    }

    @Override
    public List<BaseIdTranStruVo> queryUserByIds(Collection<String> userIds){

        List<BaseIdTranStruVo> users = userMapper.queryByIds(userIds);
        return users;
    }
    @Override
    public BaseIdTranStruVo getById(String id){
        List<BaseIdTranStruVo> users = userMapper.queryByIds(Lists.newArrayList(id));
        if(CollectionUtils.isEmpty(users)){
            return new BaseIdTranStruVo();
        }
        return users.get(0);
    }
    @Override
    public  List<BaseIdTranStruVo> queryLeadersByEmployeeIdAndTier(String employeeId, Integer tier){
        List<BaseIdTranStruVo> users = userMapper.getLevelLeadersByEmployeeIdAndTier(employeeId,tier);
        return users;
    }

    /**
     * dummy sql to be implement
     * @param employeeId
     * @param grade
     * @return
     */
    @Override
    public  List<BaseIdTranStruVo> queryLeadersByEmployeeIdAndGrade(String employeeId, Integer grade){
        List<BaseIdTranStruVo> users = userMapper.getLevelLeadersByEmployeeIdAndEndGrade(employeeId,grade);
        return users;
    }
    /**
     * dummy sql to be implement
     * @param employeeId
     * @param level setting
     * @return
     */
    @Override
    public BaseIdTranStruVo queryLeaderByEmployeeIdAndLevel(String employeeId, Integer level){

        return userMapper.getLeaderByLeventDepartment(employeeId,level);
    }
    @Override
    public List<BaseIdTranStruVo> queryEmployeeHrpbByEmployeeIds(List<String> employeeIds){
        List<BaseIdTranStruVo> users = userMapper.queryHrpbByEmployeeIds(employeeIds);
        return users;
    }
    @Override
    public List<BaseIdTranStruVo> queryEmployeeDirectLeaderByIds(List<String> employeeIds){
        List<BaseIdTranStruVo> users = userMapper.queryDirectLeaderByEmployeeIds(employeeIds);
        return users;
    }


    /**
     * 此方法主要用于通知系统,参数比较多,但至少要返回邮箱,如果需要手机号通知还需要返回手机号
     * @param id
     * @return
     */
    @Override
    public DetailedUser getEmployeeDetailById(String id){
        return userMapper.getEmployeeDetailById(id);
    }

    /**
     * 和getEmployeeDetailById类似,但是返回的是集合
     * @param ids
     * @return
     */
    @Override
    public List<DetailedUser> getEmployeeDetailByIds(Collection<String> ids){
        return userMapper.getEmployeeDetailByIds(ids);
    }

    /**
     * 返回的是数数量,0代表无有效用户
     * @param id
     * @return
     */
    @Override
    public long checkEmployeeEffective(String id){
        return userMapper.checkEmployeeEffective(id);
    }

    @Override
    public List<BaseIdTranStruVo> queryDepartmentLeaderByIds(List<String> employeeIds) {
        List<BaseIdTranStruVo> users = userMapper.queryDepartmentLeaderByIds(employeeIds);
        return users;
    }
}
