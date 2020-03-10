package com.hicon.frame.aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class TestControllerAspect{
    private static final Logger logger = Logger.getLogger(TestControllerAspect.class);
    /**
     * 调用controller包下的任意类的任意方法时均会调用此方法
     */
    //@Around("execution(* com.hicon.test.controller.*.*(..))")
    public Object run1(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("controller测试aspect成功！！！！");
        Object o = null;
        try{
            o = joinPoint.proceed();
        }catch(Throwable t){
            throw t;
        }

        return o;
    }

}
