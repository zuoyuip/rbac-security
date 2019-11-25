package com.supergenius.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 角色权限中间表
 * </p>
 *
 * @author zuoyu
 * @since 2019-11-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("security_role_authority")
@ApiModel(value="RoleAuthority对象", description="角色权限中间表")
public class RoleAuthority extends Model<RoleAuthority> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @TableId(value = "ROLE_AUTHORITY_ID", type = IdType.AUTO)
    private Integer roleAuthorityId;

    @ApiModelProperty(value = "角色ID")
    @TableField("ROLE_ID")
    private Integer roleId;

    @ApiModelProperty(value = "权限ID")
    @TableField("AUTHORITY_ID")
    private Integer authorityId;

    @ApiModelProperty(value = "创建时间")
    @TableField("ROLE_AUTHORITY_CREAT_TIME_STAMP")
    private Date roleAuthorityCreatTimeStamp;

    @ApiModelProperty(value = "更新时间")
    @TableField("ROLE_AUTHORITY_UPDATE_TIME_STAMP")
    private Date roleAuthorityUpdateTimeStamp;

    @ApiModelProperty(value = "是否被删除")
    @TableField("ROLE_AUTHORITY_IS_DELETE")
    private Boolean roleAuthorityIsDelete;


    @Override
    protected Serializable pkVal() {
        return this.roleAuthorityId;
    }

}
