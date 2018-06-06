package com.higitech.cmcpro.admin.modules.system.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.higitech.cmcpro.admin.modules.system.entity.CmcLog;
import com.higitech.cmcpro.admin.modules.system.mapper.CmcLogMapper;
import com.higitech.cmcpro.admin.modules.system.service.ICmcLogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CmcLogServiceImpl extends ServiceImpl<CmcLogMapper, CmcLog> implements ICmcLogService {
}
