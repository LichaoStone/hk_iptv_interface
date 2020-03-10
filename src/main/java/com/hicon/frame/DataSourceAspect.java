package com.hicon.frame;

import com.hicon.frame.aspect.annotation.DataSource;
import com.hicon.frame.page.MultipleDataSource;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;


public class DataSourceAspect {

	public void before(JoinPoint point){
 
        Object target = point.getTarget();
        String method = point.getSignature().getName();
        // 获取目标类的接口， 所以@DataSource需要写在接口上
        Class<?>[] classz = target.getClass().getInterfaces();
        Class<?>[] parameterTypes = ((MethodSignature) point.getSignature()).getMethod().getParameterTypes();
        try{
            Method m = classz[0].getMethod(method, parameterTypes);
            if (m != null && m.isAnnotationPresent(DataSource.class))
            {
                DataSource data = m.getAnnotation(DataSource.class);
                System.out.println("用户选择数据库库类型：" + data.value());
                MultipleDataSource.setDataSourceKey(data.value());                        // 数据源放到当前线程中
            }
 
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
