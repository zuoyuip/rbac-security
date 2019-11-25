package com.supergenius.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.supergenius.exception.CustomException;
import com.supergenius.mapper.RoleAuthorityMapper;
import com.supergenius.model.RoleAuthority;
import com.supergenius.service.IRoleAuthorityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Set;

/**
 * <p>
 * 角色权限中间表 服务实现类
 * </p>
 *
 * @author zuoyu
 * @since 2019-11-22
 */
@Service
class RoleAuthorityServiceImpl extends ServiceImpl<RoleAuthorityMapper, RoleAuthority> implements IRoleAuthorityService {

    private final RoleAuthorityMapper roleAuthorityMapper;

    public RoleAuthorityServiceImpl(RoleAuthorityMapper roleAuthorityMapper) {
        this.roleAuthorityMapper = roleAuthorityMapper;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class,
            CustomException.class})
    public boolean updateAuthorities(Integer roleId, Set<Integer> authorityIds) {
//        在这里，取原权限集合与新权限集合的差集进行删除
        Set<Integer> oldAuthorityIds = roleAuthorityMapper.selectAuthoritiesByRoleId(roleId);
        oldAuthorityIds.stream().filter(authorityId -> !authorityIds.contains(authorityId)).forEach(id ->
                roleAuthorityMapper.deleteAuthoritiesById(roleId, id));
//        在这里，取新权限集合与原权限集合的差集进行添加
        authorityIds.stream().filter(authorityId -> !oldAuthorityIds.contains(authorityId)).forEach(id ->
                roleAuthorityMapper.insert(new RoleAuthority().setRoleId(roleId).setAuthorityId(id)
                        .setRoleAuthorityCreatTimeStamp(new Date()).setRoleAuthorityIsDelete(false)));
        return true;
    }
}
