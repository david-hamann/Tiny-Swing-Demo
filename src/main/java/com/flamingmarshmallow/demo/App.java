package com.flamingmarshmallow.demo;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.flamingmarshmallow.demo.service.DemoService;
import com.flamingmarshmallow.demo.service.SimpleDemoObject;

/**
 * A simple Swing Demo.
 */
public class App {
	
	private static Logger LOGGER = LogManager.getLogger(App.class);
	
    public static void main( String[] args ) {
    	
    	//TODO read args
    	
    	DemoService service = new DemoService();
    	
    	try {
			service.load("data.jsonp");
		} catch (IOException | URISyntaxException ex) {
			LOGGER.error("can't load data file: {}", ex);
			System.exit(1);
		}

    	try {
    		UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
    	} catch (Exception ex) {
    		LOGGER.error("oopsy: {}", ex);
    	}
    	
    	LOGGER.info("starting...");
//    	launchGui();
    	@SuppressWarnings("unused")
		AppFrame frame = new AppFrame(service);
    	frame.pack(); //without this, we have to resize the window to get the panels to appear???
    }
    
    
    @SuppressWarnings("serial")
	public static class AppFrame extends JFrame {
    	public AppFrame(final DemoService service) {
    		setSize(500, 500);
    		setTitle("Swing Demo");
    		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//    		setLocation(400, 0);
    		setVisible(true);
    		
    		AppPanel panel = new AppPanel(service);
    		add(panel);
    	}
    }
    
    @SuppressWarnings("serial")
	public static class AppPanel extends JSplitPane {
    	
        public AppPanel(final DemoService service) {
        	JPanel searchPanel = new JPanel();
        	JTextField searchText = new JTextField(16);
        	JButton searchButton = new JButton("search");
        	searchButton.addActionListener(new SearchListener(service, searchText));
        	
        	searchPanel.add(searchText);
        	searchPanel.add(searchButton);
        	
        	
        	JPanel dataPanel = new JPanel();
        	JTextArea dataText = new JTextArea(10, 10);
        	dataPanel.add(dataText);
        	
        	this.setOrientation(SwingConstants.HORIZONTAL);
        	this.setTopComponent(searchPanel);
        	this.setBottomComponent(dataPanel);
        	
//        	JSplitPane splitPane = new JSplitPane(SwingConstants.HORIZONTAL, searchPanel, dataPanel);
        }
        
    }
    
    public static class SearchListener implements ActionListener {

    	private final DemoService service;
    	private final JTextField text;
    	
    	SearchListener(final DemoService service, final JTextField text) {
    		this.service = service;
    		this.text = text;
    	}
    	
		@Override
		public void actionPerformed(ActionEvent e) {
			LOGGER.info("action called: {}", e);
			String cmd = e.getActionCommand();
			LOGGER.info("command: {}, {}", cmd, cmd.equals("search"));
			if (cmd.equals("search")) {
				LOGGER.info("calling for my object: {}", this.text.getText());
				SimpleDemoObject obj = this.service.get(this.text.getText());
				LOGGER.info("Loaded object: {}", obj);
			}
		}
    	
    }
    
//  public static class SearchField implements ActionListener {
//	
//	private final JTextField textField = new JTextField(16);
//	private final JButton searchButton = new JButton("search");
//	private final Consumer<String> searchConsumer;
//	
//	public SearchField(final Consumer<String> searchConsumer) {
//		this.searchConsumer = searchConsumer;
//	}
//	
//
//	@Override
//	public void actionPerformed(ActionEvent e) {
//		String cmd = e.getActionCommand();
//		if (cmd.equals("search")) {
//			LOGGER.debug("loading data for key: {}", textField.getText());
//			searchConsumer.accept(textField.getText());
//			
//		}
//	}
//	
//}
    
//  private class TestButtonAction implements ActionListener {
//  @Override
//  public void actionPerformed(ActionEvent e) {
//  System.out.println("Button Pressed");
//  }
//}

    
    
    
//    private static void launchGui() {
//    	JFrame frame = new JFrame("Swing Demo");
//    	frame.setSize(500, 600);
//    	frame.setLayout(null);
//    	frame.setVisible(true);
//    	
//    	JPanel searchPanel = new JPanel();
//    	JTextField searchText = new JTextField(16);
//    	JButton searchButton = new JButton("search");
//    	
//    	searchPanel.add(searchText);
//    	searchPanel.add(searchButton);
//    	
//    	
//    	JPanel dataPanel = new JPanel();
//    	JTextArea dataText = new JTextArea(100, 100);
//    	dataPanel.add(dataText);
//    	
//    	JSplitPane splitPane = new JSplitPane(SwingConstants.HORIZONTAL, searchPanel, dataPanel);
//    	
//    	frame.add(splitPane);
//    	
//    	
//    	
//
////    	
////    	JPanel mainPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
////    	
////    	JPanel searchPanel = new JPanel();
////    	searchPanel.setPreferredSize(new Dimension(100, 100));
////    	searchPanel.add(new JTextField(16));
////    	searchPanel.add(new JButton("search"));
////    	mainPanel.add(searchPanel);
////    	
////    	
////    	JPanel dataPanel = new JPanel();
////    	dataPanel.setPreferredSize(new Dimension(100, 100));
////    	dataPanel.add(new JLabel("ttt"));
////    	mainPanel.add(dataPanel);
////    	
////    	frame.add(mainPanel);
//    	
//    	
////    	JButton button = new JButton("button");
////    	button.setBounds(150, 200, 220, 50); // x axis, y axis, width, heigh
////    	
////    	frame.add(button);
//    	
////    	frame.add(new JSeparator());
//    	
////    	
////    	
////    	frame.setSize(500, 600);
////    	frame.setLayout(null);
////    	frame.setVisible(true);
//    }

    
//    private static JPanel searchPanel(final SearchField sf) {
//    	JPanel panel = new JPanel();
//    	panel.add(sf.textField);
//    	panel.add(sf.searchButton);
//    }
//    

    
}
