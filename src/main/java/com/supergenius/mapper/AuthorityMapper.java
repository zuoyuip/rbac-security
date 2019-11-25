package com.supergenius.mapper;

import com.supergenius.model.Authority;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * <p>
 * 权限表 Mapper 接口
 * </p>
 *
 * @author zuoyu
 * @since 2019-11-22
 */
@Repository
public interface AuthorityMapper extends BaseMapper<Authority> {

    /**
     * 根据权限Id查询是否有该记录
     *
     * @param authorityId - 权限Id
     * @return - boolean
     */
    boolean isExistsByAuthorityId(Serializable authorityId);
}
