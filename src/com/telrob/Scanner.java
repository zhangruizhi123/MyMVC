package com.telrob;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 扫描包类
 * @author 张瑞志
 *
 * 创建时间:2017年8月8日 下午5:23:08
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
	 * 扫描文件中所有的包
	 * @param basePack
	 * @return
	 */
	public List<String> scan(String basePack){
		list(basePack,classList);
		return classList;
	}
	
	private void list(String basePack,List<String>classList){
		//将包名中的点替换
		File file=new File(path,basePack.replaceAll("\\.", "/"));
		//遍历所有包名
		File fileList[]=file.listFiles();
		for(File fl:fileList){
			String name=fl.getName();
			if(fl.isDirectory()){
				//当是路径时直接递归
				list(basePack+"."+name,classList);
			}else{
				String end=name.substring(name.indexOf("."));
				if(end!=null&&(end.equals(".java")||end.equals(".class"))){
					//获取文件名，不加后缀
					String start=name.substring(0,name.indexOf("."));
					//添加到集合中
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
