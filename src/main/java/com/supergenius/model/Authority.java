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
import org.apache.ibatis.annotations.ConstructorArgs;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 权限表
 * </p>
 *
 * @author zuoyu
 * @since 2019-11-22
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("security_authority")
@ApiModel(value = "Authority对象", description = "权限表")
public class Authority extends Model<Authority> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "AUTHORITY_ID", type = IdType.AUTO)
    private Integer authorityId;

    @ApiModelProperty(value = "权限名称")
    @TableField("AUTHORITY_NAME")
    private String authorityName;

    @ApiModelProperty(value = "权限目录名称")
    @TableField("AUTHORITY_CONTENT")
    private String authorityContent;

    @ApiModelProperty(value = "权限菜单名称")
    @TableField("AUTHORITY_MENU")
    private String authorityMenu;

    @ApiModelProperty(value = "权限目录URL")
    @TableField("AUTHORITY_MENU_URL")
    private String authorityMenuUrl;

    @ApiModelProperty(value = "创建时间")
    @TableField("AUTHORITY_CREAT_TIME_STAMP")
    private Date authorityCreatTimeStamp;

    @ApiModelProperty(value = "更新时间")
    @TableField("AUTHORITY_UPDATE_TIME_STAMP")
    private Date authorityUpdateTimeStamp;

    @ApiModelProperty(value = "是否被删除")
    @TableField("AUTHORITY_IS_DELETE")
    private Boolean authorityIsDelete;


    @Override
    protected Serializable pkVal() {
        return this.authorityId;
    }


    public Authority(String authorityName, String authorityContent, String authorityMenu, String authorityMenuUrl) {
        this.authorityName = authorityName;
        this.authorityContent = authorityContent;
        this.authorityMenu = authorityMenu;
        this.authorityMenuUrl = authorityMenuUrl;
    }
}
