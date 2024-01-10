package com.flamingmarshmallow.demo.gui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import com.flamingmarshmallow.demo.service.KeyValueDataService;
import com.flamingmarshmallow.demo.service.KeyValueDataService.Data;
import com.flamingmarshmallow.demo.service.Widget;

@SuppressWarnings("serial")
public class AppPane extends JPanel {

	private final JSplitPane pane;
	
	public AppPane(final KeyValueDataService<Long, Widget> service) {
		GridBagConstraints c = new GridBagConstraints();

		
		DataPane dataPane = new DataPane(service);
		JList<Data<Long, Widget>> objectList = new ObjectList(service, (k,v) -> dataPane.updateData(k, v));
		dataPane.registerSaveConsumer((l,o) -> {
			((ObjectList) objectList).updateListing();
		});

		JPanel indexPane = new IndexPane(objectList);
		
		JSplitPane indexedPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		indexedPane.setLeftComponent(indexPane);
		indexedPane.setRightComponent(dataPane);
		
		pane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		pane.setTopComponent(new SearchPane((k, v) -> dataPane.updateData(k, v), service));
		
		pane.setBottomComponent(indexedPane);
		
		pane.setEnabled(false);
		setLayout(new BorderLayout());
		add(pane);
	}
	
	
}

