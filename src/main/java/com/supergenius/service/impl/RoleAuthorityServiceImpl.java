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
import java.util.List;
import java.util.stream.Collectors;

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
    public boolean updateAuthorities(Integer roleId, List<Integer> authorityIds) {
//        在这里，取原权限集合与新权限集合的差集进行删除
        List<Integer> oldAuthorityIds = roleAuthorityMapper.selectAuthoritiesByRoleId(roleId);
        List<RoleAuthority> oldRoleAuthorities = oldAuthorityIds.stream().filter(authorityId -> !authorityIds.contains(authorityId)).map(id ->
                new RoleAuthority(roleId, id)).collect(Collectors.toList());
        if (oldRoleAuthorities.size() > 0) {
            if (!roleAuthorityMapper.deleteRoleAuthorities(oldRoleAuthorities)) {
                throw new CustomException("更新权限错误：删除");
            }
        }
//        在这里，取新权限集合与原权限集合的差集进行添加
        List<RoleAuthority> newRoleAuthorities = authorityIds.stream().filter(authorityId -> !oldAuthorityIds.contains(authorityId)).map(id ->
                new RoleAuthority(roleId, id).setRoleAuthorityCreatTimeStamp(new Date())
                        .setRoleAuthorityIsDelete(false)).collect(Collectors.toList());
        if (newRoleAuthorities.size() > 0) {
            if (!saveBatch(newRoleAuthorities)) {
                throw new CustomException("更新权限错误：添加");
            }
        }
        return true;
    }
}
