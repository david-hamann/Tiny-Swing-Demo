package com.flamingmarshmallow.demo.service;

import java.util.List;

import com.flamingmarshmallow.demo.service.KeyValueDataService.Data;

public interface Paging<K,V> {
	
	static final int DEFAULT_PAGE_SIZE = 25;
	
	static final int MAX_PAGE_SIZE = 1000;
	
	/**
	 * Returns the specified page with the default page size.
	 * @param pageNumber
	 * @return
	 */
	public default List<Data<K, V>> getPage(final int pageNumber) {
		return this.getPage(pageNumber, DEFAULT_PAGE_SIZE);
	}

	/**
	 * Returns the specified page, based on the supplied page size.
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public List<Data<K, V>> getPage(final int pageNumber, final int pageSize);

	/**
	 * Returns the number of pages for the default page size.
	 * @return
	 */
	public default int pageCount() {
		return this.pageCount(DEFAULT_PAGE_SIZE);
	}
	
	/**
	 * Returns the number of pages for the given page size.
	 * @param pageSize
	 * @return
	 */
	public int pageCount(final int pageSize);

	/**
	 * Returns the next page from the current page.  If there is no
	 * next page, then the method will throw
	 * {@link Paging.NoPageExists NoPageExists}
	 * 
	 * @param currentPage
	 * @return
	 */
	public default List<Data<K, V>> getNextPage(final int currentPage) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Returns the previous page from the current page.  If there is no previous pages,
	 * then the method will throw {@link Paging.NoPageExists NoPageExists}
	 * 
	 * @param currentPage
	 * @return
	 */
	public default List<Data<K, V>> getPreviousPage(final int currentPage) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Thrown when a requested page does not exist.
	 */
	@SuppressWarnings("serial")
	public static class NoPageExists extends RuntimeException {}

	/**
	 * Thrown when a data offset is out of range.
	 */
	@SuppressWarnings("serial")
	public static class OffsetOutOfRange extends RuntimeException {}
	
	/**
	 * Thrown when a page number is invalid.
	 */
	@SuppressWarnings("serial")
	public static class InvalidPageNumber extends RuntimeException {}

	/**
	 * Throw when a page size is invalid.
	 */
	@SuppressWarnings("serial")
	public static class InvalidPageSize extends RuntimeException {}
	
}
