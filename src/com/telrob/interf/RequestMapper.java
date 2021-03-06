package com.telrob.interf;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 服务层
 * @author 张瑞志
 *
 * 创建时间:2017年8月8日 下午6:07:28
 *
 */

@Documented  
@Retention(RetentionPolicy.RUNTIME)  
@Target(value={ElementType.TYPE,ElementType.METHOD}) 
public @interface RequestMapper {
	String value() default "";
}
