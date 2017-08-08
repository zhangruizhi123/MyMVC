package com.cjeg.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cjeg.service.MySer;
import com.telrob.interf.Autowired;
import com.telrob.interf.Controller;
import com.telrob.interf.RequestMapper;
import com.telrob.interf.ResponseBody;

/**
 * 
 * @author 张瑞志
 *
 * 创建时间:2017年8月8日 下午5:24:07
 *
 */

@Controller
@RequestMapper("/people")
public class MyController2 {
	@Autowired
	private MySer mySer;
	
	@RequestMapper("insert.html")
	@ResponseBody
	public List<String> insertPeople(HttpServletRequest request,HttpServletResponse respons){
		mySer.insert();
		List<String> ls=new ArrayList<String>();
		ls.add("123");
		ls.add("345");
		return ls;
	}
	
}
