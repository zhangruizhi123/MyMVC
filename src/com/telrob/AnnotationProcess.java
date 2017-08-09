package com.telrob;

import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.telrob.interf.Autowired;
import com.telrob.interf.Instance;
import com.telrob.interf.RequestMapper;
import com.telrob.interf.ResponseBody;
import com.telrob.interf.Service;

/**
 * 处理注解类
 * @author 张瑞志
 *
 * 创建时间:2017年8月8日 下午6:30:58
 *
 */


public class AnnotationProcess {
	//存放类的属性
	private Map<String,Object> clsMap=new HashMap<String,Object>();
	//存放url与方法的
	private Map<String,String> methodMap=new HashMap<String, String>();
	//存放方法对象
	private Map<String,Method> mathodM=new HashMap<String, Method>();
	//是否返回一个对象
	private Map<String,Boolean> resultM=new HashMap<String, Boolean>();
	
	private boolean debugger=true;
	
	//扫描所有的类的集合
	private List<String>cls;
	public AnnotationProcess(List<String>cls){
		this.cls=cls;
	}
	
	/**
	 * 外部直接调用该接口
	 */
	public void process(){
		processClass();
		processFiled();
		processMethod();
		if(debugger){
			System.out.println("--------内存中url链接----------");
			for(String key:methodMap.keySet()){
				System.out.println(key+"\t"+methodMap.get(key));
			}
			System.out.println("-----------------------------");
		}
	}
	
	/**
	 * 根据请求分发到不同的方法中去
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	public void invokeMethod(HttpServletRequest request,HttpServletResponse response) throws Exception{
		//请求连接
		String url=request.getRequestURI();
		//项目根路径
		String contextPath=request.getServletContext().getContextPath();
		if(url.startsWith(contextPath)){
			//获取相对路径
			String rPath=url.substring(contextPath.length(),url.length());
			String pat=methodMap.get(rPath);
			//获取要调用的方法
			if(pat!=null&&!pat.equals("")){
				String str[]=pat.split(";");
				if(str.length==2){
					Object obj=clsMap.get(str[0]);
					String name=str[1];
					try {
						//根据请求的url直接获取对应的方法对象
						Method method=mathodM.get(rPath);
						Boolean isReturn=resultM.get(rPath);
						//这里没有对方法的参数进行校验,可以根据方法的参数来对方法反射进行回调
						method.setAccessible(true);
						Object resultObj=method.invoke(obj, new Object[]{request,response});
						if(resultObj==null){
							System.out.println(resultObj);
							return;
						}
						//当请求体返回json时
						if(isReturn!=null){
							Gson gson=new Gson();
							String json=gson.toJson(resultObj);
							response.setContentType("text/plain;charset=UTF-8"); 
							response.setCharacterEncoding("UTF-8"); 
							PrintWriter out=response.getWriter();
							out.write(json);
						}else{
							//请求返回一个视图
							if(resultObj.getClass().isAssignableFrom(String.class)){
								request.getRequestDispatcher("/"+(String)resultObj).forward(request, response);
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}else{
					throw new Exception("方法获取失败");
				}
				
			}else{
				throw new Exception("无法获取url对应的链接:"+rPath);
			}
		}
	}
	/**
	 * 处理类上的注解
	 */
	public void processClass(){
		//遍历所有类
		for(String cl:cls){
			try {
				Class cs=Class.forName(cl);
				Annotation ann[]=cs.getAnnotations();
				//当存在注解时
				if(ann!=null&&ann.length>0){
					Object obj=cs.newInstance();
					//构造所有加注解的实例
					clsMap.put(cl, obj);
					for(Annotation an:ann){
						//当是service时有值
						if(an.annotationType()==Service.class){
							Service se=(Service) an;
							clsMap.put(se.value(), obj);
						}else if(an.annotationType()==Instance.class){
							Instance se=(Instance) an;
							clsMap.put(se.value(), obj);
						}
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void processFiled(){
		//遍历所有类
				for(String cl:cls){
					Object obj=clsMap.get(cl);
					//当该类存在时
					if(obj!=null){
						Class cls=obj.getClass();
						//获取所有的字段
						Field[] field=cls.getDeclaredFields();
						for(Field fd:field){
							Annotation ann[]=fd.getAnnotations();
							for(Annotation an:ann){
								//当该字段是自动赋值时
								if(an.annotationType()==Autowired.class){
									Autowired au=(Autowired) an;
									String fdn;
									if(!au.value().equals("")){
										fdn=au.value();
									}else{
										fdn=fd.getName();
									}
									try {
										//获取要赋值的对象
										Object obField=clsMap.get(fdn);
										//设置为可以存取
										fd.setAccessible(true);
										fd.set(obj, obField);
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
							}
						}
					}
				}
	}
	
	/**
	 * 处理方法上的注解
	 */
	public void processMethod(){
		for(String cl:cls){
			Object obj=clsMap.get(cl);
			//当该类存在时
			if(obj!=null){
				
				Class cls=obj.getClass();
				String clse=null;
				Method[] methods=cls.getDeclaredMethods();
				if(methods!=null&&methods.length>0){
					Annotation []clasAnn=cls.getAnnotations();
					for(Annotation clA:clasAnn){
						if(clA.annotationType()==RequestMapper.class){
							RequestMapper rm=(RequestMapper) clA;
							String value=rm.value();
							if(!value.equals("")){
								if(value.contains("/")){
									clse=value;
								}else{
									clse="/"+value;
								}
							}
						}
					}
					for(Method method:methods){
						String url=null;
						//方法上的注解
						Annotation anns[]=method.getAnnotations();
						if(anns!=null&&anns.length>0){
							for(Annotation an:anns){
								//当存在映射时
								if(an.annotationType()==RequestMapper.class){
									RequestMapper req=(RequestMapper) an;
									String value=req.value();
									if(!value.equals("")){
										if(value.contains("/")){
											url=clse+value;
										}else{
											url=clse+"/"+value;
										}
										methodMap.put(url, cl+";"+method.getName());
										mathodM.put(url, method);
									}
								}else if(an.annotationType()==ResponseBody.class){
									resultM.put(url, true);
								}
							}
						}
					}
				}
			}
		}
	}
	
}
