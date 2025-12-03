package org.openoa.engine.bpmnconf.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.openoa.base.entity.Department;

import java.util.List;


@Mapper
public interface DepartmentMapper  {
    List<Department> ListSubDepartmentByEmployeeId(@Param("employeeId") String employeeId);

    Department getDepartmentByEmployeeId(@Param("employeeId") String employeeId);
}
