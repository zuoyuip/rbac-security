# rbac-security
[博客地址](https://my.oschina.net/zuoyuip/blog/3143273)

> ### 这是一个基于简单的RBAC模型，结合Spring Security开发的权限管理模块。

## 一、RBAC模型介绍

* *RBAC是Role-Based Access Control的缩写，意思就是基于角色的权限访问控制。*

**基本思想：** 对系统的各种权限不是直接授予具体的用户，而是在用户集合与权限集合之间建立一个角色集合。每一种角色对应一组相应的权限。一旦用户被分配了适当的角色后，该用户就拥有此角色的所有操作权限。 同样用户被分配了多个适当的角色，那么该用户就拥有了被分配多个角色的所有权限。

**优点：** 不必在每次创建用户时都进行分配权限的操作，只要分配用户相应的角色即可，而且角色的权限变更比用户的权限变更要少得多，这样将**简化**用户的权限管理，减少系统的开销。

**该系统的RBAC模型——**

![Image of RBAC](https://github.com/zuoyuip/rbac-security/blob/master/UML/RBAC_model.png?raw=true)

---------

## 二、项目思想
本项目将对用户、角色、权限三者之间的关联状态概念交给系统管理员与数据库，对外依然具有RBAC的概念。但是在项目的权限管理中，项目只维护用户与权限的关系。
* 当用户登录成功后立即在数据库查询该用户所具有的角色，再根据所拥有的角色查询对应的权限——然后将这些权限赋予用户。
* 该项目的所有接口都是基于权限级别的身份校验，而非角色级别的身份校验。
* **优点：** 节省了对用户与角色、角色与权限之间关系的维护，对身份与接口之间的校验细化程度高，粒度级小。

---------

## 三、项目技术架构
* [ ]  **开发工具：** Maven（项目构建管理）
* [ ]  **开发环境：** JDK8、MySql5.5.25
* [ ]  **技术选型** ——
- 核心框架：SpringBoot 2.2.1.RELEASE
- 视图框架：SpringMVC 2.2.1.RELEASE
- 安全框架：SpringSecurity 2.2.1.RELEASE
- 持久层框架：Mybatis 3.5.2 
- 数据库连接池：Druid 1.1.10 
- 快速开发插件：Mybatis-Plus 3.2.0
- API构建工具：Swagger 2 2.9.2

---------

## 四、需求说明
1. 一个用户可以拥有多个角色，该用户需拥有这些角色所具有权限的并集。
2. 权限的控制在于两方面——前端目录菜单的展示、后端接口的访问拦截。
3. 用户登录成功后立即返回该用户可访问的菜单结构。

## 五、项目结构
```
management-security
│ 
├─config  配置    
│    ├─SwaggerConfig.java  关于Swagger2的配置类 
│    └─WebConfig.java  关于Web的配置类
│    
├─controller  视图 
│  
├─exception  异常
│    
├─generator  代码生成 
│    
├─mapper  Mybatis接口
│
├─model  模型
│  └─vo  业务模型
│    
├─security 权限模块
│    ├─constants 常量
│    │    └─SecurityConstants.java 关于权限的常量设置
│    │    
│    ├─handler 拦截器
│    │    ├─AuthenticationFailureHandlerImpl.java   认证失败的行为
│    │    └─AuthenticationSuccessHandlerImpl.java   认证成功的行为 
│    │    
│    ├─impl security的接口实现
│    │  └─UserDetailsServiceImpl.java  用户认证实现
│    │
│    └─SecurityConfig.java  权限配置类
│
├─service  业务接口
│  └─impl  业务接口实现
│   
├─utils  工具包
│  └─Result.java  结果JSON封装
│ 
│        
│       
│ 
├──resources 
│     ├─mapper   MyBatis文件
│     │
│     ├─db  MySql文件
│     │ └─data MySql数据文件  
│     │
│     └─application.yml   全局配置文件
│
```
---------

## 六、重点代码解析
### （**注意：** 这里我只展示重点代码，具体代码参考文初的GitHub地址）
#### *1、权限数据模型*
```java
/**
 * 权限表
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
```
- 这里的字段**权限名称**、**权限目录名称**、**权限菜单名称**、**权限目录URL**，其中**权限名称**是为了`Spring-Security`控制接口的拦截；**权限目录名称**、**权限菜单名称**、**权限目录URL**是为了给前端该用户可访问的菜单。

#### *2、用户数据模型*
```java
/**
 * 安全用户表
 *
 * @author zuoyu
 * @since 2019-11-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("security_user")
@ApiModel(value = "User对象", description = "安全用户表")
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

    @TableField(exist = false)
    private List<Authority> authorityBeans;


    public User(String userSecurityName, String userPassWord, String userName, String userNumber, String userPhone, String userEmail) {
        this.userSecurityName = userSecurityName;
        this.userPassWord = userPassWord;
        this.userName = userName;
        this.userNumber = userNumber;
        this.userPhone = userPhone;
        this.userEmail = userEmail;
    }

    @Override
    protected Serializable pkVal() {
        return this.userId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.authorityBeans == null || this.authorityBeans.isEmpty()) {
            return new HashSet<>(0);
        }
        return authorityBeans.stream().map(authority ->
                new SimpleGrantedAuthority(authority.getAuthorityName()))
                .collect(Collectors.toCollection(HashSet::new));
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
}
```
在这段代码中——
- 实现`org.springframework.security.core.userdetails.UserDetails`接口，将其帐号状态的一系列方法实现并对应数据库字段。
- 重点在添加字段`authorityBeans`，是权限`Authority`的集合，在重写的`Collection<? extends GrantedAuthority> getAuthorities()`方法中，提取`Authority`的字段`authorityName`并将其包装为`SimpleGrantedAuthority`权限对象，交给`Spring-Security`维护用户权限。

#### *3、菜单结构模型*
```java
/**
 * @author : zuoyu
 * @description : 菜单
 * @date : 2019-11-26 14:11
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "Menu对象", description = "菜单")
public class MenuVO {

    @ApiModelProperty(value = "菜单名称")
    private String name;

    @ApiModelProperty(value = "URL路径")
    private String url;

}
```
```java
/**
 * @author : zuoyu
 * @description : 目录
 * @date : 2019-11-26 14:15
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Content对象", description="目录")
public class ContentVO {

    @ApiModelProperty(value = "目录名称")
    private String name;

    @ApiModelProperty(value = "菜单列表")
    private List<MenuVO> menuList;

    public ContentVO(String name) {
        this.name = name;
    }
}
```
- 这两个`VO`类是为了构建返回给前端标准的*目录-菜单*结构，目标结构效果：
```json
 [
        {
            "name": "孵化器管理",
            "menuList": [
                {
                    "name": "孵化器内容管理",
                    "url": "incubator/content"
                },
                {
                    "name": "入住企业管理",
                    "url": "incubator/company"
                },
                {
                    "name": "创业端轮播图管理",
                    "url": "incubator/rotations/entrepreneurial"
                },
                {
                    "name": "投资端轮播图管理",
                    "url": "incubator/rotations/investment"
                },
                {
                    "name": "会议室管理",
                    "url": "incubator/conference"
                },
                {
                    "name": "路演管理",
                    "url": "incubator/roadshow"
                },
                {
                    "name": "课程管理",
                    "url": "incubator/course"
                }
            ]
        },
        {
            "name": "订单管理",
            "menuList": [
                {
                    "name": "会员订单管理",
                    "url": "order/members"
                },
                {
                    "name": "保荐人订单管理",
                    "url": "order/sponsor"
                },
                {
                    "name": "推荐人订单管理",
                    "url": "order/referees"
                },
                {
                    "name": "会议室预约订单管理",
                    "url": "order/conference"
                },
                {
                    "name": "路演活动订单管理",
                    "url": "order/roadshow"
                },
                {
                    "name": "企业培训订单管理",
                    "url": "order/training"
                }
            ]
        }
    ]
```

#### *4、用户源与权限的注入*
```java
/**
 * @author : zuoyu
 * @description : 认证实现
 * @date : 2019-11-26 09:53
 **/
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final IUserService iUserService;

    public UserDetailsServiceImpl(IUserService iUserService) {
        this.iUserService = iUserService;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = iUserService.loadUserByUsername(userName);
        if (user == null){
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        List<Authority> authorities = iUserService.selectAuthoritiesById(user.getUserId());
        return user.setAuthorityBeans(authorities);
    }
}
```
在这段代码中——
- 实现`org.springframework.security.core.userdetails.UserDetailsService`接口，自定义用户源。
- 重点是认证成功后，从数据库获取该用户的所有权限，自然是*用户->角色->权限*，`SQL`语句：
```sql
SELECT sa.AUTHORITY_ID                AS AUTHORITY_ID,
               sa.AUTHORITY_NAME              AS AUTHORITY_NAME,
               sa.AUTHORITY_CONTENT           AS AUTHORITY_CONTENT,
               sa.AUTHORITY_MENU              AS AUTHORITY_MENU,
               sa.AUTHORITY_MENU_URL          AS AUTHORITY_MENU_URL,
               sa.AUTHORITY_CREAT_TIME_STAMP  AS AUTHORITY_CREAT_TIME_STAMP,
               sa.AUTHORITY_UPDATE_TIME_STAMP AS AUTHORITY_UPDATE_TIME_STAMP,
               sa.AUTHORITY_IS_DELETE         AS AUTHORITY_IS_DELETE
        FROM security.security_user su,
             security.security_user_role sur,
             security.security_role sr,
             security.security_role_authority sra,
             security.security_authority sa
        WHERE sa.AUTHORITY_ID = sra.AUTHORITY_ID
          AND sa.AUTHORITY_IS_DELETE = 0
          AND sra.ROLE_ID = sr.ROLE_ID
          AND sra.ROLE_AUTHORITY_IS_DELETE = 0
          AND sr.ROLE_ID = sur.ROLE_ID
          AND sr.ROLE_IS_DELETE = 0
          AND sur.USER_ID = su.USER_ID
          AND sur.USER_ROLE_IS_DELETE = 0
          AND su.USER_IS_DELETE = 0
          AND su.USER_ID = #{userId}
```

#### *5、对应权限菜单的返回*
```java
private List<ContentVO> contentBuilder(Map<String, List<ContentStructure>> contentGroup) {
        //        根据分组组装对象
        return contentGroup.keySet().parallelStream().map(key -> {
            ContentVO contentVO = new ContentVO(key);
            List<MenuVO> menuList = new ArrayList<>(contentGroup.get(key).size());
            menuList.addAll(contentGroup.get(key).stream().map(contentStructure ->
                    new MenuVO(contentStructure.getMenuName(), contentStructure.getUrl()))
                    .collect(Collectors.toList()));
            return contentVO.setMenuList(menuList);
        }).collect(Collectors.toList());
    }

   /**
     * 根据用户查询对应的目录结构
     * @param user -
     * @return List<Content>
     */
public List<ContentVO> getContentsByUser(User user) {
        List<Authority> authorities = user.getAuthorityBeans();
        Map<String, List<ContentStructure>> contentGroup = authorities.stream().map(authority ->
                new ContentStructure(authority.getAuthorityContent(), authority.getAuthorityMenu(),
                        authority.getAuthorityMenuUrl())).collect(Collectors.groupingBy(ContentStructure::getContentName));
        return contentBuilder(contentGroup);
    }

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
class ContentStructure {

    private String contentName;

    private String menuName;

    private String url;
}
```
在这段代码中——
- 利用`ContentStructure`类和`stream`流的`Collectors.groupingBy(Object object)`方法，根据`Authority`类的`contentName`字段，以目录名称进行分组，组建*目录-菜单*结构的`List<ContentVO>`。
```java
/**
 * @author : zuoyu
 * @description : 登陆成功行为
 * @date : 2019-11-21 11:19
 **/
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Component
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

    private final IUserService iUserService;

    public AuthenticationSuccessHandlerImpl(IUserService iUserService) {
        this.iUserService = iUserService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        User user = (User) authentication.getPrincipal();
        List<ContentVO> contentList = iUserService.getContentsByUser(user);
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        ServletOutputStream servletOutputStream = response.getOutputStream();
        Result result = Result.detail("登陆成功", contentList);
        byte[] bytes = new ObjectMapper().writeValueAsBytes(result);
        servletOutputStream.write(bytes);
        servletOutputStream.flush();
        servletOutputStream.close();
    }
}
```
在这段代码中——
- 实现`org.springframework.security.web.authentication.AuthenticationSuccessHandler`定义登录成功后的行为；
- 从已登录的身份`Authentication`中获取`User`，根据`User`的`authorityBeans`构建菜单结构并返回。

---------
## 七、关于 *用户—角色* 、 *角色—权限* 关系的修改及查询效率问题
该权限系统一共五张表，其中两张为中间表。当一个用户登录成功后，获取权限的步骤为——
1. 根据**用户ID**去**用户角色中间表**获取对应的**角色ID**集（不走主键）；
2. 根据**角色ID**集去**角色权限中间表**获取这些**角色ID**集所对应的**权限ID**集（不走主键）；
3. 根据这些**权限ID**集获取权限信息。
###### 这里有两处不走主键，所以当数据量大的时候会影响查询速度，（这里不考虑数据库索引优化），且在实际业务中一条数据是不会被真正删除的，所以当一个用户的角色修改或者一个角色的权限修改时，我们肯定不能简单粗暴的全部删除再添加。这里我利用差集的方案避免了对修改时重复数据的删除与添加。
###### 以角色的权限修改为例——
```java
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class,
            CustomException.class})
    public boolean updateRole(Integer userId, List<Integer> roleIds) {
//        在这里，取原角色的集合与新角色的差集进行删除
        List<Integer> oldRoleIds = userRoleMapper.selectRoleByUserId(userId);
        List<UserRole> oldUserRoles = oldRoleIds.stream().filter(roleId -> !roleIds.contains(roleId))
                .map(id -> new UserRole(userId, id)).collect(Collectors.toList());
        if (oldUserRoles.size() > 0) {
            if (!userRoleMapper.deleteUserRoles(oldUserRoles)) {
                throw new CustomException("更新角色错误：删除");
            }
        }
//        在这里，取新角色的集合与原角色的差集进行添加
        List<UserRole> newUserRoles = roleIds.stream().filter(roleId -> !oldRoleIds.contains(roleId))
                .map(id -> new UserRole(userId, id)).collect(Collectors.toList());
        if (newUserRoles.size() > 0) {
            if (!saveBatch(newUserRoles)) {
                throw new CustomException("更新角色错误：添加");
            }
        }
        return true;
    }
```
- 当然对角色的删除与添加都是批处理，减少与数据库的交互。
## 八、本地测试
- 通过`git clone`源码
- 修改`application.yml`文件，更新MySQL账号和密码
- **第一次启动：** 创建数据库`security`，数据库编码为UTF-8，并将`application.yml`文件中的`initialization-mode:`设置为`always`，会自动建表和插入数据
- **已有数据库和表：** 启动项目之前确保`application.yml`文件中的`initialization-mode:`设置为`never`
- 在management-security目录下，执行`mvn spring-boot:run`
- 本项目地址接口地址为：`http://localhost:8080/management`
- 测试接口浏览器打开`http://localhost:8080/management/swagger-ui.html`
- **注：** idea、eclipse需安装`lombok`插件，不然会提示找不到`Model`的`get` 、`set`方法
* [ ]  **已有账户与角色：** 
```text
①——账号：admin  密码：iPadAir 角色：Admin
②——账号：user  密码：123456   角色：User
③——账号：opera 密码：123456   角色：Incubator、Opera
```
