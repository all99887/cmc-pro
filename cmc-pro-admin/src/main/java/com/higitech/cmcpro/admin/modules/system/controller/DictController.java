package com.higitech.cmcpro.admin.modules.system.controller;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.higitech.cmcpro.admin.model.CmcModel;
import com.higitech.cmcpro.admin.model.RequestModel;
import com.higitech.cmcpro.admin.modules.system.entity.CmcDict;
import com.higitech.cmcpro.admin.modules.system.entity.CmcDictCategory;
import com.higitech.cmcpro.admin.modules.system.entity.CmcRole;
import com.higitech.cmcpro.admin.modules.system.model.form.DictCategoryForm;
import com.higitech.cmcpro.admin.modules.system.model.form.DictForm;
import com.higitech.cmcpro.admin.modules.system.model.form.RoleForm;
import com.higitech.cmcpro.admin.modules.system.service.ICmcDictCategoryService;
import com.higitech.cmcpro.admin.modules.system.service.ICmcDictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api("数据字典管理api")
@RequestMapping("/cmcProAdmin/dict")
public class DictController {

    @Autowired
    private ICmcDictCategoryService cmcCategoryDictService;

    @Autowired
    private ICmcDictService cmcDictService;

    @ApiOperation("数据字典分类数据列表")
    @PostMapping("/category/list")
    public CmcModel categoryList(@RequestBody RequestModel requestModel){
        CmcModel cmcModel = new CmcModel();
        String dictCategoryKey = requestModel.getString("dictCategoryKey");
        Page<CmcDictCategory> page = new Page<>(requestModel.getPageIndex(), requestModel.getPageSize());
        Wrapper wrapper = new EntityWrapper();
        wrapper.like("dict_category_key", dictCategoryKey);
        cmcModel.setPage(cmcCategoryDictService.selectPage(page, wrapper));
        return cmcModel;
    }

    @ApiOperation("数据字典分类新增")
    @PostMapping("/category/add")
    @CacheEvict(value = "dict", key = "'dict'")
    public CmcModel categoryAdd(@Validated @RequestBody DictCategoryForm dictCategoryForm){
        CmcModel cmcModel = new CmcModel();
        CmcDictCategory query = new CmcDictCategory();
        query.setDictCategoryKey(dictCategoryForm.getDictCategoryKey());
        if (cmcCategoryDictService.selectOne(new EntityWrapper<>(query)) != null) {
            cmcModel.addError("该分类key已存在");
            return cmcModel;
        }
        cmcCategoryDictService.insert(dictCategoryForm.toDbEntity());
        return cmcModel;
    }

    @ApiOperation("数据字典分类编辑")
    @PostMapping("/category/edit")
    @CacheEvict(value = "dict", key = "'dict'")
    public CmcModel categoryEdit(@Validated @RequestBody DictCategoryForm dictCategoryForm) {
        CmcModel cmcModel = new CmcModel();
        cmcCategoryDictService.updateById(dictCategoryForm.toDbEntity());
        return cmcModel;
    }

    @ApiOperation("数据字典分类删除")
    @PostMapping("/category/del")
    @CacheEvict(value = "dict", key = "'dict'")
    public CmcModel categoryDel(@RequestBody RequestModel requestModel) {
        CmcModel cmcModel = new CmcModel();
        String idStrs = requestModel.getString("ids");
        String[] ids = idStrs.split(",");
        cmcCategoryDictService.delCategory(ids);
        return cmcModel;
    }


    @ApiOperation("数据字典数据列表")
    @PostMapping("/list")
    public CmcModel dictList(@RequestBody RequestModel requestModel){
        CmcModel cmcModel = new CmcModel();
        String categoryKey = requestModel.getString("categoryKey");
        Page<CmcDict> page = new Page<>(requestModel.getPageIndex(), requestModel.getPageSize());
        CmcDict query = new CmcDict();
        query.setDictCategoryKey(categoryKey);
        Wrapper<CmcDict> wrapper = new EntityWrapper<>(query);
        wrapper.orderBy("order_num");
        cmcModel.setPage(cmcDictService.selectPage(page, wrapper));
        return cmcModel;
    }

    @ApiOperation("数据字典新增")
    @PostMapping("/add")
    @CacheEvict(value = "dict", key = "'dict'")
    public CmcModel dictAdd(@Validated @RequestBody DictForm dictForm){
        CmcModel cmcModel = new CmcModel();
        CmcDict query = new CmcDict();
        query.setDictCategoryKey(dictForm.getDictCategoryKey());
        query.setDictKey(dictForm.getDictKey());
        if (cmcDictService.selectOne(new EntityWrapper<>(query)) != null) {
            cmcModel.addError("该字典key已存在");
            return cmcModel;
        }
        cmcDictService.insert(dictForm.toDbEntity());
        return cmcModel;
    }

    @ApiOperation("数据字典编辑")
    @PostMapping("/edit")
    @CacheEvict(value = "dict", key = "'dict'")
    public CmcModel dictEdit(@Validated @RequestBody DictForm dictForm) {
        CmcModel cmcModel = new CmcModel();
        CmcDict cmcDict = dictForm.toDbEntity();
        cmcDictService.updateById(cmcDict);
        return cmcModel;
    }

    @ApiOperation("数据字典删除")
    @PostMapping("/del")
    @CacheEvict(value = "dict", key = "'dict'")
    public CmcModel dictDel(@RequestBody RequestModel requestModel) {
        CmcModel cmcModel = new CmcModel();
        String idStrs = requestModel.getString("ids");
        String[] ids = idStrs.split(",");
        cmcDictService.deleteBatchIds(CollUtil.newArrayList(ids));
        return cmcModel;
    }

}
