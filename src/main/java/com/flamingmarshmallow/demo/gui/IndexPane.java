package com.flamingmarshmallow.demo.gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.flamingmarshmallow.demo.gui.ObjectList.PageNav;
import com.flamingmarshmallow.demo.service.KeyValueDataService.Data;
import com.flamingmarshmallow.demo.service.Widget;

@SuppressWarnings("serial")
public class IndexPane extends JPanel {
	
	private static final Logger LOGGER = LogManager.getLogger(IndexPane.class);
	
	public IndexPane(final JList<Data<Long, Widget>> objectList) {
		setBackground(Color.WHITE);
		
		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		
		objectList.setBackground(Color.CYAN);
		objectList.setForeground(Color.BLACK);
		add(new JScrollPane(objectList), c);

		c.gridx = 0;
		c.gridy = 1;
		
		NavButtons buttons = new NavButtons(objectList);
		add(buttons, c);
		
		((ObjectList) objectList).initializePages(p -> buttons.updateButtons(p));
	}
	
	public static class NavButtons extends JPanel {
		
		private JButton prevButton;
		private JButton nextButton;
		
		public NavButtons(final JList<Data<Long, Widget>> objectList) {
			prevButton = new JButton(NavAction.PREV.getText());
			prevButton.addActionListener(new NavListener(a -> ((ObjectList) objectList).changePage(a, p -> this.updateButtons(p))));
			
			nextButton = new JButton(NavAction.NEXT.getText());
			nextButton.addActionListener(new NavListener(a -> ((ObjectList) objectList).changePage(a, p -> this.updateButtons(p))));

			add(prevButton);
			add(nextButton);
		}

		private void updateButtons(final Set<ObjectList.PageNav> page) {
			LOGGER.info("change visibility to {}", page);
			this.nextButton.setEnabled(page.contains(PageNav.HAS_NEXT));
			this.prevButton.setEnabled(page.contains(PageNav.HAS_PREV));
		}
		
	}
	
}
