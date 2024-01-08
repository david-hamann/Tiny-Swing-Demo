package com.flamingmarshmallow.demo.gui;

import java.util.Optional;

public enum NavAction {
	NEXT(">"),
	PREV("<");
	
	final String text;
	NavAction(final String text) {
		this.text = text;
	}
	
	public static Optional<NavAction> fromString(final String text) {
		for (NavAction nb : NavAction.values()) {
			if (nb.text.equals(text)) {
				return Optional.of(nb);
			}
		}
		return Optional.empty();
	}
	
	public String getText() {
		return this.text;
	}
	
	public boolean is(final String text) {
		return this.text.equals(text);
	}
	
}