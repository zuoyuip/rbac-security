package com.supergenius.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author zuoyu
 * @since 2019-11-22
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("security_role")
@ApiModel(value="Role对象", description="角色表")
public class Role extends Model<Role> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色ID")
    @TableId(value = "ROLE_ID", type = IdType.AUTO)
    private Integer roleId;

    @ApiModelProperty(value = "角色名称")
    @TableField("ROLE_NAME")
    private String roleName;

    @ApiModelProperty(value = "该角色是否已被删除")
    @TableField("ROLE_IS_DELETE")
    private Boolean roleIsDelete;

    @ApiModelProperty(value = "角色的创建时间")
    @TableField("ROLE_CREAT_TIME_STAMP")
    private Date roleCreatTimeStamp;

    @ApiModelProperty(value = "角色的修改时间")
    @TableField("ROLE_UPDATE_TIME_STAMP")
    private Date roleUpdateTimeStamp;

    private List<Authority> authorities;


    @Override
    protected Serializable pkVal() {
        return this.roleId;
    }

    public Role(String roleName) {
        this.roleName = roleName;
    }
}
