package com.higitech.cmcpro.admin.modules.system.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.higitech.cmcpro.admin.modules.system.entity.CmcDict;
import com.higitech.cmcpro.admin.modules.system.mapper.CmcDictMapper;
import com.higitech.cmcpro.admin.modules.system.service.ICmcDictService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CmcDictServiceImpl extends ServiceImpl<CmcDictMapper, CmcDict> implements ICmcDictService {

}
