package com.higitech.cmcpro.admin.modules.system.model.form;

import com.higitech.cmcpro.admin.modules.system.entity.CmcUser;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class UserForm implements Serializable{
    private static final long serialVersionUID = -6368134724952669449L;

    private Long userId;
    /**
     * 登录名
     */
    @NotBlank
    @Size(max = 32)
    private String userName;

    /**
     * 真实姓名
     */
    @Size(max = 32)
    private String realName;
    /**
     * 邮箱
     */
    @Size(max = 128)
    private String email;
    /**
     * 电话
     */
    @Size(max = 32)
    private String mobile;
    /**
     * 状态 0：停用  1：启用
     */
    private Integer status;

    public CmcUser toDbEntity(){
        CmcUser cmcUser = new CmcUser();
        cmcUser.setEmail(this.email);
        cmcUser.setMobile(this.mobile);
        cmcUser.setRealName(this.realName);
        cmcUser.setStatus(this.status);
        cmcUser.setUserName(this.userName);
        cmcUser.setUserId(this.userId);
        return cmcUser;
    }
}
