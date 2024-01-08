package com.flamingmarshmallow.demo.gui;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;
import java.util.function.BiConsumer;

import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.flamingmarshmallow.demo.service.InOutService;
import com.flamingmarshmallow.demo.service.SimpleDemoObject;

@SuppressWarnings("serial")
public class ObjectList extends JList<Map.Entry<Long, SimpleDemoObject>> {
	
	private static Logger LOGGER = LogManager.getLogger(ObjectList.class);
	
	private int offset = 0;
	private int limit = 10;
	
	private final InOutService<Long, SimpleDemoObject> service;
	
	/**
	 * A list of elements in the dataset.
	 * @param service
	 * @param selectionComsumer
	 */
	ObjectList(final InOutService<Long, SimpleDemoObject> service, final BiConsumer<Long, SimpleDemoObject> selectionComsumer) {
		this.service = service;
		this.setCellRenderer(new ObjectCellRenderer());

		addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				Map.Entry<Long, SimpleDemoObject> entry = ObjectList.this.getSelectedValue();
				LOGGER.trace("selected: {}", entry);
				selectionComsumer.accept(entry.getKey(), entry.getValue());
			}
			
		});
		
		this.updateListing();
	}
	
	public void updateListing() {
		LOGGER.info("refreshing listing");
		List<Entry<Long, SimpleDemoObject>> page = service.getAll(offset, limit);
		this.setListData(new Vector<>(page));
	}
	
	//TODO add paging...
	
}