/**
 * 
 */
package top.anets.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Administrator
 *
 */
@Aspect
@Slf4j
@Component
public class ValidAspect {
//    @Pointcut("@annotation(top.anets.annotation.InvoiceHeadValid)")
//    public void validPointcut() {};
//    
//    @Around("validPointcut()")
//    public Object around(ProceedingJoinPoint jp) throws Throwable {
//    	System.out.println("环绕通知=====================");
//    	Object result  = jp.proceed();
//    	System.out.println("环绕通知结束===================");
//    	return result;
//    }
    
}
