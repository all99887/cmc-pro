package com.higitech.cmcpro.admin.modules.system.model.form;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.higitech.cmcpro.admin.modules.system.entity.CmcDict;
import com.higitech.cmcpro.admin.modules.system.entity.CmcFunc;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class DictForm implements Serializable{
    private static final long serialVersionUID = 364040165873567862L;

    private Long dictId;

    @NotBlank
    @Size(max = 32)
    private String dictCategoryKey;

    @NotBlank
    @Size(max = 32)
    private String dictKey;

    @NotBlank
    @Size(max = 64)
    private String dictValue;

    @Size(max = 64)
    private String dictDesc;

    @Min(0)
    private Integer orderNum;

    public CmcDict toDbEntity(){
        CmcDict cmcFunc = new CmcDict();
        cmcFunc.setDictId(dictId);
        cmcFunc.setDictKey(dictKey);
        cmcFunc.setDictCategoryKey(dictCategoryKey);
        cmcFunc.setDictDesc(dictDesc);
        cmcFunc.setDictValue(dictValue);
        cmcFunc.setOrderNum(orderNum);
        return cmcFunc;
    }

}
