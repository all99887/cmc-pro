package com.higitech.cmcpro.admin.modules.system.service.impl;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.higitech.cmcpro.admin.modules.system.entity.CmcDict;
import com.higitech.cmcpro.admin.modules.system.mapper.CmcDictMapper;
import com.higitech.cmcpro.admin.modules.system.service.ICmcDictService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.*;

@Service
@Transactional
public class CmcDictServiceImpl extends ServiceImpl<CmcDictMapper, CmcDict> implements ICmcDictService {

    @Override
    @Cacheable(value = "dict", key = "'dict'")
    public Map<String, List<Object>> getAllDict() {
        List<CmcDict> dictList = selectList(null);
        Map<String, List<Object>> dictInfoMap = new HashMap<>();
        for (CmcDict item : dictList) {
            String categoryKey = item.getDictCategoryKey();
            List<Object> list = null;
            if (dictInfoMap.containsKey(categoryKey)) {
                list = dictInfoMap.get(categoryKey);
            } else {
                list = new ArrayList<>();
                dictInfoMap.put(categoryKey, list);
            }
            Map<String, String> dict = new HashMap<>(2);
            dict.put("value", item.getDictKey());
            dict.put("label", item.getDictValue());
            list.add(dict);
        }
        return dictInfoMap;
    }


}
