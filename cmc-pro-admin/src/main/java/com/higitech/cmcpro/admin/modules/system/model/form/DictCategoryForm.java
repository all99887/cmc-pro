package com.higitech.cmcpro.admin.modules.system.model.form;

import com.higitech.cmcpro.admin.modules.system.entity.CmcDictCategory;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class DictCategoryForm implements Serializable{
    private static final long serialVersionUID = -6664206805327517213L;

    @NotBlank
    @Size(max = 32)
    private String dictCategoryKey;

    @Size(max = 64)
    private String dictCategoryDesc;

    public CmcDictCategory toDbEntity(){
        CmcDictCategory cmcDictCategory = new CmcDictCategory();
        cmcDictCategory.setDictCategoryDesc(dictCategoryDesc);
        cmcDictCategory.setDictCategoryKey(dictCategoryKey);
        return cmcDictCategory;
    }
}
