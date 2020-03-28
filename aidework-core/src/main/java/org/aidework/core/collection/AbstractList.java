package org.aidework.core.collection;

import java.util.Iterator;

/**
 * List 的抽象类
 * 提供一些公共方法的实现
 * 继承者只需要实现一些根据特性独立的方法即可
 * 另外，继承者如果直接实现List接口，则需要实现equals和hashCode方法
 *
 *
 *
 * @author Caysen
 * 
 * @date 2018年4月24日
 *
 * @param <T> 集合对象的类型
 */
public abstract class AbstractList<T> implements List<T>{
	
	
	/**
	 * 迭代器方法
	 * 如果继承者需要实现特定的迭代器方法，则需要重写该方法并且自定义自己的迭代器类
	 */
	@Override
	public Iterator<T> iterator() {
		Iterator<T> list=new ListIterator<>(this);
		
		return list;
	}
	
	/**
	 * 
	 * List的迭代器
	 * 这是一个公共的List迭代器，不可继承
	 * 如果继承者需要实现自己的迭代器，则还需要实现自己的迭代器返回方法，即iterator
	 *
	 *
	 * @author Caysen
	 * 
	 * @date 2018年4月24日
	 *
	 * @param <E> 迭代器中数据的类型
	 */
	private final class ListIterator<E> implements Iterator<E>{
		private E[] arr;
		private int size;
		private int current;
		
		@SuppressWarnings("unchecked")
		public ListIterator(List<? extends E> list){
			this.arr=(E[])list.toArray();
			this.size=arr.length;
			current=0;
		}
		
		@Override
		public boolean hasNext() {
			return current<size;
		}

		@Override
		public E next() {
			if(!hasNext()){
				return null;
			}
			return arr[current++];
		}
		
	}
	
	@Override
	public String toString(){
		/**
		 * 为了体现空集合的概念
		 * 即使集合为空，也同样返回一个空集合的样式字符串为不返回null
		 */
//		if(isEmpty()){
//			return null;
//		}
		StringBuilder sb=new StringBuilder(size()*4+4);
		sb.append("[");
		Object[] arr=this.toArray();
		for(Object temp:arr){
			sb.append(temp.toString()).append(",");
		}
		if(sb.length()>1){
			sb.deleteCharAt(sb.length()-1);
		}
		sb.append("]");
		return sb.toString();
	}
	
}
