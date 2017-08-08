package com.telrob.interf;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ���صĶ���
 * @author ����־
 *
 * ����ʱ��:2017��8��8�� ����6:07:28
 *
 */

@Documented  
@Retention(RetentionPolicy.RUNTIME)  
@Target(value={ElementType.METHOD}) 
public @interface ResponseBody {
	String value() default "";
}
