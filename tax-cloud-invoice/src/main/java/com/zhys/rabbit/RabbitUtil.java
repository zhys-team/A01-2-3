package com.zhys.rabbit;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.zhys.constants.CommonStatus;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by li.hui on 18/5/15.
 */
public class RabbitUtil {

    private boolean Open=true;

    private RabbitUtil(){}

    private static RabbitUtil instance = new RabbitUtil();

    public static RabbitUtil getInstance(){
        return instance;
    }

    public void OperationLog(String token,String message,AmqpTemplate rabbitTemplate){
        if(Open){
            Map query=new HashMap();
            query.put("token",token);
            query.put("message",message);
            rabbitTemplate.convertAndSend(CommonStatus.RabbitType.OPERATIONLOG.seq,query);
        }else{
            //直接保存
        }
    }
}
