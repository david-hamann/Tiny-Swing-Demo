package com.flamingmarshmallow.demo;


import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.flamingmarshmallow.demo.service.DemoService;
import com.flamingmarshmallow.demo.service.InOutService;
import com.flamingmarshmallow.demo.service.SimpleDemoObject;

/**
 * A simple Swing Demo.
 */
public class App {
	
	private static Logger LOGGER = LogManager.getLogger(App.class);
	
    @SuppressWarnings("unused")
	public static void main( String[] args ) {
    	
    	//TODO read args
  	
    	InOutService<String, SimpleDemoObject> service = DemoService.getBuilder().withDemoData("data.jsonp").build();


    	try {
    		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    	} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
    	}
    	
    	LOGGER.info("starting...");
    	
//		@SuppressWarnings("unused")
//		AppFrame frame = new AppFrame(service, 1500, 1000, "Swing Demo");
    	
    	JFrame frame = new AppGui(service, "demo app");
    	
    }
    
}


//    
//    @SuppressWarnings("serial")
//	public static class AppFrame extends JFrame {
//    	public AppFrame(final InOutService<String, SimpleDemoObject>  service, final int width, final int height, final String title) {
//    		setSize(width, height);
//    		setTitle(title);
//    		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//    		setLayout(new BorderLayout());
////    		setLocation(400, 0);
//    		setSize(new Dimension(1500,1000));
//    		setVisible(true);
//    		
//    		AppPanel panel = new AppPanel(service);
//    		getContentPane().add(panel);
//    		
//    		pack(); //without this, we have to resize the window to get the panels to appear???
////            setLocationRelativeTo(null);
////            setVisible(true);
//    	}
//    }
//    
//    @SuppressWarnings("serial")
//	public static class AppPanel extends JPanel {
//    	
//        public AppPanel(final InOutService<String, SimpleDemoObject>  service) {
//        	
//        	JSplitPane splitPane = new JSplitPane();
//        	
//        	splitPane.setOrientation(SwingConstants.HORIZONTAL);
//
//        	final DetailPanel detailPanel = new DetailPanel();
//        	
//        	splitPane.setTopComponent(new SearchPanel(service, (k, v) -> detailPanel.updateData(k, v)));
//        	splitPane.setBottomComponent(detailPanel);
//
////        	JSplitPane splitPane = new JSplitPane(SwingConstants.HORIZONTAL, searchPanel, dataPanel);
//        	setLayout(new BorderLayout());
//        }
//        
//    }
//    
//    @SuppressWarnings("serial")
//	public static class SearchPanel extends JPanel {
//    	
//    	public SearchPanel(final InOutService<String, SimpleDemoObject> service, final BiConsumer<String, SimpleDemoObject> searchSuccessUpdate) {
//    		JTextField searchText = new JTextField(16);
//        	JButton searchButton = new JButton("search");
//        	searchButton.addActionListener(new SearchListener(service, searchText, searchSuccessUpdate));
//        	this.add(searchText);
//        	this.add(searchButton);
//    	}
//    	
//    }
//
//	@SuppressWarnings("serial")
//	public static class DetailPanel extends JPanel {
//
//    	@SuppressWarnings("unused")
//		private String key;
//    	@SuppressWarnings("unused")
//		private SimpleDemoObject obj;
//    	
//    	private JTextField nameText;
//		
//		public DetailPanel() {
//			SpringLayout layout = new SpringLayout();
//			setLayout(layout);
//			
//			//TODO put the key visible also
//			JLabel nameLabel = new JLabel("name");
//			layout.putConstraint(SpringLayout.NORTH, nameLabel, 7, SpringLayout.NORTH, this);
//			layout.putConstraint(SpringLayout.WEST, nameLabel, 88, SpringLayout.WEST,  this);
//
//			nameText = new JTextField();
//			layout.putConstraint(SpringLayout.NORTH, nameText, 7, SpringLayout.NORTH, this);
//			layout.putConstraint(SpringLayout.WEST, nameText, 5, SpringLayout.EAST, nameLabel);
//			layout.putConstraint(SpringLayout.EAST, nameText, 200, SpringLayout.WEST, nameLabel);
//
//			add(nameText);
////			nameText.setColumns(10);
//
//		}
//        
//        public void updateData(final String key, final SimpleDemoObject obj) {
//        	this.key = key;
//        	this.obj = obj;
//        	
//        	this.nameText.setText(obj.name);
//        }
//		
//	}
//
//    
//    public static class SearchListener implements ActionListener {
//
//    	private final InOutService<String, SimpleDemoObject>  service;
//    	private final JTextField text;
//    	private final BiConsumer<String, SimpleDemoObject> searchSuccessUpdate;
//    	
//    	SearchListener(final InOutService<String, SimpleDemoObject>  service, final JTextField text, final BiConsumer<String, SimpleDemoObject> updateFunction) {
//    		this.service = service;
//    		this.text = text;
//    		this.searchSuccessUpdate = updateFunction;
//    	}
//    	
//		@Override
//		public void actionPerformed(ActionEvent e) {
//			LOGGER.info("action called: {}", e);
//			String cmd = e.getActionCommand();
//			LOGGER.info("command: {}, {}", cmd, cmd.equals("search"));
//			if (cmd.equals("search")) {
//				final String key = this.text.getText();
//				LOGGER.info("calling for my object: {}", key);
//				SimpleDemoObject obj = this.service.get(key);
//				LOGGER.info("Loaded object: {}", obj);
//				this.searchSuccessUpdate.accept(key, obj);
//			}
//		}
//    	
//    }
//    
//}
//    
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

