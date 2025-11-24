package org.openoa.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.openoa.base.interf.TenantField;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_bpmn_node_button_conf")
public class BpmnNodeButtonConf implements TenantField, Serializable {

    private static final long serialVersionUID = 1L;


    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("bpmn_node_id")
    private Long bpmnNodeId;

    @TableField("button_page_type")
    private Integer buttonPageType;

    @TableField("button_type")
    private Integer buttonType;

    @TableField("button_name")
    private String buttonName;

    private String remark;

    @TableField("is_del")
    private Integer isDel;
    @TableField("tenant_id")
    private String tenantId;
    //0 for no and 1 for yes
    @TableField("start_page_only")
    private Integer startPageOnly;

    @TableField("create_user")
    private String createUser;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_user")
    private String updateUser;

    @TableField("update_time")
    private Date updateTime;

}