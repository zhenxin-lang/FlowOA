package org.openoa.base.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;


@TableName("t_user")
@Getter
@Setter
public class User {
    private Long id;
    private String userName;
}
