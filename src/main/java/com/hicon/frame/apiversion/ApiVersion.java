package com.hicon.frame.apiversion;


import java.lang.annotation.*;


@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiVersion {
    /**
     * @return 版本号
     */
    int value() default 1;
}
