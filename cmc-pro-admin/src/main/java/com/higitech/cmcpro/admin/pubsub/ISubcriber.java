package com.higitech.cmcpro.admin.pubsub;

public interface ISubcriber<T> {

    /**
     * @Description: 订阅
     * @param: subscribePublish 订阅器
     */
    void subcribe(SubscribePublish subscribePublish);
    /**
     * @Description: 退订
     * @param: subscribePublish 订阅器
     */
    void unSubcribe(SubscribePublish subscribePublish);
    /**
     * @Description: 接收消息
     * @param: publisher 发布者
     * @param: message 消息
     */
    void update(String publisher,T message);

}
