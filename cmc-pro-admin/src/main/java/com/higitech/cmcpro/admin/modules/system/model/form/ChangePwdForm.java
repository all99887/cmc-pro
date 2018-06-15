package com.higitech.cmcpro.admin.modules.system.model.form;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class ChangePwdForm implements Serializable{
    private static final long serialVersionUID = -4510010734212607768L;

    @NotBlank
    @Size(min = 64, max = 64)
    @JSONField(serialize=false)
    private String oldPassword;

    @NotBlank
    @Size(min = 64, max = 64)
    @JSONField(serialize=false)
    private String password;
}
