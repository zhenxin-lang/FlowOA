package org.openoa.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.openoa.entity.Person;

import static org.openoa.base.constant.StringConstants.DB_NAME_2;

@Mapper
//@DS(DB_NAME_2)
public interface PersonMapper extends BaseMapper<Person> {
}
