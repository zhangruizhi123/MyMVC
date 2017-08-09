package com.telrob;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 *  请求分发类
 * 
 * @author 张瑞志
 *
 * 创建时间:2017年8月8日 下午5:18:43
 *
 */
 public class MyDispachServlet extends HttpServlet {
 
	private static final long serialVersionUID = 1L;
	private String packageName;
	private static AnnotationProcess ann;
    public MyDispachServlet() {
        super();
    }

	public void init(ServletConfig config) throws ServletException {
		//初始化
		String pack=config.getInitParameter("packageName");
		Scanner scan=new Scanner();
		List<String> list=scan.scan(pack);
		if(list!=null&&list.size()>0){
			ann=new AnnotationProcess(list);
			ann.process();
		}else{
			//throw new ServletException("s");
		}
		
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String url=request.getRequestURI();
			String type=request.getContentType();
			System.out.println("请求类型:"+type+"\t请求连接："+url);
			ann.invokeMethod(request, response);
		} catch (Exception e) {
			System.out.println("调用方法失败");
			e.printStackTrace();
		}
	}

}
