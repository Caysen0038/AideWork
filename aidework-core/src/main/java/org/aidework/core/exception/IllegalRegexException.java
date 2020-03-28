package org.aidework.core.exception;

/**
 * 非法正则表达式异常
 * 指定的正则表达式字符串不是合法有效的正则表达式所时抛出的异常
 *
 *
 * @author Caysen
 * 
 * @date 2018年4月16日
 *
 */
public class IllegalRegexException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3608027094217219272L;

	public IllegalRegexException(){
		super();
	}
	
	public IllegalRegexException(String message){
		super(message);
	}
	
}
