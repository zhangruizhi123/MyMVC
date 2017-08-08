package com.telrob.interf;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 返回的对象
 * @author 张瑞志
 *
 * 创建时间:2017年8月8日 下午6:07:28
 *
 */

@Documented  
@Retention(RetentionPolicy.RUNTIME)  
@Target(value={ElementType.METHOD}) 
public @interface ResponseBody {
	String value() default "";
}
