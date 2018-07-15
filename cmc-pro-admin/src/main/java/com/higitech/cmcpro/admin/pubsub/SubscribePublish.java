package com.higitech.cmcpro.admin.pubsub;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class SubscribePublish<T> {

    //订阅器队列容量
    final int QUEUE_CAPACITY = 20;
    //订阅器存储队列
    private BlockingQueue<Msg> queue = new ArrayBlockingQueue<>(QUEUE_CAPACITY);
    //订阅者
    private List<ISubcriber> subcribers = new ArrayList<>();

    @PostConstruct
    public void init(){
        Thread daemon = new Thread(() -> {
            int count = 0;
            while (true){
                if(!queue.isEmpty()){
                    Msg msg = queue.remove();
                    this.update(msg.getPublisher(), (T)msg.getMsg());
                }
                count++;
                int max = 100;
                if(queue.size() > 1000){
                    max = queue.size() / 10;
                }
                if(count % max == 0){
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    count = 0;
                }
            }
        });
//        daemon.setDaemon(true);
        daemon.start();
    }

    /**
     * @param publisher
     * @param message
     * @param isInstantMsg
     * @Description: 接收发布者的消息
     */
    public void publish(String publisher, T message, boolean isInstantMsg) {
        if (isInstantMsg) {
            update(publisher, message);
            return;
        }

        Msg<T> m = new Msg<T>(publisher, message);
        queue.offer(m);
    }

    /**
     * @param subcriber
     * @Description: 订阅
     * @return: void
     */
    public void subcribe(ISubcriber subcriber) {
        subcribers.add(subcriber);
    }

    /**
     * @param subcriber
     * @Description: 退订
     * @return: void
     */
    public void unSubcribe(ISubcriber subcriber) {
        subcribers.remove(subcriber);
    }

    /**
     * @param publisher
     * @param Msg
     * @Description: 发送消息
     * @return: void
     */
    public void update(String publisher, T Msg) {
        for (ISubcriber subcriber : subcribers) {
            subcriber.update(publisher, Msg);
        }
    }


    class Msg<T> {
        private String publisher;
        private T m;

        public Msg(String publisher, T m) {
            this.publisher = publisher;
            this.m = m;
        }

        public String getPublisher() {
            return publisher;
        }

        public void setPublisher(String publisher) {
            this.publisher = publisher;
        }

        public T getMsg() {
            return m;
        }

        public void setMsg(T m) {
            this.m = m;
        }
    }


}



