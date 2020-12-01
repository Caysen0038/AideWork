package org.aidework.core.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

/**
 * properties文件操作工具类
 * 获取制定properties文件，读取key-value信息，修改、删除key-value信息等操作
 * 
 * @author Caysen
 * 
 * @date 2018年4月9日
 *
 */
public class PropertiesHelper {
	
	/**
	 * 文件默认后缀，当路径中缺省该后缀时添加进去
	 */
	private static final String SUFFIX=".properties";
	
	/**
	 * 静态工具类，禁止实例化
	 */
	private PropertiesHelper(){
		
	}
	
	/**
	 * 根据传入的指定路径生成Properties类型对象并返回
	 * @param path 指定的文件路径
	 * @return 读取到的Properties文件对象
	 */
	public static Properties getProp(String path){
		path=validPath(path);
		Properties prop=new Properties();
		InputStream input=null;
		try {
			input=new FileInputStream(path);
		} catch (FileNotFoundException e1) {
			System.out.println("read 【"+path+"】 failed,please check the path:【"+path+"】 is valid");
			
		}
		if(input!=null){
			try {
				prop.load(input);
				return prop;
			} catch (IOException e) {
				System.out.println("load 【"+path+"】 failed,please check the file is valid");
			}finally{
				try {
					input.close();
				} catch (IOException e) {
					System.out.println("input stream close with a error");
					
				}
			}
		}
		return null;
	}

	/**
	 * 读取指定路径的properties文件，将key-value装入map对象并返回
	 * @param path 指定的properties文件路径
	 * @return 装有全部key-value值得map对象
	 */
	@SuppressWarnings("rawtypes")
	public static Map<String,String> read(String path){
		Properties prop=getProp(path);
		if(prop!=null){
			Map<String, String> map=new HashMap<>();
			for(Entry temp:prop.entrySet()){
				map.put(temp.getKey().toString(), temp.getValue().toString());
			}
			return map;
		}
		return null;
	}
	
	/**
	 * 将map中的key-value值存储进指定路径中的properties文件中
	 * @param path 指定properties文件路径
	 * @param map 被存储的key-value值对象
	 * @return 存储是否成功
	 */
	public static boolean store(String path,Map<String,String> map){
		Properties prop=getProp(path);
		if(prop!=null){
			for(Entry<String,String> temp:map.entrySet()){
				prop.setProperty(temp.getKey(), temp.getValue());
			}
			writeProp(path,prop,"Update "+map);
			return true;
		}
		return false;
	}
	
	/**
	 * 从指定路径中的properties文件移除指定的key值
	 * @param path 指定properties文件路径
	 * @param key 将要被移除的key值
	 */
	public static void remove(String path,String[] key){
		Properties prop=getProp(path);
		if(prop==null){
			return;
		}
		for(String temp:key){
			prop.remove(temp);
		}
		writeProp(path,prop,"Remove"+key);
	}
	
	/**
	 * 将properties对象写入指定路径的properties文件中
	 * 
	 * @param path properties文件路径
	 * @param prop properties文件对象，存储将要被持久化的key-value信息
	 * @param log 操作日志
	 */
	
	private static void writeProp(String path,Properties prop,String log){
		path=validPath(path);
		OutputStream out=null;
		try {
			out=new FileOutputStream(path);
		} catch (FileNotFoundException e) {
			System.out.println("write 【"+path+"】 failed,please check the path 【"+path+"】 is valid");
			e.printStackTrace();
		}
		if(out!=null){
			try {
				prop.store(out, log);
				prop.list(System.out);
			} catch (IOException e) {
				System.out.println(log+" 【"+path+"】 failed");
				e.printStackTrace();
			}finally{
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 验证文件路径后缀是否带有.properties
	 * @param path properties文件路径
	 * @return 一个带有.properties后缀的文件路径
	 */
	private static String validPath(String path){
		if(!path.endsWith(SUFFIX)){
			return path+=SUFFIX;
		}
		return path;
	}

}
