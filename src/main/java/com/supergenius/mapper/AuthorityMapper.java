package com.supergenius.mapper;

import com.supergenius.model.Authority;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
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

    /**
     * 根据权限名称或权限菜单查询是否有该记录
     * @param authorityName - 权限名称（可以为null）
     * @param authorityMenu - 权限名称（可以为null）
     * @return boolean
     */
    boolean isExistsByAuthorityNameOrMenu(@Param("authorityName") String authorityName, @Param("authorityMenu") String authorityMenu);
}
