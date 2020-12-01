package org.aidework.core.object;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.aidework.core.constant.AtomicType;

/**
 * 类型转换器
 * 主要用于pojo对象中各个层次的业务对象相互转换
 * 简单属性的转换
 *
 *
 * @author Caysen
 * 
 * @date 2018年5月16日
 *
 */
public class TypeConvertor {
	

	public static <S,T> T convert(S obj,Class<T> target){
		if(obj==null||target==null){
			
			return null;
		}
		/**
		 * 存储类型中的属性信息
		 */
		Field[] targetField=target.getDeclaredFields();
		Field[] sourceField=obj.getClass().getDeclaredFields();
		List<Field> field=new ArrayList<>();
		boolean flag=false;
		/**
		 * 判断两个类中能够有效转换的类属性
		 */
		for(Field tTemp:targetField){
			for(Field sTemp:sourceField){
				if(sTemp.getName().equals(tTemp.getName())&&
						sTemp.getType().getName().equals(tTemp.getType().getName())){
					flag=true;
					break;
				}
			}
			if(flag){
				field.add(tTemp);
				flag=false;
			}
		}
		/**
		 * 实例化需要返回的对象
		 */
		T result=null;
		try {
			result=target.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		Class<?> clazz=null;
		String methodName;
		for(Field temp:field){
			/**
			 * 判断是否为boolean类型
			 */
			if(temp.getType().getName().equals("boolean")){
				methodName="is"+uppercase(temp.getName());
			}else{
				methodName="get"+uppercase(temp.getName());
			}
			
			try {
				Method sourceMethod=obj.getClass().getMethod(methodName);
				Method resultMethod=null;
				if(AtomicType.contain(temp.getType().getName())){
					clazz=AtomicType.getAtomicClass(temp.getType().getName());
				}else{
					try {
						clazz=Class.forName(temp.getType().getName());
					} catch (ClassNotFoundException e) {
						
						e.printStackTrace();
					}
				}
				resultMethod = target.getMethod("set"+uppercase(temp.getName()),clazz);
				S source=(S)obj;
				
				resultMethod.invoke(result, sourceMethod.invoke(source));
			} catch (NoSuchMethodException e) {
				System.out.println("Not found the method named "+methodName);
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	private static String uppercase(String str){
		return str.substring(0,1).toUpperCase()+str.substring(1,str.length());
	}
	
}
