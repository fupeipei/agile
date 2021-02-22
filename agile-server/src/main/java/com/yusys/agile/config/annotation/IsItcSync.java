package com.yusys.agile.config.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * itc同步缺陷元注解
 *
 * @create 2021/2/1
 */
@Target(ElementType.METHOD)//字段注解 , 用于描述方法
@Retention(RetentionPolicy.RUNTIME)//在运行期保留注解信息
public @interface IsItcSync {
    String name() default "";
}
