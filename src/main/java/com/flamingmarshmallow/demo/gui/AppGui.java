package com.flamingmarshmallow.demo.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.flamingmarshmallow.demo.service.InOutService;
import com.flamingmarshmallow.demo.service.Widget;

@SuppressWarnings("serial")
public class AppGui extends JFrame {
	
	private static Logger LOGGER = LogManager.getLogger(AppGui.class);
	
	final InOutService<Long, Widget> service;

	public AppGui(final InOutService<Long, Widget> service, final String title) {
		this.service = service;
		EventQueue.invokeLater(() -> {
			try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            	LOGGER.error("Couldn't set look and feel", ex);
            }

            setTitle(title);
    		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    		setBackground(Color.WHITE);
    		setLayout(new BorderLayout());

    		add(new AppPane(service));
    		
    		setLocationRelativeTo(null);
    		setVisible(true);
    		pack();
    		
		});
	}
	
}
