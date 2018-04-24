package com.higitech.cmcpro.admin.modules.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.higitech.cmcpro.admin.consts.CodeConsts;
import com.higitech.cmcpro.admin.modules.system.entity.CmcFunc;
import com.higitech.cmcpro.admin.modules.system.entity.CmcUser;
import com.higitech.cmcpro.admin.modules.system.mapper.CmcUserMapper;
import com.higitech.cmcpro.admin.modules.system.service.ICmcFuncService;
import com.higitech.cmcpro.admin.modules.system.service.ICmcUserService;
import com.higitech.cmcpro.admin.util.PwdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liuyanxiang
 * @since 2018-04-21
 */
@Service
public class CmcUserServiceImpl extends ServiceImpl<CmcUserMapper, CmcUser> implements ICmcUserService {

    @Autowired
    private ICmcFuncService cmcFuncService;

    public CmcUser login(String username, String password) {
        CmcUser cmcUser = new CmcUser();
        cmcUser.setUserName(username);
        CmcUser cmcUserDb = selectOne(new EntityWrapper<>(cmcUser));
        if(cmcUserDb == null){
            return null;
        }
        if(cmcUserDb.getStatus() != CodeConsts.CmcUser.USER_STATUS_ACTIVE){
            return null;
        }
        if(!PwdUtil.comparePwd(password, cmcUserDb.getPassword())){
            return null;
        }
        return cmcUserDb;
    }

    public List<CmcFunc> getUserPermission(long userId) {
        if(userId == 1){
            CmcFunc cmcFunc = new CmcFunc();
            cmcFunc.setStatus(CodeConsts.CmcFunc.FUNC_STATUS_ACTIVE);
            return cmcFuncService.selectList(new EntityWrapper<>(cmcFunc));
        } else {
            return baseMapper.getUserPermission(userId);
        }
    }
}
