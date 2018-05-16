package com.higitech.cmcpro.admin.modules.system.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.higitech.cmcpro.admin.modules.system.entity.CmcDictCategory;
import com.higitech.cmcpro.admin.modules.system.mapper.CmcDictCategoryMapper;
import com.higitech.cmcpro.admin.modules.system.service.ICmcDictCategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CmcDictCategoryServiceImpl extends ServiceImpl<CmcDictCategoryMapper, CmcDictCategory> implements ICmcDictCategoryService {
}
