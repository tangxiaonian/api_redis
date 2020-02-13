package com.tang.api.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Classname TokenAnnotation
 * @Description [ 需要进行生成唯一token ]
 * @Author Tang
 * @Date 2020/2/13 16:04
 * @Created by ASUS
 */
@Target(value = ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TokenAnnotation {
}
