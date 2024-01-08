package com.flamingmarshmallow.demo.gui;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Vector;
import java.util.function.BiConsumer;

import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.flamingmarshmallow.demo.service.InOutService;
import com.flamingmarshmallow.demo.service.Widget;

@SuppressWarnings("serial")
public class ObjectList extends JList<Map.Entry<Long, Widget>> {
	
	private static Logger LOGGER = LogManager.getLogger(ObjectList.class);
	
	private int offset = 0;
	private int limit = 25;
	
	private final InOutService<Long, Widget> service;
	
	/**
	 * A list of elements in the dataset.
	 * @param service
	 * @param selectionComsumer
	 */
	ObjectList(final InOutService<Long, Widget> service, final BiConsumer<Long, Widget> selectionComsumer) {
		this.service = service;
		this.setCellRenderer(new ObjectCellRenderer());

		addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent event) {
				Optional.ofNullable(ObjectList.this.getSelectedValue())
					    .ifPresent(e -> selectionComsumer.accept(e.getKey(), e.getValue()));
			}
			
		});
		
		this.updateListing();
	}
	
	public void updateListing() {
		LOGGER.info("refreshing listing");
		List<Entry<Long, Widget>> page = service.getAll(offset, limit);
		this.setListData(new Vector<>(page));
	}
	
	//TODO add paging...
	
}

/*
Exception in thread "AWT-EventQueue-0" java.lang.NullPointerException: Cannot invoke "java.util.Map$Entry.getKey()" because "entry" is null
	at com.flamingmarshmallow.demo.gui.ObjectList$1.valueChanged(ObjectList.java:44)
	at java.desktop/javax.swing.JList.fireSelectionValueChanged(JList.java:1831)
	at java.desktop/javax.swing.JList$ListSelectionHandler.valueChanged(JList.java:1845)
	at java.desktop/javax.swing.DefaultListSelectionModel.fireValueChanged(DefaultListSelectionModel.java:224)
	at java.desktop/javax.swing.DefaultListSelectionModel.fireValueChanged(DefaultListSelectionModel.java:204)
	at java.desktop/javax.swing.DefaultListSelectionModel.fireValueChanged(DefaultListSelectionModel.java:251)
	at java.desktop/javax.swing.DefaultListSelectionModel.changeSelection(DefaultListSelectionModel.java:448)
	at java.desktop/javax.swing.DefaultListSelectionModel.changeSelection(DefaultListSelectionModel.java:458)
	at java.desktop/javax.swing.DefaultListSelectionModel.removeSelectionIntervalImpl(DefaultListSelectionModel.java:619)
	at java.desktop/javax.swing.DefaultListSelectionModel.clearSelection(DefaultListSelectionModel.java:463)
	at java.desktop/javax.swing.JList.clearSelection(JList.java:2082)
	at java.desktop/javax.swing.JList.setModel(JList.java:1712)
	at java.desktop/javax.swing.JList.setListData(JList.java:1753)
	at com.flamingmarshmallow.demo.gui.ObjectList.updateListing(ObjectList.java:55)
	at com.flamingmarshmallow.demo.gui.AppPane.lambda$new$1(AppPane.java:25)
	at com.flamingmarshmallow.demo.gui.DataPane.saveChanges(DataPane.java:219)
	at com.flamingmarshmallow.demo.gui.DataPane.lambda$new$0(DataPane.java:146)
	at com.flamingmarshmallow.demo.gui.SaveListener.actionPerformed(SaveListener.java:35)
	at java.desktop/javax.swing.AbstractButton.fireActionPerformed(AbstractButton.java:1972)
*/