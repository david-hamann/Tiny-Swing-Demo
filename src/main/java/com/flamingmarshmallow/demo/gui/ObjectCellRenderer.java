package com.flamingmarshmallow.demo.gui;

import java.awt.Color;
import java.awt.Component;
import java.util.Map.Entry;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import com.flamingmarshmallow.demo.service.InOutService.Data;
import com.flamingmarshmallow.demo.service.Widget;

public class ObjectCellRenderer extends JLabel implements ListCellRenderer<Data<Long, Widget>> {
	
	//TODO do we need to keep track of the element's id here?
	
	ObjectCellRenderer() {
		//TODO
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends Data<Long, Widget>> list, Data<Long, Widget> value,
			int index, boolean isSelected, boolean cellHasFocus) {
		
		this.setText(value.value().name + " (" + value.id() + ")");
		
		//https://docs.oracle.com/javase/8/docs/api/javax/swing/ListCellRenderer.html
		Color bg;
		Color fg;
		boolean isOpaque;

		JList.DropLocation dropLocation = list.getDropLocation();
		if (dropLocation != null && !dropLocation.isInsert() && dropLocation.getIndex() == index) {
			bg = Color.RED;
			fg = Color.WHITE;
			isOpaque = true;
		} else if (isSelected) {
			bg = Color.WHITE;
			fg = Color.BLACK;
			isOpaque = true;
		} else {
			bg = Color.WHITE;
			fg = Color.BLACK;
			isOpaque = false;
		}

		this.setOpaque(isOpaque);
		setBackground(bg);
		setForeground(fg);
		
		return this;
	}
	
}