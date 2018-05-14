package com.higitech.cmcpro.admin.modules.system.model.form;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.higitech.cmcpro.admin.modules.system.entity.CmcRole;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class RoleForm implements Serializable {
    private static final long serialVersionUID = -1850423715508972594L;

    private Long roleId;

    @NotBlank
    @Size(max = 20)
    private String roleName;

    @NotBlank
    @Size(max = 64)
    private String roleDesc;

    private Integer status;

    public CmcRole toCmcRole(){
        CmcRole cmcRole = new CmcRole();
        cmcRole.setRoleDesc(roleDesc);
        cmcRole.setRoleId(roleId);
        cmcRole.setRoleName(roleName);
        cmcRole.setStatus(status);
        return cmcRole;
    }
}
