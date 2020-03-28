package org.aidework.core.collection;

/**
 * Map集合类最高接口
 * 定义map集合中必备的操作方法
 * 
 *
 *
 * @author Caysen
 * 
 * @date 2018年4月24日
 *
 * @param <K> key
 * @param <V> value
 * 
 * @see Iterable
 * 
 */
public interface Map<K,V> extends Iterable<Map.MapNode<K,V>>{
	
	/**
	 * 添加进一对键值
	 * @param key 键  允许为Null
	 * @param value 值  允许为Null
	 * @return key当前对应的值，如果以前值为空，则返回当前添加的value，否则返回以前的值
	 */
	V put(K key, V value);
	
	/**
	 * 通过指定的key得到对应的value
	 * @param key 键
	 * @return 返回key对应的value，如果key不存在或则value为空，则返回null
	 */
	V get(K key);
	
	/**
	 * 删除指定key的元素
	 * @param key 键
	 * @return 被删除的值
	 */
	V remove(K key);
	
	/**
	 * 得到map中所有value的集合
	 * @return 装有所有value的List集合
	 */
	List<V> valueList();
	
	/**
	 * 得到map中所有key的集合
	 * @return 装有所有key的List集合
	 */
	List<K> keyList();
	
	/**
	 * 以Entry元素容器类型返回map中所有key-value的List集合
	 * @return
	 */
	List<MapNode<K,V>> mapList();
	
	/**
	 * Map元素容器接口
	 * Map中元素的存储必须是该接口的实现类
	 * 
	 * @author Caysen
	 * 
	 * @date 2018年4月24日
	 *
	 * @param <K>
	 * @param <V>
	 */
	public interface MapNode<K,V>{
		/**
		 * 得到当前容器中的value
		 * @return 容器中的value，允许value为Null
		 */
		V getValue();
		
		/**
		 * 得到当前容器中的key
		 * @return 容器中的key，允许key为Null
		 */
		K getKey();
		
		/**
		 * 设置容器中的value
		 * @param value 值
		 */
		void setValue(V value);
		

	}
	
	/**
	 * 获取Map中对象的数量
	 * @return 存储在Map的对象的总数
	 */
	int size();
	
	/**
	 * 清空Map
	 */
	void clear();
	
}
