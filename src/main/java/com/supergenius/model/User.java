package com.supergenius.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.util.Collection;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * <p>
 * 安全用户表
 * </p>
 *
 * @author zuoyu
 * @since 2019-11-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("security_user")
@ApiModel(value="User对象", description="安全用户表")
public class User extends Model<User> implements UserDetails {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "安全用户ID")
    @TableId(value = "USER_ID", type = IdType.AUTO)
    private Integer userId;

    @ApiModelProperty(value = "安全账号")
    @TableField("USER_SECURITY_NAME")
    private String userSecurityName;

    @ApiModelProperty(value = "用户密码")
    @TableField("USER_PASS_WORD")
    private String userPassWord;

    @ApiModelProperty(value = "账号是否启用")
    @TableField("USER_IS_ENABLED")
    private Boolean userIsEnabled;

    @ApiModelProperty(value = "账号是否未过期")
    @TableField("USER_IS_ACCOUNT_NON_EXPIRED")
    private Boolean userIsAccountNonExpired;

    @ApiModelProperty(value = "凭证是否未过期")
    @TableField("USER_IS_CREDENTIALS_NON_EXPIRED")
    private Boolean userIsCredentialsNonExpired;

    @ApiModelProperty(value = "帐号是否未锁定")
    @TableField("USER_IS_ACCOUNT_NON_LOCKED")
    private Boolean userIsAccountNonLocked;

    @ApiModelProperty(value = "创建时间")
    @TableField("USER_CREAT_TIME_STAMP")
    private Date userCreatTimeStamp;

    @ApiModelProperty(value = "修改时间")
    @TableField("USER_UPDATE_TIME_STAMP")
    private Date userUpdateTimeStamp;

    @ApiModelProperty(value = "该账号是否已被删除")
    @TableField("USER_IS_DELETE")
    private Boolean userIsDelete;

    @ApiModelProperty(value = "用户名")
    @TableField("USER_NAME")
    private String userName;

    @ApiModelProperty(value = "用户编号")
    @TableField("USER_NUMBER")
    private String userNumber;

    @ApiModelProperty(value = "用户手机号")
    @TableField("USER_PHONE")
    private String userPhone;

    @ApiModelProperty(value = "用户邮箱")
    @TableField("USER_EMAIL")
    private String userEmail;


    @Override
    protected Serializable pkVal() {
        return this.userId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.userPassWord;
    }

    @Override
    public String getUsername() {
        return this.userSecurityName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.userIsAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.userIsAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.userIsCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.userIsEnabled;
    }

    public User(String userSecurityName, String userPassWord, String userName, String userNumber, String userPhone, String userEmail) {
        this.userSecurityName = userSecurityName;
        this.userPassWord = userPassWord;
        this.userName = userName;
        this.userNumber = userNumber;
        this.userPhone = userPhone;
        this.userEmail = userEmail;
    }
}
