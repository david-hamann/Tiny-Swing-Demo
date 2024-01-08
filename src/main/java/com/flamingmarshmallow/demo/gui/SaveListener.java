package com.flamingmarshmallow.demo.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;
import java.util.function.Supplier;

import javax.swing.JOptionPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SaveListener implements ActionListener {
	private static final Logger LOGGER = LogManager.getLogger(SaveListener.class);
	
	private final Supplier<String> saveFunction;
	
	SaveListener(final Supplier<String> saveFunction) {
		this.saveFunction = saveFunction;
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		
		final int response = JOptionPane.showConfirmDialog(null, "Save?", "Confirm Save", JOptionPane.YES_NO_OPTION);
		if (response == JOptionPane.NO_OPTION) {
			return;
		}
		
		LOGGER.info("Save Called");
		Optional.ofNullable(saveFunction.get())
			.ifPresentOrElse(s -> {
				JOptionPane.showMessageDialog(null, "No Save Performed: " + s);
			}, () -> {});
	}
	
}