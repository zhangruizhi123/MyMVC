package com.cjeg.service;

import com.cjeg.dao.DA;
import com.telrob.interf.Autowired;
import com.telrob.interf.Service;

/**
 * 
 * @author ����־
 *
 * ����ʱ��:2017��8��8�� ����9:17:12
 *
 */
@Service("mySer")
public class MyservImp implements MySer {
	
	
	@Autowired
	private DA dao;
	
	
	@Override
	public void insert() {
		// TODO Auto-generated method stub
		dao.insert();
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		dao.update();
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		dao.delete();
	}

}
