package com.higitech.cmcpro.admin.modules.system.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;

@Data
public class CmcDict implements Serializable{
    private static final long serialVersionUID = -289145944954159834L;

    @TableId(value = "dict_id", type = IdType.AUTO)
    private Long dictId;

    private String dictCategoryKey;

    private String dictKey;

    private String dictValue;

    private String dictDesc;

    private Integer orderNum;

}
