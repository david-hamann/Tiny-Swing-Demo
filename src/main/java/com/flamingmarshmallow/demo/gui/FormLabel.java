package com.flamingmarshmallow.demo.gui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class FormLabel extends JLabel {
	
	public FormLabel(final String text) {
		super(text);
		setForeground(Color.BLACK);
		setAlignmentY(Component.TOP_ALIGNMENT);
	}
	
}