package com.higitech.cmcpro.admin.pubsub.impl;

import com.higitech.cmcpro.admin.modules.system.entity.CmcLog;
import com.higitech.cmcpro.admin.pubsub.IPublisher;
import com.higitech.cmcpro.admin.pubsub.SubscribePublish;
import org.springframework.stereotype.Component;

@Component
public class LogPublisher implements IPublisher<CmcLog> {

    private String name;

    public LogPublisher() {
        super();
        this.name = "LogPublisher";
    }

    public LogPublisher(String name) {
        super();
        this.name = name;
    }

    @Override
    public void publish(SubscribePublish subscribePublish, CmcLog message, boolean isInstantMsg) {
        subscribePublish.publish(this.name, message, isInstantMsg);
    }
}
