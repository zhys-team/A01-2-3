package com.zhys.timer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.zhys.base.po.RateCode;
import com.zhys.redis.RedisUtils;
import com.zhys.service.SQLManager;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ScheduledTasks {
	
	@Autowired
	private RedisUtils redis;
	
	@Autowired
	private SQLManager manager;

	
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    
    //发票查验接口token openid
    @Scheduled(fixedRate = 1000*60*10)
    public void invoiceToken() {
        //System.out.println("现在时间：" + dateFormat.format(new Date()));
        List<RateCode> rcs = (List<RateCode>) manager.list("rate_code.queryList", new RateCode());
        if(rcs!=null&&rcs.size()>0) {
        	for(RateCode rc:rcs) {
        		if(StringUtils.isNoneEmpty(rc.getJc())) {
        			redis.hset("ratecode",rc.getJc(),rc);
        			//log.info("ratecode"+rc.getJc()+((RateCode)redis.hget("ratecode", rc.getJc())).getId());
        			//log.info("ratecode"+rc.getJc()+((RateCode)redis.hget("ratecode", rc.getJc())).getId());
        		}
        	}
        }
    }
    
  
}
