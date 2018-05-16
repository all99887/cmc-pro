package com.higitech.cmcpro.admin.modules.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.higitech.cmcpro.admin.modules.system.entity.CmcFunc;
import com.higitech.cmcpro.admin.modules.system.entity.CmcRole;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liuyanxiang
 * @since 2018-04-21
 */
public interface ICmcRoleService extends IService<CmcRole> {

    /**
     * 获取角色功能访问权限
     * @param roleId
     * @return
     */
    List<CmcFunc> getRoleFuncRel(long roleId);

    /**
     * 编辑角色功能关系
     * @param roleId
     * @param funcIds
     * @return
     */
    void setRoleFuncRel(long roleId, String[] funcIds);

}
