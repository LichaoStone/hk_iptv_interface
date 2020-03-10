package com.hicon.frame.aspect.annotation;

import java.lang.annotation.*;

/**
 * 数据源注解
 * 用于多数据源情况下的数据源切换
 * @作者： lichao
 * @时间： 2019/10/31 11:15
 * @版本：
 * @说明：
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {
    String value() default "defaultDataSource";
}
