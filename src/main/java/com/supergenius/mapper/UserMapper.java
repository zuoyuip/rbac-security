package com.supergenius.mapper;

import com.supergenius.model.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 安全用户表 Mapper 接口
 * </p>
 *
 * @author zuoyu
 * @since 2019-11-22
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据用户名查询是否有该记录
     * @param userSecurityName -用户名
     * @return - boolean
     */
    boolean isExistsByUserSecurityName(String userSecurityName);
}
