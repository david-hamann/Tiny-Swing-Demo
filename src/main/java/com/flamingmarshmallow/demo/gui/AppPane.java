package com.flamingmarshmallow.demo.gui;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import com.flamingmarshmallow.demo.service.InOutService;
import com.flamingmarshmallow.demo.service.Widget;

@SuppressWarnings("serial")
public class AppPane extends JPanel {

	private final JSplitPane pane;
	
	public AppPane(final InOutService<Long, Widget> service) {
		
		DataPane dataPane = new DataPane(service);
		JList<Map.Entry<Long, Widget>> objectList = new ObjectList(service, (k,v) -> dataPane.updateData(k, v));
		dataPane.registerSaveConsumer((l,o) -> {
			((ObjectList) objectList).updateListing();
		});

		JPanel indexPane = new JPanel();
		indexPane.setLayout(new GridBagLayout());
				
		indexPane.add(new JLabel("inventory"), DataPane.getGBConstraint(0, 0));
		indexPane.add(objectList, DataPane.getGBConstraint(0, 1));
		
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

