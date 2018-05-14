package com.higitech.cmcpro.admin.modules.system.model.form;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class LoginForm implements Serializable{


    private static final long serialVersionUID = 6695639403256962202L;

    @NotBlank
    @Size(max = 20)
    private String username;

    @NotBlank
    @Size(min = 64, max = 64)
    @JSONField(serialize=false)
    private String password;

    @NotBlank
    @Size(max = 4)
    private String captcha;
}
