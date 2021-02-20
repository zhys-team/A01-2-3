//package com.zhys.tx;
//
//import com.lorne.tx.springcloud.interceptor.TxManagerInterceptor;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.Ordered;
//import org.springframework.stereotype.Component;
//
///**
// * Created by lorne on 2017/6/28.
// */
//@Aspect
//@Component
//public class TxTransactionInterceptor  implements Ordered {
//
//
//    @Override
//    public int getOrder() {
//        return 1;
//    }
//
//    @Autowired
//    private TxManagerInterceptor txManagerInterceptor;
//
//    @Around("execution(* com.zhys.service.impl.*Impl.*(..))")
//    public Object around(ProceedingJoinPoint point)throws Throwable{
//        System.out.println("interceptor-->around-->start");
//        Object val =  txManagerInterceptor.around(point);
//        System.out.println("interceptor-->around-->end");
//        return val;
//    }
//}
