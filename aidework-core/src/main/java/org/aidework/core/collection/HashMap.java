package org.aidework.core.collection;

/**
 * 继承自AbtractMap,实现了Map操作
 * 以HashCode为关键字实现表的分点存储
 * 
 *
 *
 * @author Caysen
 * 
 * @date 2018年4月26日
 *
 * @param <K>
 * @param <V>
 */
public class HashMap<K,V> extends AbstractMap<K,V>{
	/**
	 * 初始的Map容量，即Map的长度
	 */
	public static final int INITIAL_CAPACITY=1<<4;
	
	/**
	 * Map的最大容量
	 * 当Map长度超过最大容量，则不再扩张
	 * 
	 */
	// 因为int是带符号整形，占4个字节，所以最高只有32位，而最高位为符号位，所以不能左移31位，最高只能左移30位
	public static final int MAXIMUM_CAPACITY=1<<30;

	
	/**
	 * 初始化负载因子
	 * 当Map的实际容量超过capacity*loadFactor，则需要扩张Map长度
	 * 负载因子越小，则Map元素的装填都越稀疏，反之则越密集
	 * 负载因子的默认值采用JAVA HashMap默认值，若无其他需求，其实最合适的负载因子值
	 * 
	 */
	public static final float INITIAL_LOAD_FACTOR=0.75F;
	
	
	/**
	 * 挂载Map元素的Map，以数组形式维护
	 */
	private Node<K,V>[] table;
	
	/**
	 * Map的实际容量，该容量不会超过capacity*loadFactor，除非capacity达到了最大值
	 */
	private int size;
	
	/**
	 * 当前Map的容量
	 */
	private int capacity;
	
	/**
	 * 当前Map设置的负载因子
	 */
	private float loadFactor;
	
	/**
	 * 
	 * Map元素实现类
	 * 因为会存在hashCode碰撞的情况，所以元素节点以单链表的形式实现，可实现多元素挂载
	 *
	 *
	 * @author Caysen
	 * 
	 * @date 2018年4月26日
	 *
	 * @param <K> key
	 * @param <V> value
	 */
	public static final class Node<K,V> implements MapNode<K,V>{
		/**
		 * 本节点的hashcode
		 */
		private int hashCode;
		private K key;
		private V value;
		/**
		 * 下一个节点
		 */
		private Node<K,V> next;
		
		public Node(int hashCode, K key, V value, Node<K,V> next){
			this.hashCode=hashCode;
			this.key=key;
			this.value=value;
			this.next=next;
		}
		
		@Override
		public V getValue() {
			
			return this.value;
		}

		@Override
		public K getKey() {
			
			return this.key;
		}

		@Override
		public void setValue(V value) {
			this.value=value;
			
		}

		
		@Override
		public int hashCode(){
			return this.hashCode;
		}
		
		@Override
		public boolean equals(Object obj){
			if(obj==null||!(obj instanceof Node)){
				return false;
			}
			Node node=(Node)obj;
			if(node.hashCode!=hashCode){
				return false;
			}
			return node.key.equals(key)&&node.value.equals(value);
			
		}
		
		@Override
		public String toString(){
			return this.key+":"+this.value;
		}
		
	}
	
	
	public HashMap(){
		this(INITIAL_CAPACITY, INITIAL_LOAD_FACTOR);
	}
	
	public HashMap(int capacity){
		this(capacity, INITIAL_LOAD_FACTOR);
	}

	@SuppressWarnings("unchecked")
	public HashMap(int capacity, float loadFactor){
		if((this.capacity=capacity)>MAXIMUM_CAPACITY){
			this.capacity=MAXIMUM_CAPACITY;
		}
		this.loadFactor=loadFactor;
		
		table=new Node[capacity];
	}
	
	@Override
	public V put(K key, V value) {
		if((float)(size/capacity)>loadFactor){
			resize(capacity<<1);
		}
		Node<K,V> node=new Node<>(getKeyHash(key), key, value, null);
		return putNode(node);
	}
	
	@Override
	public V get(K key) {
		
		return getNode(key).value;
	}
	
	@Override
	public V remove(K key) {
		
		return removeNode(key);
	}

	@Override
	public List<V> valueList() {
		List<V> list=new ArrayList<>();
		for(Node<K,V> root:table){
			
			while(root!=null){
				list.add(root.value);
				root=root.next;
			}
			
		}
		return list;
	}

	@Override
	public List<K> keyList() {
		List<K> list=new ArrayList<>();
		for(Node<K,V> root:table){
			
			while(root!=null){
				list.add(root.key);
				root=root.next;
			}
			
		}
		return list;
	}
	
	@Override
	public List<MapNode<K, V>> mapList() {
		List<MapNode<K,V>> list=new ArrayList<>();
		for(Node<K,V> root:table){
			while(root!=null){
				list.add(root);
				root=root.next;
			}
		}
		
		
		return list;
	}

	@Override
	public int size() {
		
		return this.size;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void clear() {
		table=new Node[capacity];
	}
	
	
	private Node<K,V> getNode(K key){
		int position=getPosition(key);
		Node<K,V> root=table[position];
		while(root!=null){
			if(root.key.equals(key)){
				return root;
			}else{
				root=root.next;
			}
		}
		return null;
	}
	
	private V putNode(Node<K,V> node){
		int position=getPosition(node.key);
		Node<K,V> root;
		// 未碰撞
		if((root=table[position])==null){
			table[position]=node;
			
		}else{
			// hash发生碰撞，以单向链表存储
			
			if(root.key.equals(node.key)){
				root.value=node.value;
				return node.value;
			}
			
			Node<K,V> temp;
			while((temp=root.next)!=null){
				if(temp.key.equals(node.key)){
					/**
					 * key = new key 长度不发生变化，只改变node的value
					 * 设置完成后直接返回新值
					 */
					temp.value=node.value;
					return node.value;
				}
				root=temp;
			}
			/**
			 * 循环退出后，说明没有发生key = new key的情况
			 * 直接在挂在桶上
			 */
			root.next=node;
		}
		/**
		 * 长度的增加只有在key不同的情况下发生
		 * 如果key==new key，则不增长度
		 */
		size++;
		return node.value;
	}
	
	/**
	 * 删除指定key的桶位节点对象
	 * @param key 被删除的桶位节点对象的key
	 * @return 被删除的节点对象的值
	 */
	private V removeNode(K key){
		int position=getPosition(key);
		Node<K,V> root;
		if((root=table[position])==null){
			return null;
		}else{
			Node<K,V> last=null;
			while(root!=null){
				if(root.key.equals(key)){
					break;
				}
				last=root;
				root=root.next;
			}
			if(root==null){
				return null;
			}
			V value;
			value=root.value;
			if(root.next!=null){
				root.value=root.next.value;
				root.key=root.next.key;
				root.hashCode=root.next.hashCode;
				root.next=root.next.next;
			}
			if(last!=null){
				last.next=root;
			}
			size--;
			return value;
		}
	}
	
	
	/**
	 * 获取key的hash code
	 * @param key key值
	 * @return key的hash code
	 */
	private int getKeyHash(K key){
		if(key==null){
			return 0;
		}
		int hash=key.hashCode();
		/*
		 * int 占位四个字节
		 * 表的最大长度不会超过1<<30,也就是两个字节
		 * 所以让hash后16位于前16位先行计算
		 */
		return hash^hash>>>16;
	}
	
	/**
	 * 得到该key在表中的桶位
	 * @param key 被索引的key
	 * @return 表中索引位置
	 */
	private int getPosition(K key){
		
		return (getKeyHash(key) & capacity)-1;
	}
	
	/**
	 * 扩张表
	 * 按照新的capacity扩张原本的表，并按照新表重新计算桶位
	 * @param capacity
	 */
	private void resize(int capacity){
		if(capacity > MAXIMUM_CAPACITY){
			capacity = MAXIMUM_CAPACITY;
		}
		this.capacity=capacity;
		@SuppressWarnings("unchecked")
		Node<K,V>[] newTable=new Node[capacity];
		Node<K,V>[] oldTable=table;
		table=newTable;
		// 重新计算hash位置装入新表中
		for(Node<K,V> node:oldTable){
			putNode(node);
		}
	}

	
	
	
}
