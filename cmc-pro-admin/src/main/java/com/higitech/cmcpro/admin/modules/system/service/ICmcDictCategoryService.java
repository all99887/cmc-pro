package com.higitech.cmcpro.admin.modules.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.higitech.cmcpro.admin.modules.system.entity.CmcDictCategory;

public interface ICmcDictCategoryService extends IService<CmcDictCategory> {

    void delCategory(String[] ids);
}
