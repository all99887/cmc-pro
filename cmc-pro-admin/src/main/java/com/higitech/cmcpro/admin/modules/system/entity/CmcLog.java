package com.higitech.cmcpro.admin.modules.system.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class CmcLog implements Serializable{
    private static final long serialVersionUID = 1946435583280344510L;

    @TableId(value = "log_id", type = IdType.AUTO)
    private Long logId;

    private Long operatorId;

    private String operatorRealName;

    private Date operateTime;

    private String operatorIp;

    private String url;

    private String params;

    private String returnResult;

}
