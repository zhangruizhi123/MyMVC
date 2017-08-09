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
 * ����ע����
 * @author ����־
 *
 * ����ʱ��:2017��8��8�� ����6:30:58
 *
 */


public class AnnotationProcess {
	//����������
	private Map<String,Object> clsMap=new HashMap<String,Object>();
	//���url�뷽����
	private Map<String,String> methodMap=new HashMap<String, String>();
	//��ŷ�������
	private Map<String,Method> mathodM=new HashMap<String, Method>();
	//�Ƿ񷵻�һ������
	private Map<String,Boolean> resultM=new HashMap<String, Boolean>();
	
	private boolean debugger=true;
	
	//ɨ�����е���ļ���
	private List<String>cls;
	public AnnotationProcess(List<String>cls){
		this.cls=cls;
	}
	
	/**
	 * �ⲿֱ�ӵ��øýӿ�
	 */
	public void process(){
		processClass();
		processFiled();
		processMethod();
		if(debugger){
			System.out.println("--------�ڴ���url����----------");
			for(String key:methodMap.keySet()){
				System.out.println(key+"\t"+methodMap.get(key));
			}
			System.out.println("-----------------------------");
		}
	}
	
	/**
	 * ��������ַ�����ͬ�ķ�����ȥ
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	public void invokeMethod(HttpServletRequest request,HttpServletResponse response) throws Exception{
		//��������
		String url=request.getRequestURI();
		//��Ŀ��·��
		String contextPath=request.getServletContext().getContextPath();
		if(url.startsWith(contextPath)){
			//��ȡ���·��
			String rPath=url.substring(contextPath.length(),url.length());
			String pat=methodMap.get(rPath);
			//��ȡҪ���õķ���
			if(pat!=null&&!pat.equals("")){
				String str[]=pat.split(";");
				if(str.length==2){
					Object obj=clsMap.get(str[0]);
					String name=str[1];
					try {
						//���������urlֱ�ӻ�ȡ��Ӧ�ķ�������
						Method method=mathodM.get(rPath);
						Boolean isReturn=resultM.get(rPath);
						//����û�жԷ����Ĳ�������У��,���Ը��ݷ����Ĳ������Է���������лص�
						method.setAccessible(true);
						Object resultObj=method.invoke(obj, new Object[]{request,response});
						if(resultObj==null){
							System.out.println(resultObj);
							return;
						}
						//�������巵��jsonʱ
						if(isReturn!=null){
							Gson gson=new Gson();
							String json=gson.toJson(resultObj);
							response.setContentType("text/plain;charset=UTF-8"); 
							response.setCharacterEncoding("UTF-8"); 
							PrintWriter out=response.getWriter();
							out.write(json);
						}else{
							//���󷵻�һ����ͼ
							if(resultObj.getClass().isAssignableFrom(String.class)){
								request.getRequestDispatcher("/"+(String)resultObj).forward(request, response);
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}else{
					throw new Exception("������ȡʧ��");
				}
				
			}else{
				throw new Exception("�޷���ȡurl��Ӧ������:"+rPath);
			}
		}
	}
	/**
	 * �������ϵ�ע��
	 */
	public void processClass(){
		//����������
		for(String cl:cls){
			try {
				Class cs=Class.forName(cl);
				Annotation ann[]=cs.getAnnotations();
				//������ע��ʱ
				if(ann!=null&&ann.length>0){
					Object obj=cs.newInstance();
					//�������м�ע���ʵ��
					clsMap.put(cl, obj);
					for(Annotation an:ann){
						//����serviceʱ��ֵ
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
		//����������
				for(String cl:cls){
					Object obj=clsMap.get(cl);
					//���������ʱ
					if(obj!=null){
						Class cls=obj.getClass();
						//��ȡ���е��ֶ�
						Field[] field=cls.getDeclaredFields();
						for(Field fd:field){
							Annotation ann[]=fd.getAnnotations();
							for(Annotation an:ann){
								//�����ֶ����Զ���ֵʱ
								if(an.annotationType()==Autowired.class){
									Autowired au=(Autowired) an;
									String fdn;
									if(!au.value().equals("")){
										fdn=au.value();
									}else{
										fdn=fd.getName();
									}
									try {
										//��ȡҪ��ֵ�Ķ���
										Object obField=clsMap.get(fdn);
										//����Ϊ���Դ�ȡ
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
	 * �������ϵ�ע��
	 */
	public void processMethod(){
		for(String cl:cls){
			Object obj=clsMap.get(cl);
			//���������ʱ
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
						//�����ϵ�ע��
						Annotation anns[]=method.getAnnotations();
						if(anns!=null&&anns.length>0){
							for(Annotation an:anns){
								//������ӳ��ʱ
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
