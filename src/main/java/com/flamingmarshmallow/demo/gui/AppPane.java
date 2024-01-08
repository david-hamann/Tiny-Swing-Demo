package com.flamingmarshmallow.demo.gui;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

import com.flamingmarshmallow.demo.service.InOutService;
import com.flamingmarshmallow.demo.service.SimpleDemoObject;

@SuppressWarnings("serial")
public class AppPane extends JPanel {

	private final JSplitPane pane;
	
	public AppPane(final InOutService<Long, SimpleDemoObject> service) {
		
		DataPane dataPane = new DataPane(service);

		pane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		pane.setTopComponent(new SearchPane((k, v) -> dataPane.updateData(k, v), service));
		pane.setBottomComponent(dataPane);
		pane.setEnabled(false);
		setLayout(new BorderLayout());
		add(pane);
	}
	
}