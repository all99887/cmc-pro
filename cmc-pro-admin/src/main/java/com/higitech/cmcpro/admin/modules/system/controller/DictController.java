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
import com.higitech.cmcpro.admin.modules.system.model.form.RoleForm;
import com.higitech.cmcpro.admin.modules.system.service.ICmcDictCategoryService;
import com.higitech.cmcpro.admin.modules.system.service.ICmcDictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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
        Page<CmcDictCategory> page = new Page<>(requestModel.getPageIndex(), requestModel.getPageSize());
        cmcModel.setPage(cmcCategoryDictService.selectPage(page));
        return cmcModel;
    }

    @ApiOperation("数据字典分类编辑")
    @PostMapping("/category/add")
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
    public CmcModel categoryEdit(@Validated @RequestBody DictCategoryForm dictCategoryForm) {
        CmcModel cmcModel = new CmcModel();
        cmcCategoryDictService.updateById(dictCategoryForm.toDbEntity());
        return cmcModel;
    }

    @ApiOperation("数据字典分类删除")
    @PostMapping("/category/del")
    public CmcModel categoryDel(@RequestBody RequestModel requestModel) {
        CmcModel cmcModel = new CmcModel();
        String idStrs = requestModel.getString("ids");
        String[] ids = idStrs.split(",");
        cmcCategoryDictService.deleteBatchIds(CollUtil.newArrayList(ids));
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
        cmcModel.setPage(cmcDictService.selectPage(page, wrapper));
        return cmcModel;
    }

}
