package com.supergenius.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.supergenius.mapper.AuthorityMapper;
import com.supergenius.model.Authority;
import com.supergenius.service.IAuthorityService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author zuoyu
 * @since 2019-11-22
 */
@Service
class AuthorityServiceImpl extends ServiceImpl<AuthorityMapper, Authority> implements IAuthorityService {

    @Override
    public boolean save(Authority authority) {
        authority.setAuthorityCreatTimeStamp(new Date()).setAuthorityIsDelete(false);
        return super.save(authority);
    }

    @Override
    public boolean saveBatch(Collection<Authority> entityList) {
        return super.saveBatch(entityList.stream().map(authority ->
                authority.setAuthorityCreatTimeStamp(new Date()).setAuthorityIsDelete(false))
                .collect(Collectors.toList()));
    }
}
