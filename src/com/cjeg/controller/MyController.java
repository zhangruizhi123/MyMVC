package com.cjeg.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cjeg.dao.DA;
import com.telrob.interf.Autowired;
import com.telrob.interf.Controller;
import com.telrob.interf.RequestMapper;

/**
 * 
 * @author 张瑞志
 *
 * 创建时间:2017年8月8日 下午5:24:07
 *
 */
@Controller
@RequestMapper("/abc")
public class MyController {
	
	
	
	@RequestMapper("insert")
	public void insert(HttpServletRequest request,HttpServletResponse respons){
		try {
			respons.getWriter().write("hello word");
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("ddd");
	}
	
	@RequestMapper("delete")
	public void delete(HttpServletRequest request,HttpServletResponse respons){
		try {
			respons.getWriter().write("hello word");
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("delete");
	}
	
	@RequestMapper("update")
	public void update(HttpServletRequest request,HttpServletResponse respons){
		try {
			respons.getWriter().write("hello word");
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("update");
	}
}
