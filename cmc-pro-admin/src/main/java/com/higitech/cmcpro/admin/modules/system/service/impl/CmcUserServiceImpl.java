package com.higitech.cmcpro.admin.modules.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.higitech.cmcpro.admin.modules.system.entity.CmcUser;
import com.higitech.cmcpro.admin.modules.system.mapper.CmcUserMapper;
import com.higitech.cmcpro.admin.modules.system.service.ICmcUserService;
import com.higitech.cmcpro.admin.util.PwdUtil;
import org.springframework.stereotype.Service;

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

    public CmcUser login(String username, String password) {
        CmcUser cmcUser = new CmcUser();
        cmcUser.setUserName(username);
        CmcUser cmcUserDb = selectOne(new EntityWrapper<CmcUser>(cmcUser));
        if(cmcUserDb == null){
            return null;
        }
        if(!PwdUtil.comparePwd(password, cmcUserDb.getPassword())){
            return null;
        }
        return cmcUserDb;
    }
}
