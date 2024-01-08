package com.flamingmarshmallow.demo.gui;

import java.awt.Color;
import java.awt.event.ActionListener;
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
//		searchText.setOpaque(true);
		searchText.setBackground(Color.WHITE);
		
		JButton searchButton = new JButton("search");

		ActionListener searchListener = new SearchListener(service, searchText, searchSuccessUpdate);
		
		searchText.addActionListener(searchListener);
    	searchButton.addActionListener(searchListener);
    	
    	add(searchText);
    	add(searchButton);
    	setBackground(Color.CYAN);
	}
	
}