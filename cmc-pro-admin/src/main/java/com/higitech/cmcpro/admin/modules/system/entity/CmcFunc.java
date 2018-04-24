package com.higitech.cmcpro.admin.modules.system.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author liuyanxiang
 * @since 2018-04-21
 */
@Data
public class CmcFunc implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增主键
     */
    @TableId(value = "func_id", type = IdType.AUTO)
    private Long funcId;
    /**
     * 父节点id
     */
    private Long pId;
    private String funcName;
    /**
     * 功能描述
     */
    private String funcDesc;
    /**
     * 功能url前缀，用于判断权限
     */
    private String funcUrlPre;

    /**
     * 入口url
     */
    private String funcIndexUrl;

    /**
     * 排序值
     */
    private Integer orderNum;
    /**
     * 功能状态  0：停用 1：启用
     */
    private Integer status;

}
