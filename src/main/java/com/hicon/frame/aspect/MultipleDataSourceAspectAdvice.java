package com.hicon.frame.aspect;


import com.hicon.frame.aspect.annotation.DataSource;
import com.hicon.frame.page.MultipleDataSource;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @作者： lichao
 * @时间： 2019-10-2019/10/31 11:19
 * @版本：
 * @说明：
 */
@Aspect
@Component
public class MultipleDataSourceAspectAdvice {
    private static final Logger logger = Logger.getLogger(MultipleDataSourceAspectAdvice.class);

    @Around(value="com.hicon.frame.aspect.pointcut.SystemArchitecture.anyPublicMethod()"
            + "&& @annotation(dataSource)")
    public Object setDataSource(ProceedingJoinPoint jp,DataSource dataSource) throws Throwable {
        logger.info("--MultipleDataSourceAspectAdvice--");

        String ds = dataSource.value();
        if(ds != null && !ds.equals("")){
            MultipleDataSource.setDataSourceKey(ds);
        }
        logger.info("ds="+ds);

        Object o = null;
        try{
            o = jp.proceed();
        }catch(Throwable t){
            throw t;
        }finally{
            MultipleDataSource.setDataSourceKey("");
        }
        return o;
    }
}
