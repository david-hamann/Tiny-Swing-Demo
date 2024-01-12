package com.flamingmarshmallow.demo.gui;

import java.util.List;
import java.util.Optional;
import java.util.Vector;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.flamingmarshmallow.demo.service.KeyValueDataService.Data;
import com.flamingmarshmallow.demo.service.Paging;
import com.flamingmarshmallow.demo.service.Paging.PageNav;
import com.flamingmarshmallow.demo.service.Paging.PagingDetail;
import com.flamingmarshmallow.demo.service.Widget;

@SuppressWarnings("serial")
public class ObjectList extends JList<Data<Long, Widget>> {
	
	private static Logger LOGGER = LogManager.getLogger(ObjectList.class);
	
	private int currentPage = 1;
	
//	private int offset = 0;
	private int limit = 20;
	
	private final Paging<Long, Widget> service;
	
	/**
	 * A list of elements in the dataset.
	 * @param service
	 * @param selectionComsumer
	 */
	ObjectList(final Paging<Long, Widget> service, final BiConsumer<Long, Widget> selectionComsumer) {
		this.service = service;
		this.setCellRenderer(new ObjectCellRenderer());

		addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent event) {
				Optional.ofNullable(ObjectList.this.getSelectedValue())
					    .ifPresent(d -> selectionComsumer.accept(d.id(), d.value()));
			}
			
		});
		
//		this.updateListing();
	}
	
	public void updateListing() {
		LOGGER.info("refreshing listing");
		try {
			List<Data<Long, Widget>> page = service.getPage(currentPage, limit);
			this.setListData(new Vector<>(page));
		} catch (Exception ex) {
			LOGGER.error("error: page={}, limit={}", currentPage, limit, ex);
			
		}
	}
	
	public void initializePages(final Consumer<PagingDetail> consumer) {
		updateListing();
		final int pages = service.pageCount(this.limit);
		PagingDetail pageDetail = new PagingDetail(PageNav.get(this.currentPage, pages), this.currentPage, pages);
		consumer.accept(pageDetail);
	}
	
	public int getPageCount() {
		return service.pageCount(this.limit);
	}
	
	public int getCurrentPage() {
		return this.currentPage;
	}
	
	/**
	 * Calling the change page with previous or next will update the offset to return a different page.
	 * @param action
	 * @param consumer
	 */
	public void changePage(final NavAction action, final Consumer<PagingDetail> consumer) {
		final int pages = service.pageCount(this.limit);
		switch (action) {
		case NEXT:
			LOGGER.info("next page");
			if (this.currentPage < pages) {
				List<Data<Long, Widget>> page = service.getPage(++currentPage, limit);
				this.setListData(new Vector<>(page));
			}
			break;
		case PREV:
			LOGGER.info("last page");
			if (this.currentPage > 1) {
				List<Data<Long, Widget>> page = service.getPage(--currentPage, limit);
				this.setListData(new Vector<>(page));
			}
			break;
		}
		
		//callback with current state of paging
		PagingDetail pageDetail = new PagingDetail(PageNav.get(this.currentPage, pages), this.currentPage, pages);
		consumer.accept(pageDetail);
	}

	
	
//	@SuppressWarnings("unused")
//	private int calcOffset(final NavAction action) {
//		
//		int size = service.size();
//		
//		
//		
//		return 0;
//	}
	
}
