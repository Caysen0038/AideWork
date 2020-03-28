package org.aidework.core.collection;

/**
 * 
 * List,线性表数据结构的集合实现接口
 * 实现List接口，除了Collection集合方法外，还包含了体现线性表特性特点的方法
 *
 *
 * @author Caysen
 * 
 * @date 2018年4月23日
 *
 * @param <T> 集合对象的类型
 * 
 * @see Collection
 * @see Iterable
 */
public interface List<T> extends Collection<T> {
	
	

	/**
	 * 得到指定索引位置的对象
	 * 
	 * @param index 指定对象的位置索引
	 * @return 指定位置的集合，若超出范围则返回null
	 */
	T get(int index);
	
	/**
	 * 得到集合的第一个对象
	 * @return 集合中的第一个对象，若集合为空，则返回null
	 */
	T getFirst();
	
	/**
	 * 得到集合中的最后一个对象
	 * 当集合对象个数为1的时候，getFirst和getLast得到的对象为同一个对象
	 * @return 集合中的最后一个对象，若集合为空，则返回null
	 */
	T getLast();
	
	/**
	 * 得到指定对象的第一个索引位置
	 * @param obj 被检查索引的独享
	 * @return 和指定对象相等的元素的第一个索引位置，如果返回-1，则表示集合不包含该对象
	 */
	int getIndex(T obj);
	
	/**
	 * 删除指定索引位置的对象
	 * @param index 指定对象的索引位置
	 * @return 被删除的对象
	 */
	T remove(int index);
	
	/**
	 * 将集合的对象以数组的形式返回
	 * @return 数组形式的对象集合
	 */
	Object[] toArray();
	
	@Override
	List<? extends T> removeAll(Collection<? extends T> col);
	
}
