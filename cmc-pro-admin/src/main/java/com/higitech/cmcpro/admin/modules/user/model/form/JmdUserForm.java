package com.higitech.cmcpro.admin.modules.user.model.form;

import com.higitech.cmcpro.admin.modules.user.model.JmdUser;
import lombok.Data;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import java.io.Serializable;

@Data
public class JmdUserForm implements Serializable{

    private static final long serialVersionUID = 1L;

    private Long userId;
    private String userName;
    private String password;
    private Date createTime;
    private Integer status;

    public JmdUser toDbEntity(){
        JmdUser entity = new JmdUser();
        entity.setUserId(userId);
        entity.setUserName(userName);
        entity.setPassword(password);
        entity.setCreateTime(createTime);
        entity.setStatus(status);
        return entity;
    }
}
