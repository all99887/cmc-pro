package com.higitech.cmcpro.admin.modules.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.higitech.cmcpro.admin.modules.system.entity.CmcDict;

import java.util.List;
import java.util.Map;

public interface ICmcDictService extends IService<CmcDict> {

    Map<String, List<Object>> getAllDict();
}
