package com.flamingmarshmallow.demo.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Optional;
import java.util.function.BiConsumer;

import javax.swing.JTextField;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.flamingmarshmallow.demo.service.InOutService;
import com.flamingmarshmallow.demo.service.Widget;

public class SearchListener implements ActionListener {
	private static final Logger LOGGER = LogManager.getLogger(SearchListener.class);
	
	private final InOutService<Long, Widget>  service;
	
	private final JTextField text;
	private final BiConsumer<Long, Widget> searchSuccessUpdate;
	
	SearchListener(final InOutService<Long, Widget>  service, final JTextField text, final BiConsumer<Long, Widget> updateFunction) {
		this.service = service;
		this.text = text;
		this.searchSuccessUpdate = updateFunction;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		LOGGER.info("action called: {}", e);
		String cmd = e.getActionCommand();
		LOGGER.info("command: {}, {}", cmd, cmd.equals("search"));
		if (e.getSource().equals(this.text) || cmd.equals("search")) {
			try {
				final Long key = Long.valueOf(this.text.getText());
				LOGGER.info("calling for my object: {}", key);
				
				Optional.ofNullable(this.service.get(key)).ifPresentOrElse(
				obj -> {
					LOGGER.info("Loaded object: {}", obj);
					this.searchSuccessUpdate.accept(key, obj);					
				},
				() -> {
					LOGGER.info("no object found with key: \"{}\"", key);
				});
			} catch (NumberFormatException ex) {
				LOGGER.error(ex);
				return;
			}

		}
	}
	
}