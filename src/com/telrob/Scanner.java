package com.telrob;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * ɨ�����
 * @author ����־
 *
 * ����ʱ��:2017��8��8�� ����5:23:08
 *
 */
public class Scanner {
	private static List<String>classList=new ArrayList<String>();
	private String path=null;
	public Scanner(){
		String basePath=Scanner.class.getResource("/").getFile();
		File file=new File(basePath);
		path=file.getAbsolutePath();
	}
	/**
	 * ɨ���ļ������еİ�
	 * @param basePack
	 * @return
	 */
	public List<String> scan(String basePack){
		list(basePack,classList);
		return classList;
	}
	
	private void list(String basePack,List<String>classList){
		//�������еĵ��滻
		File file=new File(path,basePack.replaceAll("\\.", "/"));
		//�������а���
		File fileList[]=file.listFiles();
		for(File fl:fileList){
			String name=fl.getName();
			if(fl.isDirectory()){
				//����·��ʱֱ�ӵݹ�
				list(basePack+"."+name,classList);
			}else{
				String end=name.substring(name.indexOf("."));
				if(end!=null&&(end.equals(".java")||end.equals(".class"))){
					//��ȡ�ļ��������Ӻ�׺
					String start=name.substring(0,name.indexOf("."));
					//��ӵ�������
					classList.add(basePack+"."+start);
				}
				
			}
		}
		
	}
	public static void main(String[] args) {
		Scanner scan=new Scanner();
		List<String>ls=scan.scan("com.cjeg");
		for(String str:ls){
			System.out.println(str);
		}
	}
}
