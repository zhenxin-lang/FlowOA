package org.openoa.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("person")
public class Person {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String name;
    private Integer age;
    private String year;
}
