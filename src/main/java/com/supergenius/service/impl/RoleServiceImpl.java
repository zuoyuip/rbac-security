package com.supergenius.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.supergenius.exception.CustomException;
import com.supergenius.mapper.RoleMapper;
import com.supergenius.model.Role;
import com.supergenius.model.RoleAuthority;
import com.supergenius.service.IRoleAuthorityService;
import com.supergenius.service.IRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author zuoyu
 * @since 2019-11-22
 */
@Service
class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    private final RoleMapper roleMapper;
    private final IRoleAuthorityService iRoleAuthorityService;

    RoleServiceImpl(RoleMapper roleMapper, IRoleAuthorityService iRoleAuthorityService) {
        this.roleMapper = roleMapper;
        this.iRoleAuthorityService = iRoleAuthorityService;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class,
            CustomException.class})
    public boolean save(Role role, List<Integer> authorityIds) {
        if (!save(role)) {
            throw new CustomException("创建角色失败");
        }
        List<RoleAuthority> roleAuthorities = authorityIds.stream().map(authorityId->
                new RoleAuthority(role.getRoleId(), authorityId)).collect(Collectors.toList());
        if (!iRoleAuthorityService.saveBatch(roleAuthorities)){
            throw new CustomException("权限分配异常");
        }
        return true;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class,
            CustomException.class})
    public boolean save(Role role) {
        if (roleMapper.isExistsByRoleName(role.getRoleName())) {
            throw new CustomException("该角色已存在");
        }
        role.setRoleCreatTimeStamp(new Date()).setRoleIsDelete(false);
        return super.save(role);
    }

    @Override
    public boolean saveBatch(Collection<Role> entityList) {
        return super.saveBatch(entityList.stream().map(authority ->
                authority.setRoleCreatTimeStamp(new Date()).setRoleIsDelete(false))
                .collect(Collectors.toList()));
    }
}
