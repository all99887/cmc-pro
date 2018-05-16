package com.higitech.cmcpro.admin.modules.system.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.higitech.cmcpro.admin.modules.system.entity.CmcFunc;
import com.higitech.cmcpro.admin.modules.system.mapper.CmcFuncMapper;
import com.higitech.cmcpro.admin.modules.system.service.ICmcFuncService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class CmcFuncServiceImpl extends ServiceImpl<CmcFuncMapper, CmcFunc> implements ICmcFuncService {

}
