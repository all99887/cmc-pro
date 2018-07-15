package com.higitech.cmcpro.admin.modules.system.controller;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.higitech.cmcpro.admin.cache.SessionCache;
import com.higitech.cmcpro.admin.component.PicCaptchaComponent;
import com.higitech.cmcpro.admin.consts.NameConsts;
import com.higitech.cmcpro.admin.model.CmcModel;
import com.higitech.cmcpro.admin.modules.system.entity.CmcFunc;
import com.higitech.cmcpro.admin.modules.system.entity.CmcLog;
import com.higitech.cmcpro.admin.modules.system.entity.CmcUser;
import com.higitech.cmcpro.admin.modules.system.model.form.ChangePwdForm;
import com.higitech.cmcpro.admin.modules.system.model.form.LoginForm;
import com.higitech.cmcpro.admin.modules.system.service.ICmcUserService;
import com.higitech.cmcpro.admin.pubsub.impl.LogPubSub;
import com.higitech.cmcpro.admin.pubsub.impl.LogPublisher;
import com.higitech.cmcpro.admin.util.PwdUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;

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

//    @Autowired
//    private WebLogComponent webLogComponent;

    @Autowired
    private LogPublisher logLogPublisher;

    @Autowired
    private LogPubSub logPubSub;

    @ApiOperation("web端用户登录")
    @PostMapping("/webLogin.do")
    public CmcModel webLogin(@Validated @RequestBody LoginForm loginForm, HttpSession session, HttpServletRequest request){
        String captchaReal = Objects.toString(session.getAttribute(NameConsts.SessionKeys.PIC_CAPTCHA_KEY), "");
        if(StrUtil.equals(captchaReal, loginForm.getCaptcha(), true)){
            CmcModel cmcModel = login(loginForm);
            if(cmcModel.isSuccess()){
                String token = Objects.toString(cmcModel.get("token"), "");
                CmcUser cmcUser = sessionCache.get(token, NameConsts.SessionKeys.USER);

                CmcLog cmcLog = new CmcLog();
                cmcLog.setOperateTime(new Date());
                JSONObject params = JSON.parseObject(JSON.toJSONString(loginForm));
                params.remove("password");
                cmcLog.setParams(params.toJSONString());
                cmcLog.setUrl("/system/webLogin.do");
                cmcLog.setOperatorRealName(cmcUser.getRealName());
                cmcLog.setOperatorIp(ServletUtil.getClientIP(request));
                cmcLog.setOperatorId(cmcUser.getUserId());
                JSONObject returnResult = JSON.parseObject(JSON.toJSONString(cmcModel));
                returnResult.remove("token");
                cmcLog.setReturnResult(JSON.toJSONString(returnResult));
                logLogPublisher.publish(logPubSub, cmcLog, false);
//                webLogComponent.pushLog(cmcLog);
            }
            return cmcModel;
        }
        CmcModel cmcModel = new CmcModel();
        cmcModel.addError("common.error.captcha");
        return cmcModel;
    }

    @ApiOperation("用户登录")
    @PostMapping("/login.do")
    public CmcModel login(@RequestBody LoginForm loginForm){
        CmcModel cmcModel = new CmcModel();
        CmcUser cmcUser = cmcUserService.login(loginForm.getUsername(), loginForm.getPassword());
        if(cmcUser == null){
            cmcModel.addError("common.error.login");
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

    @ApiOperation("web端获取图形验证码")
    @PostMapping("/webPicCaptcha.do")
    public CmcModel webPicCaptcha(HttpSession session) {
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

    @ApiOperation("修改密码")
    @PostMapping("/changePwd")
    public CmcModel changePwd(@Validated @RequestBody ChangePwdForm changePwdForm, @RequestHeader("cmcProToken") String token) {
        CmcModel cmcModel = new CmcModel();
        CmcUser cmcUser = sessionCache.get(token, NameConsts.SessionKeys.USER);
        if(PwdUtil.comparePwd(changePwdForm.getOldPassword(), cmcUser.getPassword())){
            // 修改密码
            CmcUser updateUser = new CmcUser();
            updateUser.setUserId(cmcUser.getUserId());
            updateUser.setPassword(PwdUtil.pwdHash(changePwdForm.getPassword()));
            cmcUserService.updateById(updateUser);
        } else {
            //原密码错误
            cmcModel.addError("common.error.oldPwd");
        }
        return cmcModel;
    }

}
