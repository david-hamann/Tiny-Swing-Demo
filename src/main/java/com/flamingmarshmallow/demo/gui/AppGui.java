package com.flamingmarshmallow.demo.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.function.Supplier;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.flamingmarshmallow.demo.service.InOutService;
import com.flamingmarshmallow.demo.service.SimpleDemoObject;

//import layout.SpringUtilities;

@SuppressWarnings("serial")
public class AppGui extends JFrame {
	
	private static Logger LOGGER = LogManager.getLogger(AppGui.class);
	
	final InOutService<Long, SimpleDemoObject> service;

	public AppGui(final InOutService<Long, SimpleDemoObject> service, final String title) {
		this.service = service;
		EventQueue.invokeLater(() -> {
			try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            	LOGGER.error("Couldn't set look and feel", ex);
            }

            @SuppressWarnings("unused")
			JFrame frame = new AppFrame(title, service);
		});
	}
	
	public class AppFrame extends JFrame {
		
		public AppFrame(final String title, final InOutService<Long, SimpleDemoObject> service) {
			setTitle(title);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setLayout(new BorderLayout());
			add(new AppPane(service));
			pack();
			setLocationRelativeTo(null);
			setVisible(true);
		}
		
	}
	
	public static class AppPane extends JPanel {

		private final JSplitPane pane;
		
		public AppPane(final InOutService<Long, SimpleDemoObject> service) {
			
			DataPane dataPane = new DataPane(service);

			pane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
			pane.setTopComponent(new SearchPane((k, v) -> dataPane.updateData(k, v), service));
			pane.setBottomComponent(dataPane);
			pane.setEnabled(false);
			setLayout(new BorderLayout());
			add(pane);
		}
		
	}
	
	static String prettyDate(final long timestamp) {
		return ZonedDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault())
				.format(DateTimeFormatter.ISO_DATE_TIME);
	}
	
	public static class FormLabel extends JLabel {
		public FormLabel(final String text) {
			super(text);
			setForeground(Color.BLACK);
			setAlignmentY(Component.TOP_ALIGNMENT);
		}
	}
	
	
}
