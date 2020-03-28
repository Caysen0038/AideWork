package org.aidework.core.helper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * 日期工具类
 * 提供了获取当前日期、时间、时间戳等方法
 *
 *
 *
 * @author Caysen
 * 
 * @date 2018年4月8日
 *
 */

public class NumberHelper {
	
	/**
	 * 随机数生成器，主要用于生成时间戳+随机数操作
	 */
	private final static Random random;
	
	/**
	 * 日期格式化转换器
	 */
	private final static SimpleDateFormat format;
	
	static{
		random=new Random();
		format=new SimpleDateFormat();
	}
	
	/**
	 * 静态工具类，禁止实例化
	 */
	private NumberHelper(){
		
	}
	
	/**
	 * 按照指定格式返回日期
	 * @param format
	 * @return
	 */
	public static String getDate(String f) {
		format.applyPattern(f);
		return format.format(new Date());
	}
	
	/**
	 * 获取当前日期
	 * 返回格式 yyyy-MM-dd
	 * @return 当前日期
	 */
	public static String getDate(){
		format.applyPattern("yyyy-MM-dd");
		return format.format(new Date());
	}
	
	/**
	 * 获取当前时间
	 * 返回格式 hh:mm:ss
	 * @return 当前时间
	 */
	public static String getTime(){
		format.applyPattern("hh:mm:ss");
		return format.format(new Date());
	}
	
	/**
	 * 获取当前日期和时间
	 * 返回格式 yyyy-MM-dd hh:mm:ss
	 * @return 当前日期+时间
	 */
	public static String getDateTime(){
		format.applyPattern("yyyy-MM-dd hh:mm:ss");
		return format.format(new Date());
	}
	
	/**
	 * 获得当前日期为周几
	 * @return
	 */
	public static String getWeekday() {
		format.applyPattern("EEEE");
		return format.format(new Date());
	}
	
	/**
	 * 获取指定长度的时间戳，若长度大于时间戳，则由随机数补足末尾
	 * 
	 * @param length 时间戳长度
	 * @return 指定长度的时间戳
	 */
	public static String getTimeStamp(int length){
		format.applyPattern("yyyyMMddhhmmss");
		StringBuilder stamp=new StringBuilder(length);
		stamp.append(format.format(new Date()));
		if(length<stamp.length()){
			return stamp.substring(0,length);
		}
		if(length>stamp.length()){
			for(int i=0,n=length-stamp.length();i<n;i++){
				stamp.append(random.nextInt(10));
			}
			return stamp.toString();
		}
		return stamp.toString();
	}
	
	/**
	 * 获取指定长度的随机整数字符串
	 * @param length 随机数的个数
	 * @return 指定长度的随机整数字符串
	 */
	public static String getRandom(int length){
		StringBuilder sb=new StringBuilder(length);
		for(int i=0;i<length;i++){
			sb.append(random.nextInt(10));
		}
		return sb.toString();
	}
	
	/**
	 * 生成全球唯一的ID，即UUID
	 * @return
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString();
	}
}
