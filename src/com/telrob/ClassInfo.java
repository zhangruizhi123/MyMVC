package com.telrob;

/**
 * 
 * @author 张瑞志
 *
 * 创建时间:2017年8月8日 下午6:48:50
 *
 */
public class ClassInfo {
	private String className;//类名
	private String annotationvalue;//注解的值
	private Object object;//实体对象
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getAnnotationvalue() {
		return annotationvalue;
	}
	public void setAnnotationvalue(String annotationvalue) {
		this.annotationvalue = annotationvalue;
	}
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}
	@Override
	public String toString() {
		return "ClassInfo [className=" + className + ", annotationvalue="
				+ annotationvalue + ", object=" + object + "]";
	}
	
	
}
