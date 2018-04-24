package com.higitech.cmcpro.admin.modules.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.higitech.cmcpro.admin.modules.system.entity.CmcFunc;
import com.higitech.cmcpro.admin.modules.system.entity.CmcUser;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liuyanxiang
 * @since 2018-04-21
 */
public interface ICmcUserService extends IService<CmcUser> {

    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     */
    CmcUser login(String username, String password);

    /**
     * 获取用户功能访问权限
     * @param userId
     * @return
     */
    List<CmcFunc> getUserPermission(long userId);
}
