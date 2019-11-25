package com.supergenius.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.supergenius.exception.CustomException;
import com.supergenius.mapper.AuthorityMapper;
import com.supergenius.mapper.RoleAuthorityMapper;
import com.supergenius.mapper.RoleMapper;
import com.supergenius.model.Role;
import com.supergenius.model.RoleAuthority;
import com.supergenius.service.IRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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
    private final AuthorityMapper authorityMapper;
    private final RoleAuthorityMapper roleAuthorityMapper;

    RoleServiceImpl(RoleMapper roleMapper, AuthorityMapper authorityMapper, RoleAuthorityMapper roleAuthorityMapper) {
        this.roleMapper = roleMapper;
        this.authorityMapper = authorityMapper;
        this.roleAuthorityMapper = roleAuthorityMapper;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class,
            CustomException.class})
    public boolean save(Role role, List<Integer> authorityIds) {
        if (!save(role)) {
            throw new CustomException("创建角色失败！");
        }
        AtomicInteger count = new AtomicInteger(0);
        authorityIds.forEach(authorityId -> {
            if (!authorityMapper.isExistsByAuthorityId(authorityId)) {
                throw new CustomException("权限不存在！");
            }

            count.addAndGet(roleAuthorityMapper.insert(new RoleAuthority().setRoleId(role.getRoleId())
                    .setAuthorityId(authorityId).setRoleAuthorityCreatTimeStamp(new Date())
                    .setRoleAuthorityIsDelete(false)));
        });
        if (count.get() < authorityIds.size()) {
            throw new CustomException("角色权限分配失败");
        }
        return true;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class,
            CustomException.class})
    public boolean save(Role role) {
        if (roleMapper.isExistsByRoleName(role.getRoleName())) {
            throw new CustomException("该角色已存在！");
        }
        role.setRoleCreatTimeStamp(new Date()).setRoleIsDelete(false);
        return super.save(role);
    }
}
