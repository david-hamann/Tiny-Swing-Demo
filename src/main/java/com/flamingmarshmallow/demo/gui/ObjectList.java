package com.flamingmarshmallow.demo.gui;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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
import com.flamingmarshmallow.demo.service.Widget;

@SuppressWarnings("serial")
public class ObjectList extends JList<Data<Long, Widget>> {
	
	public static enum PageNav {
		HAS_PREV, HAS_NEXT;

		/**
		 * Page must be between 1 and pages inclusive.
		 * 
		 * @param page
		 * @param pages
		 * @return
		 */
		public static Set<PageNav> get(final int page, final int pages) {
			final Set<PageNav> nav = new HashSet<>();
			if (page == 0 || page > pages) {
				throw new IllegalArgumentException();
			}
			if (page > 1) {
				nav.add(HAS_PREV);
			}
			if (page < pages) {
				nav.add(HAS_NEXT);
			}
			return nav;
		}
	}
	
	private static Logger LOGGER = LogManager.getLogger(ObjectList.class);
	
	private int currentPage = 1;
	
//	private int offset = 0;
	private int limit = 25;
	
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
	
	public void initializePages(final Consumer<Set<PageNav>> consumer) {
		updateListing();
		final int pages = service.pageCount(this.limit);
		consumer.accept(PageNav.get(this.currentPage, pages));
	}
	
	/**
	 * Calling the change page with previous or next will update the offset to return a different page.
	 * @param action
	 * @param consumer
	 */
	public void changePage(final NavAction action, final Consumer<Set<PageNav>> consumer) {
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
		consumer.accept(PageNav.get(this.currentPage, pages));
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
