package org.aidework.core.collection;

import java.util.Collection;

public interface Queue<T> extends Collection<T>{
	T peek();
	T pop();
}
