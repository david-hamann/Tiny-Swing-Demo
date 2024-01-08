package com.flamingmarshmallow.demo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
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
	
	private final InOutService<String, SimpleDemoObject> service;

	public AppGui(final InOutService<String, SimpleDemoObject> service, final String title) {
		this.service = service;
		EventQueue.invokeLater(() -> {
			try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            	LOGGER.error("Couldn't set look and feel", ex);
            }

            @SuppressWarnings("unused")
			JFrame frame = new AppFrame(title);
		});
	}
	
	public class AppFrame extends JFrame {
		
		public AppFrame(final String title) {
			setTitle(title);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setLayout(new BorderLayout());
			add(new AppPane());
			pack();
			setLocationRelativeTo(null);
			setVisible(true);
		}
		
	}
	
	public class AppPane extends JPanel {

		private final JSplitPane pane;
		
		public AppPane() {
			
			DataPane dataPane = new DataPane(true);

			pane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
			pane.setTopComponent(new SearchPane((k, v) -> dataPane.updateData(k, v)));
			pane.setBottomComponent(dataPane);
			pane.setEnabled(false);
			setLayout(new BorderLayout());
			add(pane);
		}
		
	}
	
	public class DataPane extends JPanel {
		
		private String currentKey;
		private SimpleDemoObject currentObj;

		private JTextField nameText;
		private JTextArea descriptionText;
		private JTextField attributesText;
		private JTextField keyText;
		private JLabel updatedLabel;
		private JLabel createdLabel;
		
		
		public DataPane(final boolean b) {
			setBackground(Color.WHITE);
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			
			//formPanel
			JPanel formPanel = new JPanel();
			formPanel.setLayout(new GridBagLayout());
			formPanel.setBorder(BorderFactory.createEmptyBorder(25,25,25,25));
			
			JLabel keyLabel = new FormLabel("Part ID");
			formPanel.add(keyLabel, getGBConstraint(0,0));
			keyText = new JTextField(10);
			keyText.setEnabled(false);
			keyText.setForeground(Color.RED);
			keyText.setBackground(Color.LIGHT_GRAY);
			keyLabel.setLabelFor(keyText);
			formPanel.add(keyText, getGBConstraint(1,0));

			JLabel nameLabel = new FormLabel("Name");
			formPanel.add(nameLabel, getGBConstraint(0,1));
			
			nameText = new JTextField(10);
			nameLabel.setLabelFor(nameText);
			formPanel.add(nameText, getGBConstraint(1,1));
			
			JLabel descriptionLabel = new FormLabel("Description");
			formPanel.add(descriptionLabel, getGBConstraint(0,2));
			
			descriptionText = new JTextArea();
			descriptionText.setRows(10);
			descriptionLabel.setLabelFor(descriptionText);
			formPanel.add(descriptionText, getGBConstraint(1,2));

			JLabel attributesLabel = new FormLabel("Attributes");
			formPanel.add(attributesLabel, getGBConstraint(0,3));
			attributesText = new JTextField(10);
			attributesLabel.setLabelFor(attributesText);
			formPanel.add(attributesText, getGBConstraint(1,3));
			
			add(formPanel);
			
			//metadataPanel
			JPanel datePanel = new JPanel();
			datePanel.setBackground(Color.WHITE);
			
			updatedLabel = new JLabel("");
			updatedLabel.setForeground(Color.BLUE);
			updatedLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			datePanel.add(updatedLabel);

			createdLabel = new JLabel("");
			createdLabel.setForeground(Color.BLUE);
			createdLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			datePanel.add(createdLabel);
			
			add(datePanel);
			
			//buttonPane
			//save changes opens a modal, asking for an id

			JPanel buttonPanel = new JPanel();
			buttonPanel.setLayout(new FlowLayout());
			


			//clears the form, so we can create a new object
			JButton clearButton = new JButton("clear");
			buttonPanel.add(clearButton);
			
			JButton undoButton = new JButton("undo");
			buttonPanel.add(undoButton);
			JButton saveButton = new JButton("save");
			saveButton.addActionListener(new SaveListener(() -> this.saveChanges(service)));
			buttonPanel.add(saveButton);
			
			add(buttonPanel);
			
			
		}
		
		private GridBagConstraints getGBConstraint(final int x, final int y) {
			GridBagConstraints constraints = new GridBagConstraints();
			constraints.fill = GridBagConstraints.HORIZONTAL;
			constraints.gridx = x;
			constraints.gridy = y;
			return constraints;
		}
		
//		public DataPane() {
//			
//			setBackground(Color.WHITE);
//			SpringLayout layout = new SpringLayout();
//			setLayout(layout);
//			
//			JLabel keyLabel = new FormLabel("Part ID");
//			keyLabel.setAlignmentY(Component.TOP_ALIGNMENT);
//			add(keyLabel);
//			keyText = new JTextField(10);
//			keyText.setEnabled(false);
//			keyText.setForeground(Color.RED);
//			keyText.setBackground(Color.LIGHT_GRAY);
//			keyLabel.setLabelFor(keyText);
//			add(keyText);
//			
//			
//			JLabel nameLabel = new FormLabel("Name");
//			add(nameLabel);
//			
//			nameText = new JTextField(10);
//			nameLabel.setLabelFor(nameText);
//			add(nameText);
//			
//			JLabel descriptionLabel = new FormLabel("Description");
//			add(descriptionLabel);
//			
//			descriptionText = new JTextArea();
//			descriptionText.setRows(10);
//			descriptionLabel.setLabelFor(descriptionText);
//			add(descriptionText);
//
//			JLabel attributesLabel = new FormLabel("Attributes");
//			add(attributesLabel);
//			attributesText = new JTextField(10);
//			attributesLabel.setLabelFor(attributesText);
//			add(attributesText);
//			
//			
////			SpringUtilities.makeCompactGrid(this, 4, 2, 6, 6, 6, 6);
//			setOpaque(true);
//			
////			
////			dateLabel = new JLabel("");
////			dateLabel.setForeground(Color.BLUE);
////			add(dateLabel);
//		}
		
		@Override
		public Dimension getPreferredSize() {
			return new Dimension(500,1000);
		}
		
		public void updateData(final String key, final SimpleDemoObject obj) {
			this.currentKey = key;
			this.currentObj = obj;

//			this.keyText.setText("<html><b>" + key + "</b></html>");
			this.keyText.setText(key);
			this.nameText.setText(obj.name);
			this.descriptionText.setText(obj.description);
			this.attributesText.setText(String.join(",", obj.attributes));
			this.updatedLabel.setText("updated: " + prettyDate(obj.updated));
			this.createdLabel.setText("created: " + prettyDate(obj.created));
	
		}
		
		public SimpleDemoObject saveChanges(final InOutService<String, SimpleDemoObject> service) {
			//TODO disable save if nothing is loaded and no changes have been made
			final long updateDate = Instant.now().toEpochMilli();
			final String attributes = this.attributesText.getText();
			final List<String> attributeList = List.of(attributes.split(","));
			SimpleDemoObject updated = new SimpleDemoObject(
					this.nameText.getText(),
					this.descriptionText.getText(),
					attributeList,
					this.currentObj.created,
					updateDate);
			if (updated.equals(this.currentObj)) {
				LOGGER.info("objects haven't changed, aborting save");
				return null;
			}
			service.save(this.currentKey, updated);
			this.updatedLabel.setText("updated: " + prettyDate(updateDate));
			return updated;
		}

		//TODO undoChanges -> repaint the form with the current object
		
		//TODO clearForm the form to add new objects
		
	}
	
	private static String prettyDate(final long timestamp) {
		return ZonedDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault())
				.format(DateTimeFormatter.ISO_DATE_TIME);
	}
	
	public class FormLabel extends JLabel {
		public FormLabel(final String text) {
			super(text);
			setForeground(Color.BLACK);
			setAlignmentY(Component.TOP_ALIGNMENT);
		}
	}
	
	public class SearchPane extends JPanel {
		
		public SearchPane(final BiConsumer<String, SimpleDemoObject> searchSuccessUpdate) {
			
//			SpringLayout layout = new SpringLayout();
//			setLayout(layout);
			
			JTextField searchText = new JTextField(16);
        	JButton searchButton = new JButton("search");

//        	layout.putConstraint(SpringLayout.EAST, searchButton, 7, SpringLayout.EAST, this);
//        	layout.putConstraint(SpringLayout.EAST, searchText, 5, SpringLayout.WEST, searchButton);
        	
        	searchButton.addActionListener(new SearchListener(service, searchText, searchSuccessUpdate));
        	add(searchText);
        	add(searchButton);
        	setBackground(Color.WHITE);
		}
		
//		@Override
//		public Dimension getPreferredSize() {
//			return new Dimension(300,200);
//		}
		
	}

	
	public class SaveListener implements ActionListener {
		
		private final Supplier<SimpleDemoObject> saveFunction;
		
		SaveListener(final Supplier<SimpleDemoObject> saveFunction) {
			this.saveFunction = saveFunction;
		}
		

		@Override
		public void actionPerformed(ActionEvent e) {
			LOGGER.info("Save Called");
			saveFunction.get();			
		}
		
	}
	
	public class SearchListener implements ActionListener {
		private final InOutService<String, SimpleDemoObject>  service;
		
    	private final JTextField text;
    	private final BiConsumer<String, SimpleDemoObject> searchSuccessUpdate;
    	
    	SearchListener(final InOutService<String, SimpleDemoObject>  service, final JTextField text, final BiConsumer<String, SimpleDemoObject> updateFunction) {
    		this.service = service;
    		this.text = text;
    		this.searchSuccessUpdate = updateFunction;
    	}
    	
		@Override
		public void actionPerformed(ActionEvent e) {
			LOGGER.info("action called: {}", e);
			String cmd = e.getActionCommand();
			LOGGER.info("command: {}, {}", cmd, cmd.equals("search"));
			if (cmd.equals("search")) {
				final String key = this.text.getText();
				LOGGER.info("calling for my object: {}", key);
				
				Optional.ofNullable(this.service.get(key)).ifPresentOrElse(
					obj -> {
						LOGGER.info("Loaded object: {}", obj);
						this.searchSuccessUpdate.accept(key, obj);					
					},
					() -> {
						LOGGER.info("no object found with key: \"{}\"", key);
					});

			}
		}
    	
    }
	
}
