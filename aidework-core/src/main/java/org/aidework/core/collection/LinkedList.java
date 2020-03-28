package org.aidework.core.collection;


/**
 * 链表集合
 * 该实现类通过维护一个双向链表来存储数据集合
 * 链表的节点为内部嵌套类Node
 * 本类中也许会抛出NEP，调用方应该考虑到并处理NEP
 * 集合元素对象能够包含Null值，调用方在获取元素的时候需要考虑到返回Null的可能性
 * 
 * @author Caysen
 * 
 * @date 2018年4月23日
 *
 * @param <T>
 */
public class LinkedList<T> extends AbstractList<T>{
	
	/**
	 * 当前集合的根节点引用，也就是第一个元素
	 * 该引用总是指向第一个节点，一般不发生改变，除非根元素被删除，那么将移到后一个节点的地址上
	 */
	private Node<T> root=null;
	
	/**
	 * 当前集合的尾节点引用，也就是最后一个元素
	 * 每当删除或者增加后，该引用指向的地址一定会发生变化
	 */
	private Node<T> tail=null;

	/**
	 * 集合中节点的个数
	 */
	private int size=0;
	
	/**
	 * 默认构造方法，无任何操作
	 */
	public LinkedList(){
		
	}
	
	/**
	 * 设置根节点
	 * @param root 根节点数据
	 */
	public LinkedList(T root){
		
		add(root);
	}
	

	public static class Node<E>{
		/**
		 * 节点中的数据
		 */
		private E data;
		/**
		 * 下一个节点
		 */
		private Node<E> next;
		/**
		 * 上一个节点
		 */
		private Node<E> pre;
		
		/**
		 * 默认构造方法，将把data数据设置为null
		 */
		public Node(){
			this(null);
		}
		
		/**
		 * 指定data内容
		 * @param data 节点数据
		 */
		public Node(E data){
			this.data=data;
		}
		
		/**
		 * 获取节点中的数据
		 * @return 节点中的数据，可能为null
		 */
		public E getData() {
			return data;
		}
		
		/**
		 * 设置节点中的数据
		 * @param data 节点数据
		 */
		public void setData(E data) {
			this.data = data;
		}
		
		/**
		 * 得到当前结点的下一个节点引用地址
		 * @return 下一个节点引用地址
		 */
		public Node<E> getNext() {
			return next;
		}
		
		/**
		 * 设置下一个节点引用地址
		 * @param next 节点引用地址
		 */
		public void setNext(Node<E> next) {
			this.next = next;
		}
		
		/**
		 * 得到前一个节点引用地址
		 * @return 得到前一个节点引用地址
		 */
		public Node<E> getPre() {
			return pre;
		}
		
		/**
		 * 设置前一个节点引用地址
		 * @param pre 节点引用地址
		 */
		public void setPre(Node<E> pre) {
			this.pre = pre;
		}
	}
	
	
	@Override
	public boolean add(T e) {
		
		Node<T> next=new Node<>(e);
		next.setPre(tail);
		if(tail!=null){
			tail.setNext(next);
		}else{
			root=next;
		}
		tail=next;
		size++;
		return true;
	}
	
	
	@Override
	public boolean addAll(Collection<? extends T> col) {
		if(isNull(col)){
			return false;
		}
		boolean success=true;
		for(T temp:col){
			if(!add(temp)){
				success=false;
			}
		}
		return success;
	}
	
	@Override
	public boolean contains(T obj) {
		if(isEmpty()){
			return false;
		}
		return getNode(obj)!=null;
	}
	
	
	@Override
	public boolean containsAll(Collection<? extends T> col) {
		if(col==null){
			System.err.println("Wraning:index out of bounds");
		}
		boolean success=true;
		for(T temp:col){
			if(!(success=contains(temp))){
				break;
			}
		}
		return success;
	}

	@Override
	public T remove(T obj) {
		Node<T> node=getNode(obj);
		if(node==null){
			return null;
		}
		node=removeNode(node);
		T data=node.getData();
		node.setData(null);
		return data;
	}
	
	/**
	 * 删除一个集合的对象
	 * 并将被删除的对象以链表集合的形式组织并返回
	 */
	@Override
	public LinkedList<? extends T> removeAll(Collection<? extends T> col) {
		LinkedList<T> list=new LinkedList<>();
		if(isNull(col)){
			return null;
		}
		for(T temp:col){
			list.add(remove(temp));
		}
		return list;
	}
	

	@Override
	public T replace(T oldObj, T newObj) {
		if(isEmpty()){
			return null;
		}
		int i=getIndex(oldObj);
		if(i==-1){
			return null;
		}
		Node<T> node=getNode(i);
		oldObj=node.getData();
		node.setData(newObj);
		return oldObj;
	}
	
	@Override
	public int size() {
		
		return this.size;
	}
	
	@Override
	public boolean isEmpty() {
		
		return size()==0;
	}
	

	@Override
	public void clear() {
		root=null;
		tail=null;
		size=0;
		
	}
	
	@Override
	public T get(int index) {
		if(index<0||index>size()){
			System.err.println("Wraning:index out of bounds");
			return null;
		}

		
		return getNode(index).getData();
	}

	@Override
	public T getFirst() {
		if(this.isEmpty()){
			return null;
		}
		return root.getData();
	}

	@Override
	public T getLast() {
		if(this.isEmpty()){
			return null;
		}
		return tail.getData();
	}


	@Override
	public int getIndex(T obj) {
		
		if(isEmpty()){
			return -1;
		}
		/**
		 * 如果先进行contains判断
		 * 最坏的情况下会遍历两次链表
		 * 效率上来讲很差，不宜采用
		 */
//		if(findNode(obj)==null){
//			return -1;
//		}
		int i=0;
		Node<T> node=root;
		while(!node.getData().equals(obj)){
			i++;
			node=node.getNext();
			// 不能肯定集合包含该对象，也能会遍历到尾节点，需要对节点是否为空判断
			if(node==null){
				return -1;
			}
		}
		return i;
	}

	@Override
	public T remove(int index) {
		
		Node<T> node=removeNode(getNode(index));
		if(node==null){
			return null;
		}
		T data=node.getData();
		node.setData(null);
		return data;
	}

	
	@Override
	public Object[] toArray() {
		Object[] arr=new Object[size()];
		Node<T> node=root;
		int i=0;
		while(node!=null){
			arr[i++]=node.getData();
			node=node.getNext();
		}
		return arr;
	}

	@Override
	public boolean equals(Object obj){
		if(obj==null){
			return false;
		}
		if(!(obj instanceof LinkedList)){
			return false;
		}
		@SuppressWarnings("unchecked")
		LinkedList<Object> list=(LinkedList<Object>)obj;
		if(list.size()!=this.size()){
			return false;
		}
		Object[] tempArr=list.toArray();
		Object[] arr=toArray();
		for(int i=0,n=arr.length;i<n;i++){
			if(arr[i]==null){
				if(tempArr[i]!=null){
					return false;
				}
			}else if(!arr[i].equals(tempArr[i])){
					return false;
			}
		}
		return true;
	}
	
	/**
	 * 在链表中查找该对象
	 * @param obj 被查找的对象
	 * @return 对象所在的节点，如果为null，则表示链表不包含该对象
	 */
	private Node<T> getNode(T obj){
		if(obj==null){
			throw new NullPointerException();
		}
		Node<T> node=root;
		while(node!=null){
			if(node.getData()==null){
				if(obj==null){
					return node;
				}
			}else if(node.getData().equals(obj)){
				return node;
			}
			node=node.getNext();
		}
		return null;
	}
	
	/**
	 * 得到指定索引位置的节点
	 * @param index 指定的索引位置
	 * @return 指定的节点，如果指定的索引为超出size范围，则返回null
	 */
	private Node<T> getNode(int index){
		if(index<0||index>=size){
			return null;
		}
		if(index<size>>1){
			Node<T> node=root;
			while(index--!=0){
				node=node.getNext();
			}
			return node;
		}else{
			Node<T> node=tail;

			for(int i=size-1;i>index;i--){
				node=node.pre;
			}
			return node;
		}

	}
	
	/**
	 * 检查对象是否为null，防止NEP
	 * 此方法仅仅判断是否为Null，不对对象类型或内容进行判断
	 * @param obj 被检查的对象
	 * @return 是否为空，true为空对象，false为非空对象
	 */
	private boolean isNull(Object obj){
		if(obj==null){
			System.err.println("Warning:The paramter can no be Null.");
			return true;
		}
		return false;
	}
	
	/**
	 * 将指定节点从链表中删除
	 * @param node 即将被删除的节点
	 * @return 被删除的节点
	 */
	private Node<T> removeNode(Node<T> node){
		if(node.getPre()!=null){
			node.getPre().setNext(node.getNext());
		}else{
			root=node.getNext();
		}
		if(node.getNext()!=null){
			node.getNext().setPre(node.getPre());
		}else{
			tail=node.getPre();
		}
		size--;
		node.setNext(null);
		node.setPre(null);
		return node;
	}
	


}
