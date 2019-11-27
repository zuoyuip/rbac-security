package com.supergenius.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 用户角色中间表
 * </p>
 *
 * @author zuoyu
 * @since 2019-11-22
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("security_user_role")
@ApiModel(value = "UserRole对象", description = "用户角色中间表")
public class UserRole extends Model<UserRole> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @TableId(value = "USER_ROLE_ID", type = IdType.AUTO)
    private Integer userRoleId;

    @ApiModelProperty(value = "用户ID")
    @TableField("USER_ID")
    private Integer userId;

    @ApiModelProperty(value = "角色ID")
    @TableField("ROLE_ID")
    private Integer roleId;

    @ApiModelProperty(value = "角色分配时间 ")
    @TableField("USER_ROLE_CREAT_TIME_STAMP")
    private Date userRoleCreatTimeStamp;

    @ApiModelProperty(value = "角色的修改时间")
    @TableField("USER_ROLE_UPDATE_TIME_STAMP")
    private Date userRoleUpdateTimeStamp;

    @ApiModelProperty(value = "是否被删除")
    @TableField("USER_ROLE_IS_DELETE")
    private Boolean userRoleIsDelete;


    @Override
    protected Serializable pkVal() {
        return this.userRoleId;
    }

    public UserRole(Integer userId, Integer roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }
}
