package com.higitech.cmcpro.admin.modules.system.model.form;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

@Data
public class LoginForm implements Serializable{


    private static final long serialVersionUID = 6695639403256962202L;

    private String username;

    @JSONField(serialize=false)
    private String password;

    private String captcha;
}
