package com.kindo.kaohe.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
@Aspect
@Order(-99)
@Component
public class LogAspect {
	@Before("@annotation(log)")// 拦截被TestAnnotation注解的方法；如果你需要拦截指定package指定规则名称的方法，可以使用表达式execution(...)，具体百度一下资料一大堆
    public void beforeTest(JoinPoint point, LogAnnotation log) throws Throwable {
        System.out.println("beforeTest:" + log.name());
    }

    @After("@annotation(log)")
    public void afterTest(JoinPoint point, LogAnnotation log) {
        System.out.println("afterTest:" + log.name());
    }
    
    @Around("@annotation(log)")
    public void aroundMethod(ProceedingJoinPoint point, LogAnnotation log){
    	System.out.println("aroundMethod:" + log.name());
    	Object[] args = point.getArgs();
    	try {
			point.proceed();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
