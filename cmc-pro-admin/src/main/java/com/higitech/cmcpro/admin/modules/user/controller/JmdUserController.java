package com.higitech.cmcpro.admin.modules.user.controller;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.plugins.Page;
import com.higitech.cmcpro.admin.model.CmcModel;
import com.higitech.cmcpro.admin.model.RequestModel;
import com.higitech.cmcpro.admin.modules.user.model.JmdUser;
import com.higitech.cmcpro.admin.modules.user.service.IJmdUserService;
import com.higitech.cmcpro.admin.modules.user.model.form.JmdUserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/JmdUser")
public class JmdUserController {

    @Autowired
    private IJmdUserService serviceImpl;

    // 查询条件暂不支持生成
    @PostMapping("/list")
    public CmcModel categoryList(@RequestBody RequestModel requestModel){
        CmcModel cmcModel = new CmcModel();
        Page<JmdUser> page = new Page<>(requestModel.getPageIndex(), requestModel.getPageSize());
        cmcModel.setPage(serviceImpl.selectPage(page));
        return cmcModel;
    }

    // form校验暂不支持生成
    @PostMapping("/add")
    public CmcModel categoryAdd(@Validated @RequestBody JmdUserForm form){
        CmcModel cmcModel = new CmcModel();
        serviceImpl.insert(form.toDbEntity());
        return cmcModel;
    }

    // form校验暂不支持生成
    @PostMapping("/edit")
    public CmcModel categoryEdit(@Validated @RequestBody JmdUserForm form) {
        CmcModel cmcModel = new CmcModel();
        serviceImpl.updateById(form.toDbEntity());
        return cmcModel;
    }

    @PostMapping("/del")
    public CmcModel categoryDel(@RequestBody RequestModel requestModel) {
       CmcModel cmcModel = new CmcModel();
        String idStrs = requestModel.getString("ids");
        String[] ids = idStrs.split(",");
        ArrayList<String> idList = CollUtil.newArrayList(ids);
        serviceImpl.deleteBatchIds(idList);
        return cmcModel;
    }
}
