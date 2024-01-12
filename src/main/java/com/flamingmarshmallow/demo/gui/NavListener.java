package com.flamingmarshmallow.demo.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;

public class NavListener implements ActionListener {
	
	private final Consumer<NavAction> consumer;
	public NavListener(final Consumer<NavAction> consumer) {
		this.consumer = consumer;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		NavAction.fromString(e.getActionCommand())
				 .ifPresent(c -> this.consumer.accept(c));
	}

}
