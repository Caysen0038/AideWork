package org.aidework.core.constant;
/**
 * 原子数据类型列表
 * 包含了int,long,float,double,byte,short,boolean,char
 *
 * @author Caysen
 * 
 * @date 2018年4月5日
 *
 */
public enum AtomicType {
	INT(int.class,"int"),
	LONG(long.class,"long"),
	FLOAT(float.class,"float"),
	DOUBLE(double.class,"double"),
	BYTE(byte.class,"byte"),
	SHORT(short.class,"short"),
	BOOLEAN(boolean.class,"boolean"),
	CHAR(char.class,"char");
	private Class<?> clazz;
	private String name;
	private AtomicType(Class<?> clazz,String name){
		this.clazz=clazz;
		this.name=name;
	}
	/**
	 * 得到原子类型的Class对象
	 * @return 原子类型的Class对象
	 */
	public Class<?> getAtomicClass(){
		return this.clazz;
	}
	/**
	 * 获得原子类型的名称，如int类型的名称为“int”
	 * @return 存储原子类型名称的字符串
	 */
	public String getName(){
		return this.name;
	}
	/**
	 * 判断指定名称的类型是否被包含在原子类型列表
	 * @param name 被判断的类型名称，如double类型的名称为“double”
	 * @return 是否存在，true为存在，false则不存在
	 */
	public static boolean contain(String name){
		for(AtomicType temp:AtomicType.values()){
			if(temp.getName().equals(name)){
				return true;
			}
		}
		return false;
	}
	/**
	 * 得到指定名称类型的Class对象
	 * 如name=“int”,则返回int.Class
	 * @param name 类型的名称
	 * @return 对应名称的Class对象，若返回null则表示指定名称为非可识别的原子类型
	 */
	public static Class<?> getAtomicClass(String name){
		for(AtomicType temp:AtomicType.values()){
			if(temp.getName().equals(name)){
				return temp.getAtomicClass();
			}
		}
		//如果列表未包含指定名称的类型，说明非可识别的原子类型，则返回null
		return null;
	}
}
