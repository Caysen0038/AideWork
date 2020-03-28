package org.aidework.core.collection;

import java.util.Iterator;

public abstract class AbstractMap<K,V> implements Map<K,V>{
	
	
	@Override
	public Iterator<MapNode<K, V>> iterator() {
		Iterator<MapNode<K,V>> iterator=new MapIterator<MapNode<K,V>>();
		return iterator;
	}
	
	private final class MapIterator<E extends MapNode<K,V>> implements Iterator<MapNode<K,V>>{

		private List<MapNode<K,V>> list;
		private int current=0;
		private int size=-1;
		public MapIterator(){
			this.list=mapList();
			this.size=size();
		}
		
		@Override
		public boolean hasNext() {
			
			return current<size-1;
		}

		@Override
		public MapNode<K, V> next() {
			if(hasNext()){
				return list.get(current++);
			}
			return null;
		}
		
	}
	
	
	@Override
	public String toString(){
		List<MapNode<K,V>> list=mapList();
		StringBuilder sb=new StringBuilder(list.size());
		sb.append("[");
		for(MapNode<K,V> node:list){
			sb.append(node.getKey())
				.append(":")
				.append(node.getValue())
				.append(",");
		}
		sb.append("]");
		return sb.toString();
	}
	
	
}
