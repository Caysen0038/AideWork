package org.aidework.core.object;


/**
 * 
 * @author Caysen
 * 
 * 对象克隆器，更深层次的克隆，不同于object的浅克隆
 *
 */

public class CloneDuplicator extends Duplicator {

	@Override
	public <T> T duplicate(Object src, Class<T> clazz) {
		return null;
	}
}
