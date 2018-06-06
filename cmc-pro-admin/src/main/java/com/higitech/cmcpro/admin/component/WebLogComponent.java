package com.higitech.cmcpro.admin.component;

import com.higitech.cmcpro.admin.modules.system.entity.CmcLog;
import com.higitech.cmcpro.admin.modules.system.mapper.CmcLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
public class WebLogComponent {

    private static Queue<CmcLog> queue = new ConcurrentLinkedQueue<>();

    @Autowired
    private CmcLogMapper cmcLogMapper;

    public void pushLog(CmcLog cmcLog){
        queue.add(cmcLog);
    }

    @PostConstruct
    public void init(){
        Thread daemon = new Thread(() -> {
            int count = 0;
            while (true){
                if(!queue.isEmpty()){
                    CmcLog cmcLog = queue.remove();
                    cmcLogMapper.insert(cmcLog);
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
}
