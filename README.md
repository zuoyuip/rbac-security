# rbac-security

> ### 这是一个基于简单的RBAC模型，结合Spring Security开发的权限管理模块。

## 一、RBAC模型介绍

* *RBAC是Role-Based Access Control的缩写，意思就是基于角色的权限访问控制。*

**基本思想：** 对系统的各种权限不是直接授予具体的用户，而是在用户集合与权限集合之间建立一个角色集合。每一种角色对应一组相应的权限。一旦用户被分配了适当的角色后，该用户就拥有此角色的所有操作权限。 同样用户被分配了多个适当的角色，那么该用户就拥有了被分配多个角色的所有权限。

**优点：** 不必在每次创建用户时都进行分配权限的操作，只要分配用户相应的角色即可，而且角色的权限变更比用户的权限变更要少得多，这样将<u>简化</u>用户的权限管理，减少系统的开销。 

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

## 四、项目结构
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

## 五、本地测试
- 通过git clone源码
- 修改`application.yml`文件，更新MySQL账号和密码
- **第一次启动：** 创建数据库security，数据库编码为UTF-8，并将`application.yml`文件中的`initialization-mode:`设置为`always`，会自动建表和插入数据
- **已有数据库和表：** 启动项目之前确保`application.yml`文件中的`initialization-mode:`设置为`never`
- 在management-security目录下，执行`mvn spring-boot:run`
- 本项目地址接口地址为：`http://localhost:8080/management`
- 测试接口浏览器打开`http://localhost:8080/management/swagger-ui.html`
- **注：** idea、eclipse需安装lombok插件，不然会提示找不到entity的get set方法
* [ ]  **已有账户与角色：** 
```text
①——账号：admin  密码：iPadAir 角色：Admin
②——账号：user  密码：123456   角色：User
③——账号：opera 密码：123456   角色：Incubator、Opera
```