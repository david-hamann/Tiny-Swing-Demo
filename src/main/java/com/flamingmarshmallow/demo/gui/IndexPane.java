package com.flamingmarshmallow.demo.gui;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.Map;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.flamingmarshmallow.demo.service.Widget;

@SuppressWarnings("serial")
public class IndexPane extends JPanel {
	
	public IndexPane(final JList<Map.Entry<Long, Widget>> objectList) {
		setBackground(Color.WHITE);
		setLayout(new GridLayout(1,0));
		
		objectList.setBackground(Color.CYAN);
		objectList.setForeground(Color.BLACK);
		add(new JScrollPane(objectList));
	}
	
	
}