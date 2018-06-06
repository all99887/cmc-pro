package com.higitech.cmcpro.admin.modules.common.controller;

import com.higitech.cmcpro.admin.cache.SessionCache;
import com.higitech.cmcpro.admin.consts.NameConsts;
import com.higitech.cmcpro.admin.model.CmcModel;
import com.higitech.cmcpro.admin.modules.system.entity.CmcFunc;
import com.higitech.cmcpro.admin.modules.system.entity.CmcUser;
import com.higitech.cmcpro.admin.modules.system.service.ICmcDictCategoryService;
import com.higitech.cmcpro.admin.modules.system.service.ICmcDictService;
import com.higitech.cmcpro.admin.modules.system.service.ICmcUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api("公共api，都有权访问")
@RequestMapping("/common")
public class CommonController {

    @Autowired
    private ICmcUserService cmcUserService;

    @Autowired
    private ICmcDictService cmcDictService;

    @Autowired
    private SessionCache sessionCache;

    @ApiOperation("获取菜单列表")
    @PostMapping("/menuList")
    public CmcModel funcList(@RequestHeader("cmcProToken") String token){
        CmcModel cmcModel = new CmcModel();
        CmcUser cmcUser = sessionCache.get(token, NameConsts.SessionKeys.USER);
        List<CmcFunc> userPermissionList = cmcUserService.getUserPermission(cmcUser.getUserId());
        cmcModel.set("funcList", userPermissionList);
        cmcModel.set("sysAdminOn", cmcUser.getUserId() == 1);
        return cmcModel;
    }

    @ApiOperation("用于前台请求一下查看token是否过期")
    @PostMapping("/loginStatus")
    public CmcModel loginStatus(){
        CmcModel cmcModel = new CmcModel();
        return cmcModel;
    }

    @ApiOperation("前台获取数据字典")
    @PostMapping("/dictList.do")
    public CmcModel dictList(){
        CmcModel cmcModel = new CmcModel();
        cmcModel.set("dict", cmcDictService.getAllDict());
        return cmcModel;
    }

}
