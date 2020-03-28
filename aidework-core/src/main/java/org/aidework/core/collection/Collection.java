package org.aidework.core.collection;

/**
 * 单值集合类最高接口
 * 定义了所有的集合实现类必须实现的方法
 * 每个集合实现类都应该能够被外部调用到方法并有效的执行
 * 为保证集合的有效性，集合的对象应该至少实现equals方法和hashCode方法
 *
 * @author Caysen
 * 
 * @date 2018年4月23日
 *
 * @param <T>
 */
public interface Collection<T> extends Iterable<T>{
	/**
	 * 添加一个对象到集合中
	 * 
	 * @param obj 即将被添加进集合的对象
	 * @return 添加是否成功
	 */
	boolean add(T obj);
	/**
	 * 添加一个集合的对象到集合中
	 * 
	 * 
	 * @param col 即将被添加进集合的对象集合
	 * @return 添加是否成功
	 */
	boolean addAll(Collection<? extends T> col);
	
	/**
	 * 将指定的对象从集合中删除
	 * 
	 * @param obj 即将被从集合中删除的对象
	 * @return 被从集合中删除的对象
	 */
	T remove(T obj);
	
	/**
	 * 将参数集合中的元素从集合中删除
	 * 
	 * @param col 即将被从集合中删除的对象的集合
	 * @return 被从集合中删除的对象的集合,返回的集合类型为被调用的集合类型
	 */
	Collection<? extends T> removeAll(Collection<? extends T> col);
	
	/**
	 * 检查集合中是否包含了指定的对象
	 * @param obj 被检查的对象
	 * @return 是否包含该对象，true为包含，false为不包含
	 */
	boolean contains(T obj);
	
	/**
	 * 检查集合中是否包含了指定集合中的所有对象
	 * @param col 被检查的对象的集合
	 * @return 是否包含集合中所有的对象，true为所有都包含，false为至少有一个不包含
	 */
	boolean containsAll(Collection<? extends T> col);
	
	/**
	 * 将集合中指定对象替换成新对象
	 * 
	 * @param oldObj 即将被替代的旧对象
	 * @param newObj 替代旧对象的新对象
	 * @return 被替代并移除的旧对象
	 */
	T replace(T oldObj, T newObj);
	
	
	/**
	 * 得到当前集合中元素的个数
	 * @return 元数的个数
	 */
	int size();
	
	/**
	 * 清除当前集合，清空集合中所有的元素
	 */
	void clear();
	
	/**
	 * 检查集合是否为空
	 * 
	 * @return 是否为空集合，true为空，false为至少含有一个元素
	 */
	boolean isEmpty();
}
