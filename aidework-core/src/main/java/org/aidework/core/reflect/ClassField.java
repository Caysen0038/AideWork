package org.aidework.core.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class ClassField {
	
	private ClassField(){
		/**
		 * lazy 单例模式
		 */
	}
	public enum FieldInfo{
		MODIFIER,TYPE,NAME;
	}
	
	public static final class ClassFieldFactory{
		private static final ClassField classField=new ClassField();
		public static ClassField newInstance(){
			return classField;
		}
	}
	public Map<FieldInfo,String> getFieldInfo(Field field){
		if(field==null){
			return null;
		}
		Map<FieldInfo,String> map=new HashMap<>(3);
		map.put(FieldInfo.MODIFIER, Modifier.toString(field.getModifiers()));
		map.put(FieldInfo.TYPE, field.getType().getName());
		map.put(FieldInfo.NAME, field.getName());
		return map;
	}
}
