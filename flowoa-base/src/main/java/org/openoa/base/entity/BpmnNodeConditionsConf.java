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
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_bpmn_node_conditions_conf")
public class BpmnNodeConditionsConf implements TenantField, Serializable {


    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("bpmn_node_id")
    private Long bpmnNodeId;

    @TableField("is_default")
    private Integer isDefault;
    @TableField("group_relation")
    private Integer groupRelation;

    private Integer sort;

    @TableField("ext_json")
    private String extJson;
    /**
     * remark
     */
    private String remark;
    /**
     * 0 for normal,1 for delete
     */
    @TableField("is_del")
    private Integer isDel;
    @TableField("tenant_id")
    private String tenantId;
    /**
     * create user
     */
    @TableField("create_user")
    private String createUser;
    /**
     * create time
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * update user
     */
    @TableField("update_user")
    private String updateUser;
    /**
     * update time
     */
    @TableField("update_time")
    private Date updateTime;

}