package com.flamingmarshmallow.demo.service;

import java.util.List;
import java.util.Set;

/**
 * A simple service interface.
 * 
 * @param <K> key
 * @param <V> value
 */
public interface InOutService<K, V> {
	
	public V get(final K key);
	
	public default List<V> getAll(final K[] keys) { //(final K... keys) {
		return this.getAll(Set.of(keys));
	}
	
	public List<V> getAll(final Set<K> keys);
	
	public void save(final K key, final V obj);

	public void delete(final K key);
	
	public default List<V> getAll(final int offset, final int limit) {
		throw new UnsupportedOperationException();
	}

}
