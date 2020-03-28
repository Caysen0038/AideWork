package org.aidework.core.exception;

/**
 * 类型不匹配异常
 * 该对象的Class类型与指定的Class类型不匹配所抛出
 *
 *
 * @author Caysen
 * 
 * @date 2018年4月16日
 *
 */
public class TypeMismatchException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7235410263549590722L;
	
	public TypeMismatchException(){
		super();
	}
	
	public TypeMismatchException(String message){
		super(message);
	}

}
