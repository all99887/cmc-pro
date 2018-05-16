package com.higitech.cmcpro.admin.modules.system.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

@Data
public class CmcDictCategory {

    @TableId(value = "dict_category_key", type = IdType.INPUT)
    private String dictCategoryKey;

    private String dictCategoryDesc;

}
