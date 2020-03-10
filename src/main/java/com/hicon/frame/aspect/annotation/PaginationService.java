package com.hicon.frame.aspect.annotation;

import java.lang.annotation.*;

/**
 * 分页注解
 * 用于控制数据分页
 * @作者 栗超
 * @说明
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PaginationService {
    /**
     * 前端组件的类型
     * @return
     */
    String webTableType() default "dataTables";
}

