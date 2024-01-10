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

import com.flamingmarshmallow.demo.service.KeyValueDataService;
import com.flamingmarshmallow.demo.service.KeyValueDataService.Data;
import com.flamingmarshmallow.demo.service.Widget;

@SuppressWarnings("serial")
public class ObjectList extends JList<Data<Long, Widget>> {
	
	public static enum Page {
		HAS_PREV, HAS_NEXT;
	}
	
	private static Logger LOGGER = LogManager.getLogger(ObjectList.class);
	
	private int offset = 0;
	private int limit = 25;
	
	private final KeyValueDataService<Long, Widget> service;
	
	/**
	 * A list of elements in the dataset.
	 * @param service
	 * @param selectionComsumer
	 */
	ObjectList(final KeyValueDataService<Long, Widget> service, final BiConsumer<Long, Widget> selectionComsumer) {
		this.service = service;
		this.setCellRenderer(new ObjectCellRenderer());

		addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent event) {
				Optional.ofNullable(ObjectList.this.getSelectedValue())
					    .ifPresent(d -> selectionComsumer.accept(d.id(), d.value()));
			}
			
		});
		
		this.updateListing();
	}
	
	public void updateListing() {
		LOGGER.info("refreshing listing");
		List<Data<Long, Widget>> page = service.getAll(offset, limit);
		this.setListData(new Vector<>(page));
	}
	
	/**
	 * Calling the change page with previous or next will update the offset to return a different page.
	 * @param action
	 * @param consumer
	 */
	public void changePage(final NavAction action, final Consumer<Page> consumer) {
		switch (action) {
		case NEXT:
			LOGGER.info("next page");
			break;
		case PREV:
			LOGGER.info("last page");
			break;
		}
		
		//callback with current state of paging
		consumer.accept(Page.HAS_NEXT);
	}
	
	@SuppressWarnings("unused")
	private int calcOffset(final NavAction action) {
		
		int size = service.size();
		
		
		
		return 0;
	}
	
}
