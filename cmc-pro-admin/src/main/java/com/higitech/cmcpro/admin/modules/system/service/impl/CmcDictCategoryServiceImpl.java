package com.higitech.cmcpro.admin.modules.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.higitech.cmcpro.admin.modules.system.entity.CmcDictCategory;
import com.higitech.cmcpro.admin.modules.system.mapper.CmcDictCategoryMapper;
import com.higitech.cmcpro.admin.modules.system.service.ICmcDictCategoryService;
import com.higitech.cmcpro.admin.modules.system.service.ICmcDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;

@Service
@Transactional
public class CmcDictCategoryServiceImpl extends ServiceImpl<CmcDictCategoryMapper, CmcDictCategory> implements ICmcDictCategoryService {

    @Autowired
    private ICmcDictService cmcDictService;

    @Override
    public void delCategory(String[] ids) {
        deleteBatchIds(CollUtil.newArrayList(ids));
        Wrapper wrapper = new EntityWrapper();
        wrapper.in("dict_category_key", ids);
        cmcDictService.delete(wrapper);
    }
}
