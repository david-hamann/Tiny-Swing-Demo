package com.flamingmarshmallow.demo.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.lang.UnsupportedOperationException;

/**
 * A simple service interface.
 * 
 * @param <K> key
 * @param <V> value
 */
public interface InOutService<K, V> {
	
	public V get(final K key);
	
	public default Map<K, V> getAll(final K[] keys) { //(final K... keys) {
		return this.getAll(Set.of(keys));
	}
	
	public default Map<K, V> getAll(final Set<K> keys) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Saves an object and returns the new key.
	 * @param obj
	 */
	public K save(final V obj);
	
	public void save(final K key, final V obj);

	public void delete(final K key);
	
	/**
	 * Supports insertion ordering through a LinkedHashMap.
	 * @param offset
	 * @param limit
	 * @return
	 */
	public default LinkedHashMap<K, V> getAll(final int offset, final int limit) {
		throw new UnsupportedOperationException();
	}

}
