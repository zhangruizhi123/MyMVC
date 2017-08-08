package com.cjeg.dao;

import com.telrob.interf.Instance;

/**
 * 
 * @author 张瑞志
 *
 * 创建时间:2017年8月8日 下午7:55:33
 *
 */

@Instance("dao")
public class Dao  implements DA {
	public void insert(){
		System.out.println("insert");
	}
	public void update(){
		System.out.println("update");
	}
	
	public void delete(){
		System.out.println("delete 方法被调用");
	}
}
