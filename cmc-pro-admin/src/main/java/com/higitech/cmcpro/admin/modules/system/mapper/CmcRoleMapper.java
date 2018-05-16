package com.higitech.cmcpro.admin.modules.system.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.higitech.cmcpro.admin.modules.system.entity.CmcFunc;
import com.higitech.cmcpro.admin.modules.system.entity.CmcRole;
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
public interface CmcRoleMapper extends BaseMapper<CmcRole> {

    List<CmcFunc> getRoleFuncRel(@Param("roleId") long roleId);

    void delRoleFuncRel(@Param("roleId") long roleId);

    void addRoleFuncRel(@Param("roleId") long roleId, @Param("funcIds") String[] funcIds);

}
