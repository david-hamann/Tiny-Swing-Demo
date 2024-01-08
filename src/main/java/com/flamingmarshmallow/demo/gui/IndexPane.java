package com.flamingmarshmallow.demo.gui;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JList;
import javax.swing.JPanel;

import com.flamingmarshmallow.demo.service.Widget;

public class IndexPane extends JPanel {
	
	public IndexPane(final JList<Map.Entry<Long, Widget>> objectList) {
		setBackground(Color.WHITE);
		setLayout(new GridLayout(1,0));
		
		objectList.setBackground(Color.CYAN);
		objectList.setForeground(Color.BLACK);
		add(objectList);
	}
	
	
}