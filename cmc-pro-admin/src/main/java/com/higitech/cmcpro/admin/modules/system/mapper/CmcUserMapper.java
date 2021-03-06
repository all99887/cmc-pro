package com.higitech.cmcpro.admin.modules.system.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.higitech.cmcpro.admin.modules.system.entity.CmcFunc;
import com.higitech.cmcpro.admin.modules.system.entity.CmcRole;
import com.higitech.cmcpro.admin.modules.system.entity.CmcUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liuyanxiang
 * @since 2018-04-21
 */
public interface CmcUserMapper extends BaseMapper<CmcUser> {

    List<CmcFunc> getUserPermission(@Param("userId") long userId);

    List<CmcRole> getUserRoleRel(@Param("userId") long userId);

    void delUserRoleRel(@Param("userId") long userId);

    void addUserRoleRel(@Param("userId") long userId, @Param("roleIds") String[] roleIds);
}
