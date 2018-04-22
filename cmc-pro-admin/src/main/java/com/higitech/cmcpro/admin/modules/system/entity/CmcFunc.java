package com.higitech.cmcpro.admin.modules.system.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author liuyanxiang
 * @since 2018-04-21
 */
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
     * 功能类型 0：菜单 1：功能
     */
    private Integer funcType;
    /**
     * 排序值
     */
    private Integer orderNum;
    /**
     * 功能状态  0：停用 1：启用
     */
    private Integer status;


    public Long getFuncId() {
        return funcId;
    }

    public void setFuncId(Long funcId) {
        this.funcId = funcId;
    }

    public Long getpId() {
        return pId;
    }

    public void setpId(Long pId) {
        this.pId = pId;
    }

    public String getFuncName() {
        return funcName;
    }

    public void setFuncName(String funcName) {
        this.funcName = funcName;
    }

    public String getFuncDesc() {
        return funcDesc;
    }

    public void setFuncDesc(String funcDesc) {
        this.funcDesc = funcDesc;
    }

    public Integer getFuncType() {
        return funcType;
    }

    public void setFuncType(Integer funcType) {
        this.funcType = funcType;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "CmcFunc{" +
        ", funcId=" + funcId +
        ", pId=" + pId +
        ", funcName=" + funcName +
        ", funcDesc=" + funcDesc +
        ", funcType=" + funcType +
        ", orderNum=" + orderNum +
        ", status=" + status +
        "}";
    }
}
