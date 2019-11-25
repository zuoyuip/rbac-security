create table security_authority
(
    AUTHORITY_ID                int auto_increment comment '主键',
    AUTHORITY_NAME              varchar(32)                         not null comment '权限名称',
    AUTHORITY_CONTENT           varchar(32)                         not null comment '权限目录名称',
    AUTHORITY_MENU              varchar(32)                         not null comment '权限菜单名称',
    AUTHORITY_MENU_URL          varchar(64)                         not null comment '权限目录URL',
    AUTHORITY_CREAT_TIME_STAMP  timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '创建时间',
    AUTHORITY_UPDATE_TIME_STAMP timestamp                           null comment '更新时间',
    AUTHORITY_IS_DELETE         tinyint(1)                          not null comment '是否被删除',
    constraint TB_AUTHORITY_AUTHORITY_ID_uindex
        unique (AUTHORITY_ID),
    constraint TB_AUTHORITY_AUTHORITY_MENU_URL_uindex
        unique (AUTHORITY_MENU_URL),
    constraint TB_AUTHORITY_AUTHORITY_MENU_uindex
        unique (AUTHORITY_MENU),
    constraint TB_AUTHORITY_AUTHORITY_NAME_uindex
        unique (AUTHORITY_NAME)
)
    comment '权限表';

alter table security_authority
    add primary key (AUTHORITY_ID);

create table security_role
(
    ROLE_ID                int auto_increment comment '角色ID',
    ROLE_NAME              varchar(32)                         not null comment '角色名称',
    ROLE_IS_DELETE         tinyint(1)                          not null comment '该角色是否已被删除',
    ROLE_CREAT_TIME_STAMP  timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '角色的创建时间',
    ROLE_UPDATE_TIME_STAMP timestamp                           null comment '角色的修改时间',
    constraint TB_ROLE_ROLE_ID_uindex
        unique (ROLE_ID),
    constraint TB_ROLE_ROLE_NAME_uindex
        unique (ROLE_NAME)
)
    comment '角色表';

alter table security_role
    add primary key (ROLE_ID);

create table security_role_authority
(
    ROLE_AUTHORITY_ID                int auto_increment comment 'ID',
    ROLE_ID                          int                                 not null comment '角色ID',
    AUTHORITY_ID                     int                                 not null comment '权限ID',
    ROLE_AUTHORITY_CREAT_TIME_STAMP  timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '创建时间',
    ROLE_AUTHORITY_UPDATE_TIME_STAMP timestamp                           null comment '更新时间',
    ROLE_AUTHORITY_IS_DELETE         tinyint(1)                          not null comment '是否被删除',
    constraint TB_ROLE_AUTHORITY_ROLE_AUTHORITY_ID_uindex
        unique (ROLE_AUTHORITY_ID)
)
    comment '角色权限中间表';

alter table security_role_authority
    add primary key (ROLE_AUTHORITY_ID);

create table security_user
(
    USER_ID                         int auto_increment comment '安全用户ID',
    USER_SECURITY_NAME              varchar(32)                         not null comment '安全账号',
    USER_PASS_WORD                  varchar(128)                         not null comment '用户密码',
    USER_IS_ENABLED                 tinyint(1)                          not null comment '账号是否启用',
    USER_IS_ACCOUNT_NON_EXPIRED     tinyint(1)                          not null comment '账号是否未过期',
    USER_IS_CREDENTIALS_NON_EXPIRED tinyint(1)                          not null comment '凭证是否未过期',
    USER_IS_ACCOUNT_NON_LOCKED      tinyint(1)                          not null comment '帐号是否未锁定',
    USER_CREAT_TIME_STAMP           timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '创建时间',
    USER_UPDATE_TIME_STAMP          timestamp                           null comment '修改时间',
    USER_IS_DELETE                  tinyint(1)                          not null comment '该账号是否已被删除',
    USER_NAME                       varchar(32)                         not null comment '用户名',
    USER_NUMBER                     varchar(32)                         not null comment '用户编号',
    USER_PHONE                      varchar(32)                         not null comment '用户手机号',
    USER_EMAIL                      varchar(32)                         not null comment '用户邮箱',
    constraint TB_USER_USER_ID_uindex
        unique (USER_ID),
    constraint TB_USER_USER_NAME_uindex
        unique (USER_SECURITY_NAME),
    constraint security_user_USER_EMAIL_uindex
        unique (USER_EMAIL),
    constraint security_user_USER_NUMBER_uindex
        unique (USER_NUMBER),
    constraint security_user_USER_PHONE_uindex
        unique (USER_PHONE)
)
    comment '安全用户表';

alter table security_user
    add primary key (USER_ID);

create table security_user_role
(
    USER_ROLE_ID                int auto_increment comment 'ID',
    USER_ID                     int                                 not null comment '用户ID',
    ROLE_ID                     int                                 not null comment '角色ID',
    USER_ROLE_CREAT_TIME_STAMP  timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '角色分配时间
',
    USER_ROLE_UPDATE_TIME_STAMP timestamp                           null comment '角色的修改时间',
    USER_ROLE_IS_DELETE         tinyint(1)                          not null comment '是否被删除',
    constraint tb_user_role_USER_ROLE_ID_uindex
        unique (USER_ROLE_ID)
)
    comment '用户角色中间表';

alter table security_user_role
    add primary key (USER_ROLE_ID);

