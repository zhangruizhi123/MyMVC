package com.cjeg.dao;

import com.telrob.interf.Instance;

/**
 * 
 * @author ����־
 *
 * ����ʱ��:2017��8��8�� ����7:55:33
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
		System.out.println("delete ����������");
	}
}
