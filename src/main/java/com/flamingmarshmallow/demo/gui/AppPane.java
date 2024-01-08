package com.flamingmarshmallow.demo.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.util.Map;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import com.flamingmarshmallow.demo.service.InOutService;
import com.flamingmarshmallow.demo.service.Widget;

@SuppressWarnings("serial")
public class AppPane extends JPanel {

	private final JSplitPane pane;
	
	public AppPane(final InOutService<Long, Widget> service) {
		GridBagConstraints c = new GridBagConstraints();

		
		DataPane dataPane = new DataPane(service);
		JList<Map.Entry<Long, Widget>> objectList = new ObjectList(service, (k,v) -> dataPane.updateData(k, v));
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
	
	
	public static class IndexPane extends JPanel {
		
		public IndexPane(final JList<Map.Entry<Long, Widget>> objectList) {
			setBackground(Color.WHITE);
			setLayout(new GridLayout(1,0));
			
			objectList.setBackground(Color.CYAN);
			objectList.setForeground(Color.BLACK);
			add(objectList);
		}
		
		
	}
	
	
}

