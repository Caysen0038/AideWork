package org.aidework.core.collection;

@SuppressWarnings({"unchecked","rawtypes"})
public class ArrayList<T> extends AbstractList<T>{
	public static final int DEFAULT_CAPACITY=10;
	private Object[] data;
	private int capacity;
	private int size;
	
	public ArrayList(){
		this(DEFAULT_CAPACITY);
	}
	
	/**
	 * 初始化数组容量，默认为10
	 * @param capacity 数组长度
	 */
	public ArrayList(int capacity){
		size=0;
		this.capacity=capacity;
		data=new Object[capacity];
	}
	
	@Override
	public boolean add(T obj) {
		
		checkListCapacity();
		this.data[size++]=obj;
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
	public T get(int index) {
		if(index<0||index>=size){
			return null;
		}
		return (T)data[index];
	}

	@Override
	public T getFirst() {
		
		return (T)data[0];
	}

	@Override
	public T getLast() {
		
		return (T)data[size-1];
	}

	@Override
	public int getIndex(T obj) {
		for(int i=0;i<size;i++){
			if(data[i]==null){
				if(obj==null){
					return i;
				}
			}else if(data[i].equals(obj)){
				return i;
			}
		}
		return -1;
	}

	@Override
	public T remove(int index) {
		if(index<0||index>=size){
			return null;
		}
		Object obj=data[index];
		System.arraycopy(data, index+1, data, index, size-1-index);
		data[size-1]=null;
		size--;
		return (T)obj;
	}
	
	@Override
	public T remove(T obj) {
		int i=getIndex(obj);
		
		return remove(i);
	}

	@Override
	public List<? extends T> removeAll(Collection<? extends T> col) {
		ArrayList<T> list=new ArrayList<>();
		if(isNull(col)){
			return list;
		}
		
		for(T temp:col){
			list.add(remove(temp));
		}
		
		return list;
	}

	
	@Override
	public Object[] toArray() {
		Object[] arr=new Object[size];
		System.arraycopy(data, 0, arr, 0, size);
		return arr;
	}

	@Override
	public boolean contains(T obj) {
		
		return getIndex(obj)!=-1;
	}

	@Override
	public boolean containsAll(Collection<? extends T> col) {
		if(isNull(col)){
			return false;
		}
		for(T temp:col){
			if(!contains(temp)){
				return false;
			}
		}
		return true;
	}

	@Override
	public T replace(T oldObj, T newObj) {
		int i=getIndex(oldObj);
		if(i==-1){
			return null;
		}
		data[i]=newObj;
		return newObj;
	}

	@Override
	public int size() {
		
		return size;
	}

	@Override
	public void clear() {
		data=new Object[capacity];
		size=0;
	}

	@Override
	public boolean isEmpty() {
		
		return size()==0;
	}
	
	@Override
	public boolean equals(Object obj){
		if(obj==null){
			return false;
		}
		if(!(obj instanceof ArrayList)){
			return false;
		}
		ArrayList list=(ArrayList)obj;
		if(list.size()!=this.size()){
			return false;
		}
		for(int i=0;i<this.size;i++){
			if(list.get(i)==null){
				if(this.data[i]!=null){
					return false;
				}
			}else if(!list.get(i).equals(this.data[i])){
					return false;
				
			}
		}
		return true;
	}

	
	/**
	 * 检查集合容量是否饱满，饱满则扩张
	 */
	private void checkListCapacity(){
		if(size==capacity){
			extendList(capacity<<1);
		}
	}
	
	/**
	 * 扩展数组长度
	 * @param capacity 指定新数组的长度
	 */
	private void extendList(int capacity){
		if(capacity<=this.capacity){
			return;
		}
		Object[] arr=new Object[capacity];
		this.capacity=capacity;
		System.arraycopy(data, 0, arr, 0, data.length);
		data=arr;
	}
	
	private boolean isNull(Object obj){
		if(obj==null){
			System.err.println("Warning:The paramter can no be Null.");
			return true;
		}
		return false;
	}
	
	
}
