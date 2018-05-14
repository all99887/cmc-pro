package com.higitech.cmcpro.admin.modules.system.model.form;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.higitech.cmcpro.admin.consts.CodeConsts;
import com.higitech.cmcpro.admin.modules.system.entity.CmcFunc;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class FuncForm implements Serializable {


    private static final long serialVersionUID = 1439302069308195023L;

    private Long funcId;

    private Long pId;

    @NotBlank
    @Size(max = 20)
    private String funcName;

    @NotBlank
    @Size(max = 64)
    private String funcDesc;

    @Size(max = 32)
    private String funcUrlPre;

    @Size(max = 32)
    private String funcIndexUrl;

    @Max(999)
    private Integer orderNum;

    @Max(9)
    private Integer status;

    public CmcFunc toCmcFunc(){
        CmcFunc cmcFunc = new CmcFunc();
        cmcFunc.setFuncId(funcId);
        cmcFunc.setStatus(status);
        cmcFunc.setFuncDesc(funcDesc);
        cmcFunc.setFuncIndexUrl(funcIndexUrl);
        cmcFunc.setFuncName(funcName);
        cmcFunc.setFuncUrlPre(funcUrlPre);
        cmcFunc.setOrderNum(orderNum);
        cmcFunc.setPId(pId);
        return cmcFunc;
    }
}
