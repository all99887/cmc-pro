package com.higitech.cmcpro.admin.modules.system.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
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
public class CmcUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "user_id", type = IdType.AUTO)
    private Long userId;
    /**
     * 登录名
     */
    private String userName;
    /**
     * 登录密码
     */
    private String password;
    /**
     * 真实姓名
     */
    private String realName;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 电话
     */
    private String mobile;
    /**
     * 状态 0：停用  1：启用
     */
    private Integer status;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 最后登录时间
     */
    private Date lastLoginTime;

}
