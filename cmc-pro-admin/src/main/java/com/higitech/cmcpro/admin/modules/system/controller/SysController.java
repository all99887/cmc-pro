package com.higitech.cmcpro.admin.modules.system.controller;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.higitech.cmcpro.admin.cache.SessionCache;
import com.higitech.cmcpro.admin.component.PicCaptchaComponent;
import com.higitech.cmcpro.admin.consts.NameConsts;
import com.higitech.cmcpro.admin.model.CmcModel;
import com.higitech.cmcpro.admin.modules.system.entity.CmcFunc;
import com.higitech.cmcpro.admin.modules.system.entity.CmcUser;
import com.higitech.cmcpro.admin.modules.system.model.form.LoginForm;
import com.higitech.cmcpro.admin.modules.system.service.ICmcUserService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@RestController
@RequestMapping("/system")
@Api("系统操作相关api")
@Slf4j
public class SysController {

    @Autowired
    private ICmcUserService cmcUserService;

    @Autowired
    private SessionCache sessionCache;

    @Autowired
    private PicCaptchaComponent picCaptchaComponent;

    @ApiOperation("web端用户登录")
    @PostMapping("/webLogin.do")
    public CmcModel webLogin(@Validated @RequestBody LoginForm loginForm, HttpSession session){
        String captchaReal = Objects.toString(session.getAttribute(NameConsts.SessionKeys.PIC_CAPTCHA_KEY), "");
        if(StrUtil.equals(captchaReal, loginForm.getCaptcha(), true)){
            return login(loginForm);
        }
        CmcModel cmcModel = new CmcModel();
        cmcModel.addError("验证码错误");
        return cmcModel;
    }

    @ApiOperation("用户登录")
    @PostMapping("/login.do")
    public CmcModel login(@RequestBody LoginForm loginForm){
        CmcModel cmcModel = new CmcModel();
        CmcUser cmcUser = cmcUserService.login(loginForm.getUsername(), loginForm.getPassword());
        if(cmcUser == null){
            cmcModel.addError("用户名密码错误或用户已停用");
            return cmcModel;
        }
        List<CmcFunc> userPermissionList = cmcUserService.getUserPermission(cmcUser.getUserId());
        String token = RandomUtil.simpleUUID();
        sessionCache.set(token, NameConsts.SessionKeys.USER, cmcUser);
        Set<String> permissionSet = new HashSet<>();
        CollUtil.forEach(userPermissionList.iterator(), (CmcFunc value, int index)->
                permissionSet.add(value.getFuncUrlPre())
        );
        sessionCache.set(token, NameConsts.SessionKeys.USER_PERMISSION, permissionSet);
        cmcModel.set("token", token);
        cmcModel.set("realName", cmcUser.getRealName());
        return cmcModel;
    }

    @ApiOperation("用于前台请求一下查看token是否过期")
    @PostMapping("/loginStatus")
    public CmcModel loginStatus(){
        CmcModel cmcModel = new CmcModel();
        return cmcModel;
    }

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

    @ApiOperation("web端获取图形验证码")
    @PostMapping("/webPicCaptcha.do")
    public CmcModel webPicCaptcha(HttpSession session, HttpServletResponse response) {
        CmcModel cmcModel = new CmcModel();
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            String certCode = picCaptchaComponent.outputVerifyImage(80, 36, baos, 4);
            byte[] bytes = baos.toByteArray();
            String imageData = Base64.encode(bytes);
            cmcModel.set("pic", imageData);
            session.setAttribute(NameConsts.SessionKeys.PIC_CAPTCHA_KEY, certCode);
        } catch (IOException e) {
            log.error("生成图形验证码错误", e);
            cmcModel.addError("生成图形验证码错误");
        }
        return cmcModel;
    }

    @ApiOperation("用户注销")
    @PostMapping("/logout")
    public CmcModel logout(@RequestHeader("cmcProToken") String token) {
        CmcModel cmcModel = new CmcModel();
        sessionCache.invalidate(token);
        return cmcModel;
    }

}
