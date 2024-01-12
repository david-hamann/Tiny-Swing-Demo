package com.flamingmarshmallow.demo.service;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * A simple service interface.
 * 
 * @param <K> key
 * @param <V> value
 */
public interface KeyValueDataService<K, V> {
	
	public static record Data<K, V>(K id, V value) {
		public Data {
			Objects.requireNonNull(id);
			Objects.requireNonNull(value);
		}
	}
	
	/**
	 * Returns the number of elements in the data.
	 * @return
	 */
	public int size();

	/**
	 * Returns a single object if the key is found.
	 * @param key
	 * @return
	 */
	public V get(final K key);

	/**
	 * Returns all of the objects where the key exists.
	 * @param keys
	 * @return
	 */
	public default List<Data<K, V>> getAll(final K[] keys) { //(final K... keys) {
		return this.getAll(Set.of(keys));
	}

	/**
	 * Returns all of the objects for the keys if they exist.
	 * @param keys
	 * @return
	 */
	public default List<Data<K, V>> getAll(final Set<K> keys) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Saves an object and returns the new key.
	 * @param obj
	 */
	public K save(final V obj);

	/**
	 * Saves the object with the provided key.
	 * @param key
	 * @param obj
	 */
	public void save(final K key, final V obj);

	/**
	 * Deletes the object with the provided key.
	 * @param key
	 */
	public V delete(final K key);
	
	/**
	 * Returns a paged view of the data.
	 * @param offset
	 * @param limit
	 * @return
	 */
	public default List<Data<K, V>> getAll(final int offset, final int limit) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Returns a paged view of the search results.  Subsequent searches with the identical
	 * searchTerm will not need to operate a new search unless the search cache has timed-out.
	 * 
	 * @param searchTerm
	 * @param offset
	 * @param limit
	 * @return
	 */
	public default List<Data<K, V>> search(final String searchTerm, final int offset, final int limit) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Returns the first 100 matches.
	 * @param searchTerm
	 * @return
	 */
	public default List<Data<K, V>> search(final String searchTerm) {
		return this.search(searchTerm, 0, 100);
	}
	
}
