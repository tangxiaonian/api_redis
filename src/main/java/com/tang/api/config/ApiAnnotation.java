package com.tang.api.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Classname ApiAnnotation
 * @Description [ 接口是否需要进行幂等性验证 ]
 * @Author Tang
 * @Date 2020/2/13 16:07
 * @Created by ASUS
 */
@Target(value = ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiAnnotation {
    /**
     *
     * token 存的位置
     */
    String type() default "header";
}
