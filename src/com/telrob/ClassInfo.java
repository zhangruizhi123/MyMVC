package com.telrob;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author ����־
 *
 * ����ʱ��:2017��8��8�� ����6:48:50
 *
 */
public class ClassInfo {
	private String className;//����
	private String annotationvalue;//ע���ֵ
	private Object object;//ʵ�����
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
	
	public static void main(String[] args) {
		Object obj=new  HashMap<String,String>();
		
		System.out.println(obj.getClass().isAssignableFrom(HashMap.class));
	}
	
}
