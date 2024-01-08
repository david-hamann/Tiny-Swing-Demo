package com.flamingmarshmallow.demo.gui;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.function.BiConsumer;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.flamingmarshmallow.demo.service.InOutService;
import com.flamingmarshmallow.demo.service.Widget;

@SuppressWarnings("serial")
public class SearchPane extends JPanel {
	
	public SearchPane(final BiConsumer<Long, Widget> searchSuccessUpdate, final InOutService<Long, Widget> service) {

		JTextField searchText = new TextField(16);
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