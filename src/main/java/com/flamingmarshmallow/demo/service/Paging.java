package com.flamingmarshmallow.demo.service;

import java.util.List;

import com.flamingmarshmallow.demo.service.InOutService.Data;

public interface Paging<K,V> {
	
	static int defaultPageSize = 25;
		
	public default List<Data<K, V>> getPage(final int pageNumber) {
		return this.getPage(pageNumber, defaultPageSize);
	}
	
	public List<Data<K, V>> getPage(final int pageNumber, final int pageSize);
	
	public int pageCount(final int pageSize);
	
}
