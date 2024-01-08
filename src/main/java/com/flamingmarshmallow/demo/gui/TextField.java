package com.flamingmarshmallow.demo.gui;

import java.awt.Color;

import javax.swing.JTextField;

public class TextField extends JTextField {
	
	public TextField(final int columns) {
		super(columns);
		setBackground(Color.WHITE);
		setForeground(Color.BLACK);
	}
	
}