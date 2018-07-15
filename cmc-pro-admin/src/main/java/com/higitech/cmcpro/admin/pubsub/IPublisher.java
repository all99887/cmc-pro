package com.higitech.cmcpro.admin.pubsub;

public interface IPublisher<T> {

    /**
     * @Description: 向订阅器发布消息
     * @param subscribePublish 订阅器
     * @param message 消息
     * @param isInstantMsg	是否立即发送
     */
    void publish(SubscribePublish subscribePublish,T message,boolean isInstantMsg);

}
