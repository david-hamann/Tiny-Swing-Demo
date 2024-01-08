package com.flamingmarshmallow.demo.gui;

import java.awt.Color;
import java.awt.Component;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import com.flamingmarshmallow.demo.service.SimpleDemoObject;

public class ObjectCellRenderer extends JLabel implements ListCellRenderer<Map.Entry<Long, SimpleDemoObject>> {
	
	//TODO do we need to keep track of the element's id here?
	
	ObjectCellRenderer() {
		//TODO
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends Entry<Long, SimpleDemoObject>> list,
			Entry<Long, SimpleDemoObject> entry, int index, boolean isSelected, boolean cellHasFocus) {
		
		this.setText(entry.getValue().name + " (" + entry.getKey() + ")");
		
		//https://docs.oracle.com/javase/8/docs/api/javax/swing/ListCellRenderer.html
		Color bg;
		Color fg;

		JList.DropLocation dropLocation = list.getDropLocation();
		if (dropLocation != null && !dropLocation.isInsert() && dropLocation.getIndex() == index) {
			bg = Color.blue;
			fg = Color.white;
		} else if (isSelected) {
			bg = Color.RED;
			fg = Color.WHITE;
		} else {
			bg = Color.WHITE;
			fg = Color.BLACK;
		}
		
		setBackground(bg);
		setForeground(fg);
		
		return this;
	}
	
	
	
}