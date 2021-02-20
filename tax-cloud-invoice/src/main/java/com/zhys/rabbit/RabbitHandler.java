package com.zhys.rabbit;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

/**
 * Created by li.hui on 18/5/15.
 */
@lombok.extern.log4j.Log4j
@Component
public class RabbitHandler {

    private static RabbitHandler rabbitHandler;

//    @Autowired
//    private AccessService accessService;

    @PostConstruct
    public void Init() {//保险点就是这么写,这块不写也没事
        rabbitHandler = this;
    }

    /**用户操作日志 */
    @org.springframework.amqp.rabbit.annotation.RabbitHandler
    //@RabbitListener(queues = "OPERATIONLOG")
    public void OPERATIONLOG(Map query) {
        try{
            String token=query.get("token").toString();
            String message=query.get("message").toString();
            System.out.println(token);
            //CommonUntil.getInstance().CreateOperation(token,message,readOnlineService,operationService,readUserService);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    



}
