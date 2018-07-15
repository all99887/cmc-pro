package com.higitech.cmcpro.admin.pubsub.impl;

import com.higitech.cmcpro.admin.modules.system.entity.CmcLog;
import com.higitech.cmcpro.admin.modules.system.mapper.CmcLogMapper;
import com.higitech.cmcpro.admin.pubsub.ISubcriber;
import com.higitech.cmcpro.admin.pubsub.SubscribePublish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class LogSubcriber implements ISubcriber<CmcLog>{

    @Autowired
    private LogPubSub logPubSub;

    @Autowired
    private CmcLogMapper cmcLogMapper;

    @PostConstruct
    public void init(){
        this.subcribe(logPubSub);
    }

    public String name;

    public LogSubcriber() {
        super();
        this.name = "LogSubcriber";
    }

    public LogSubcriber(String name) {
        super();
        this.name = name;
    }


    @Override
    public void subcribe(SubscribePublish subscribePublish) {
        subscribePublish.subcribe(this);
    }

    @Override
    public void unSubcribe(SubscribePublish subscribePublish) {
        subscribePublish.unSubcribe(this);
    }

    @Override
    public void update(String publisher, CmcLog message) {
//        System.out.println(this.name+"收到"+publisher+"发来的消息:"+message.toString());
        cmcLogMapper.insert(message);
    }
}
