package com.higitech.cmcpro.admin.modules.system.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.higitech.cmcpro.admin.modules.system.entity.CmcFunc;
import com.higitech.cmcpro.admin.modules.system.entity.CmcRole;
import com.higitech.cmcpro.admin.modules.system.mapper.CmcRoleMapper;
import com.higitech.cmcpro.admin.modules.system.service.ICmcRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
@Transactional
public class CmcRoleServiceImpl extends ServiceImpl<CmcRoleMapper, CmcRole> implements ICmcRoleService {

    @Override
    public List<CmcFunc> getRoleFuncRel(long roleId) {
        return baseMapper.getRoleFuncRel(roleId);
    }

    @Override
    public void setRoleFuncRel(long roleId, String[] funcIds) {
        baseMapper.delRoleFuncRel(roleId);
        baseMapper.addRoleFuncRel(roleId, funcIds);
    }
}
