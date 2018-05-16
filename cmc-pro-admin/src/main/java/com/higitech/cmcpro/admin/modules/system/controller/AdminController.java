package com.higitech.cmcpro.admin.modules.system.controller;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.higitech.cmcpro.admin.model.CmcModel;
import com.higitech.cmcpro.admin.model.RequestModel;
import com.higitech.cmcpro.admin.modules.system.entity.CmcFunc;
import com.higitech.cmcpro.admin.modules.system.entity.CmcRole;
import com.higitech.cmcpro.admin.modules.system.entity.CmcUser;
import com.higitech.cmcpro.admin.modules.system.model.form.FuncForm;
import com.higitech.cmcpro.admin.modules.system.model.form.RoleForm;
import com.higitech.cmcpro.admin.modules.system.model.form.UserForm;
import com.higitech.cmcpro.admin.modules.system.service.ICmcFuncService;
import com.higitech.cmcpro.admin.modules.system.service.ICmcRoleService;
import com.higitech.cmcpro.admin.modules.system.service.ICmcUserService;
import com.higitech.cmcpro.admin.util.PwdUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@Api("系统功能管理api（用户，角色，功能）")
@RequestMapping("/cmcProAdmin/")
public class AdminController {

    @Autowired
    private ICmcFuncService cmcFuncService;

    @Autowired
    private ICmcRoleService cmcRoleService;

    @Autowired
    private ICmcUserService cmcUserService;

    @ApiOperation("系统功能添加")
    @PostMapping("/func/add")
    public CmcModel funcAdd(@Validated @RequestBody FuncForm funcForm) {
        CmcModel cmcModel = new CmcModel();
        cmcFuncService.insert(funcForm.toDbEntity());
        return cmcModel;
    }

    @ApiOperation("系统功能编辑")
    @PostMapping("/func/edit")
    public CmcModel funcEdit(@Validated @RequestBody FuncForm funcForm) {
        CmcModel cmcModel = new CmcModel();
        cmcFuncService.updateById(funcForm.toDbEntity());
        return cmcModel;
    }

    @ApiOperation("系统功能删除")
    @PostMapping("/func/del")
    public CmcModel funcDel(@RequestBody RequestModel requestModel) {
        CmcModel cmcModel = new CmcModel();
        cmcFuncService.deleteById(requestModel.getLong("funcId"));
        return cmcModel;
    }

    @ApiOperation("角色列表获取")
    @PostMapping("/role/list")
    public CmcModel roleList(@RequestBody RequestModel requestModel) {
        CmcModel cmcModel = new CmcModel();
        Page<CmcRole> cmcRolePage = new Page<>(requestModel.getPageIndex(), requestModel.getPageSize());
        cmcModel.setPage(cmcRoleService.selectPage(cmcRolePage));
        return cmcModel;
    }

    @ApiOperation("角色添加")
    @PostMapping("/role/add")
    public CmcModel roleAdd(@Validated @RequestBody RoleForm roleForm) {
        CmcModel cmcModel = new CmcModel();
        CmcRole query = new CmcRole();
        query.setRoleName(roleForm.getRoleName());
        if (cmcRoleService.selectOne(new EntityWrapper<>(query)) != null) {
            cmcModel.addError("该角色已存在");
            return cmcModel;
        }
        cmcRoleService.insert(roleForm.toDbEntity());
        return cmcModel;
    }

    @ApiOperation("角色编辑")
    @PostMapping("/role/edit")
    public CmcModel roleEdit(@Validated @RequestBody RoleForm roleForm) {
        CmcModel cmcModel = new CmcModel();
        CmcRole query = new CmcRole();
        query.setRoleName(roleForm.getRoleName());
        CmcRole cmcRoleDb = cmcRoleService.selectOne(new EntityWrapper<>(query));
        if (cmcRoleDb != null && cmcRoleDb.getRoleId() != roleForm.getRoleId()) {
            cmcModel.addError("该角色已存在");
            return cmcModel;
        }
        cmcRoleService.updateById(roleForm.toDbEntity());
        return cmcModel;
    }

    @ApiOperation("角色删除")
    @PostMapping("/role/del")
    public CmcModel roleDel(@RequestBody RequestModel requestModel) {
        CmcModel cmcModel = new CmcModel();
        String idStrs = requestModel.getString("ids");
        String[] ids = idStrs.split(",");
        cmcRoleService.deleteBatchIds(CollUtil.newArrayList(ids));
        return cmcModel;
    }

    @ApiOperation("角色功能授权列表")
    @PostMapping("/role/funcRel/list")
    public CmcModel funcRelList(@RequestBody RequestModel requestModel) {
        CmcModel cmcModel = new CmcModel();
        long roleId = requestModel.getLong("roleId");
        List<CmcFunc> roleFuncList = cmcRoleService.getRoleFuncRel(roleId);
        List<CmcFunc> allFuncList = cmcUserService.getUserPermission(1);
        cmcModel.set("roleFuncList", roleFuncList);
        cmcModel.set("allFuncList", allFuncList);
        return cmcModel;
    }

    @ApiOperation("角色功能授权修改")
    @PostMapping("/role/funcRel/edit")
    public CmcModel funcRelEdit(@RequestBody RequestModel requestModel) {
        CmcModel cmcModel = new CmcModel();
        long roleId = requestModel.getLong("roleId");
        String funcIds = requestModel.getString("funcIds");
        cmcRoleService.setRoleFuncRel(roleId, funcIds.split(","));
        return cmcModel;
    }

    @ApiOperation("用户列表获取")
    @PostMapping("/user/list")
    public CmcModel userList(@RequestBody RequestModel requestModel) {
        CmcModel cmcModel = new CmcModel();
        Page<CmcUser> cmcRolePage = new Page<>(requestModel.getPageIndex(), requestModel.getPageSize());
        EntityWrapper<CmcUser> ew = new EntityWrapper();
        ew.ne("user_id", 1);
        cmcModel.setPage(cmcUserService.selectPage(cmcRolePage, ew));
        return cmcModel;
    }

    @ApiOperation("用户添加")
    @PostMapping("/user/add")
    public CmcModel userAdd(@Validated @RequestBody UserForm userForm) {
        CmcModel cmcModel = new CmcModel();
        CmcUser query = new CmcUser();
        query.setUserName(userForm.getUserName());
        if (cmcUserService.selectOne(new EntityWrapper<>(query)) != null) {
            cmcModel.addError("该用户名已存在");
            return cmcModel;
        }

        CmcUser cmcUserDb = userForm.toDbEntity();
        Date now = new Date();
        cmcUserDb.setCreateTime(now);
        cmcUserDb.setLastLoginTime(now);
        cmcUserDb.setPassword(PwdUtil.genDefaultPwd());
        cmcUserService.insert(cmcUserDb);
        return cmcModel;
    }

    @ApiOperation("用户编辑")
    @PostMapping("/user/edit")
    public CmcModel userEdit(@Validated @RequestBody UserForm userForm) {
        CmcModel cmcModel = new CmcModel();
        if (userForm.getUserId() == 1) {
            cmcModel.addError("不能编辑超级管理员");
            return cmcModel;
        }
        CmcUser query = new CmcUser();
        query.setUserName(userForm.getUserName());
        CmcUser cmcUserDb = cmcUserService.selectOne(new EntityWrapper<>(query));
        if (cmcUserDb != null && cmcUserDb.getUserId() != userForm.getUserId()) {
            cmcModel.addError("该用户名已存在");
            return cmcModel;
        }

        CmcUser cmcUserNew = userForm.toDbEntity();
        //下面这些不允许编辑，取目前db中的值覆盖
        cmcUserNew.setUserName(cmcUserDb.getUserName());
        cmcUserNew.setPassword(cmcUserDb.getPassword());
        cmcUserNew.setLastLoginTime(cmcUserDb.getLastLoginTime());
        cmcUserNew.setCreateTime(cmcUserDb.getCreateTime());

        cmcUserService.updateById(cmcUserNew);
        return cmcModel;
    }

    @ApiOperation("用户删除")
    @PostMapping("/user/del")
    public CmcModel userDel(@RequestBody RequestModel requestModel) {
        CmcModel cmcModel = new CmcModel();
        String idStrs = requestModel.getString("ids");
        String[] ids = idStrs.split(",");

        ArrayList<String> idList = CollUtil.newArrayList(ids);
        idList.remove("1");//不能删除超级管理员

        cmcUserService.deleteBatchIds(idList);
        return cmcModel;
    }

    @ApiOperation("用户角色关系获取")
    @PostMapping("/user/roleRel/list")
    public CmcModel roleRelList(@RequestBody RequestModel requestModel) {
        CmcModel cmcModel = new CmcModel();
        long userId = requestModel.getLong("userId");
        if (userId == 1) {
            cmcModel.addError("该功能不支持超级管理员");
            return cmcModel;
        }
        List<CmcRole> userRoleList = cmcUserService.getUserRoleRel(userId);
        CmcRole queryRole = new CmcRole();
        queryRole.setStatus(1);
        Wrapper<CmcRole> query = new EntityWrapper<>(queryRole);
        List<CmcRole> roleList = cmcRoleService.selectList(query);
        cmcModel.set("userRoleList", userRoleList);
        cmcModel.set("roleList", roleList);
        return cmcModel;
    }

    @ApiOperation("用户角色关系修改")
    @PostMapping("/user/roleRel/edit")
    public CmcModel roleRelEdit(@RequestBody RequestModel requestModel) {
        CmcModel cmcModel = new CmcModel();
        long userId = requestModel.getLong("userId");
        String roleIds = requestModel.getString("roleIds");
        cmcUserService.setUserRoleRel(userId, roleIds.split(","));
        return cmcModel;
    }


}
