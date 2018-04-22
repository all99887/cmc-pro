package com.higitech.cmcpro.admin.modules.system.controller;

import com.higitech.cmcpro.admin.consts.NameConsts;
import com.higitech.cmcpro.admin.model.CmcModel;
import com.higitech.cmcpro.admin.modules.system.entity.CmcUser;
import com.higitech.cmcpro.admin.modules.system.entity.form.LoginForm;
import com.higitech.cmcpro.admin.modules.system.service.ICmcUserService;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/system")
@Slf4j
public class SysController {

    @Autowired
    private ICmcUserService cmcUserService;

    @PostMapping("/login.do")
    public CmcModel login(@RequestBody LoginForm loginForm, HttpSession session){
        log.info(loginForm.toString());
        CmcModel cmcModel = new CmcModel();
        CmcUser cmcUser = cmcUserService.login(loginForm.getUsername(), loginForm.getPassword());
        if(cmcUser == null){
            cmcModel.addError("用户名密码错误");
            return cmcModel;
        }
        session.setAttribute(NameConsts.SessionKeys.USER, cmcUser);
        return cmcModel;
    }

}
