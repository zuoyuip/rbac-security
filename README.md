# rbac-security

> ### 这是一个基于简单的RBAC模型，结合Spring Security开发的权限管理系统。

## 一、RBAC模型介绍

* *RBAC是Role-Based Access Control的缩写，意思就是基于角色的权限访问控制。*

**基本思想**对系统的各种权限不是直接授予具体的用户，而是在用户集合与权限集合之间建立一个角色集合。每一种角色对应一组相应的权限。一旦用户被分配了适当的角色后，该用户就拥有此角色的所有操作权限。 同样用户被分配了多个适当的角色，那么该用户就拥有了被分配多个角色的所有权限。

**优点：** 不必在每次创建用户时都进行分配权限的操作，只要分配用户相应的角色即可，而且角色的权限变更比用户的权限变更要少得多，这样将<u>简化</u>用户的权限管理，减少系统的开销。 

**该系统的RBAC模型——**

![Image of RBAC](https://github.com/zuoyuip/rbac-security/blob/master/UML/RBAC_model.png?raw=true)

## 二、项目流程

