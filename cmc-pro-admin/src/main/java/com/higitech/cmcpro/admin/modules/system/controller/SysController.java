package com.higitech.cmcpro.admin.modules.system.controller;

import com.higitech.cmcpro.admin.model.CmcModel;
import com.higitech.cmcpro.admin.modules.system.entity.form.LoginForm;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/system")
@Slf4j
public class SysController {

    @PostMapping("/login")
    public CmcModel login(@RequestBody LoginForm loginForm){
        log.info(loginForm.toString());
        CmcModel cmcModel = new CmcModel();
        System.out.println(BCrypt.checkpw("1212", loginForm.getPassword()));
        return cmcModel;

    }

}
