package com.flamingmarshmallow.demo.gui;

import java.awt.Color;
import java.util.function.BiConsumer;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.flamingmarshmallow.demo.service.InOutService;
import com.flamingmarshmallow.demo.service.SimpleDemoObject;

@SuppressWarnings("serial")
public class SearchPane extends JPanel {
	
	public SearchPane(final BiConsumer<Long, SimpleDemoObject> searchSuccessUpdate, final InOutService<Long, SimpleDemoObject> service) {

		JTextField searchText = new JTextField(16);
    	JButton searchButton = new JButton("search");

    	searchButton.addActionListener(new SearchListener(service, searchText, searchSuccessUpdate));
    	add(searchText);
    	add(searchButton);
    	setBackground(Color.WHITE);
	}
	
}