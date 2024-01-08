package com.flamingmarshmallow.demo.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;

import com.flamingmarshmallow.demo.service.InOutService;
import com.flamingmarshmallow.demo.service.SimpleDemoObject;
import com.flamingmarshmallow.demo.utils.DateUtils;

@SuppressWarnings("serial")
public class DataPane extends JPanel {
	
	private static final Logger LOGGER = LogManager.getLogger(DataPane.class);
	
	private Long currentKey = Long.valueOf(0);
	private SimpleDemoObject currentObj = SimpleDemoObject.EMPTY;

	private JTextField nameText;
	private JTextArea descriptionText;
	private JTextField attributesText;
	private FormLabel keyText;
	private JLabel updatedLabel;
	private JLabel createdLabel;
	
	
	public DataPane(final InOutService<Long, SimpleDemoObject> service) {
		setBackground(Color.WHITE);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		//formPanel
		JPanel formPanel = new JPanel();
		formPanel.setBackground(Color.WHITE);
		formPanel.setLayout(new GridBagLayout());
//		formPanel.setBorder(BorderFactory.createEmptyBorder(25,25,25,25));
		
		JLabel keyLabel = new FormLabel("Part ID");
		formPanel.add(keyLabel, getGBConstraint(0,0));
		keyText = new FormLabel("");
		keyText.setHorizontalAlignment(SwingConstants.LEFT);
		formPanel.add(keyText, getGBConstraint(1,0));

		JLabel nameLabel = new FormLabel("Name");
		formPanel.add(nameLabel, getGBConstraint(0,1));
		
		nameText = new TextField(10);
		nameLabel.setLabelFor(nameText);
		formPanel.add(nameText, getGBConstraint(1,1));
		
		JLabel descriptionLabel = new FormLabel("Description");
		formPanel.add(descriptionLabel, getGBConstraint(0,2));
		
		descriptionText = new JTextArea();
		descriptionText.setRows(10);
		descriptionText.setBackground(Color.WHITE);
		descriptionText.setForeground(Color.BLACK);
		descriptionLabel.setLabelFor(descriptionText);
		
		JScrollPane descriptionPane = new JScrollPane(descriptionText);
		formPanel.add(descriptionPane, getGBConstraint(1,2));
		JLabel attributesLabel = new FormLabel("Attributes");
		formPanel.add(attributesLabel, getGBConstraint(0,3));
		attributesText = new TextField(10);
		attributesLabel.setLabelFor(attributesText);
		formPanel.add(attributesText, getGBConstraint(1,3));
		
		add(formPanel);
		
		//metadataPanel
		JPanel datePanel = new JPanel();
		datePanel.setLayout(new GridBagLayout());
		datePanel.setBackground(Color.WHITE);
		
		updatedLabel = new JLabel("");
		updatedLabel.setForeground(Color.BLUE);
		updatedLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		datePanel.add(updatedLabel, getGBConstraint(0,0));

		createdLabel = new JLabel("");
		createdLabel.setForeground(Color.BLUE);
		createdLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		datePanel.add(createdLabel, getGBConstraint(0,1));
		
		add(datePanel);
		
		
		//buttonPane
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		buttonPanel.setBackground(Color.CYAN);

		//clears the form, so we can create a new object
		JButton clearButton = new JButton("clear");
		clearButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clearForm();
			}
		});
		clearButton.setToolTipText("clear all fields");
		buttonPanel.add(clearButton);
		
		JButton resetButton = new JButton("reset");
		resetButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				resetForm();
			}
		});
		resetButton.setToolTipText("undo any changes made");
		buttonPanel.add(resetButton);
		
		JButton saveButton = new JButton("save");
		saveButton.addActionListener(new SaveListener(() -> this.saveChanges(service)));
		saveButton.setToolTipText("save new or changed object");
		buttonPanel.add(saveButton);
		
		add(buttonPanel);
		
		this.updateData(0l, SimpleDemoObject.EMPTY);
	}
	
	private GridBagConstraints getGBConstraint(final int x, final int y) {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = x;
		constraints.gridy = y;
		constraints.insets = new Insets(3,3,3,3);
		return constraints;
	}
	

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(500, 500);
	}
	
	public void updateData(final Long key, final SimpleDemoObject obj) {
		this.currentKey = key;
		this.currentObj = obj;

		this.keyText.setText(Long.toString(key));
		this.nameText.setText(obj.name);
		this.descriptionText.setText(obj.description);
		this.attributesText.setText(String.join(",", obj.attributes));
		this.updatedLabel.setText("updated: " + DateUtils.prettyDate(obj.updated));
		this.createdLabel.setText("created: " + DateUtils.prettyDate(obj.created));

	}
	
	/**
	 * Save changes to the form.  Checks that there are changes made before calling the save methods.
	 * @param service
	 * @return
	 */
	private final String saveChanges(final InOutService<Long, SimpleDemoObject> service) {
		final long updateDate = Instant.now().toEpochMilli();
		final String attributes = this.attributesText.getText();

		final List<String> attributeList = Arrays.asList(attributes.split(",")).stream()
			.map(String::trim)
			.filter(Strings::isNotBlank)
			.collect(Collectors.toList());
		
		SimpleDemoObject updated = new SimpleDemoObject(
				this.nameText.getText(),
				this.descriptionText.getText(),
				attributeList,
				this.currentObj.created,
				updateDate);
		if (updated.equals(SimpleDemoObject.EMPTY)) {
			return "object is empty";
		}
		if (updated.equals(this.currentObj)) {
			return "objects haven't changed";
		}
		if (this.currentObj.equals(SimpleDemoObject.EMPTY)) {
			final Long newId = service.save(updated);
			LOGGER.info("Saved to new ID: {}", newId);
			updateData(newId, updated);
			return null;
		}
		service.save(this.currentKey, updated);
		this.updatedLabel.setText("updated: " + DateUtils.prettyDate(updateDate));
		return null;
	}

	/**
	 * Resets any changes made the current object.
	 */
	private final void resetForm() {
		updateData(this.currentKey, this.currentObj);
	}
	
	/**
	 * Clears the form the form to add new objects
	 */
	private final void clearForm() {
		this.currentKey = -1l;
		this.currentObj = SimpleDemoObject.EMPTY;
		updateData(0l, SimpleDemoObject.EMPTY);
	}
	
}