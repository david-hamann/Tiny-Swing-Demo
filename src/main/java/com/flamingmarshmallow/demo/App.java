package com.flamingmarshmallow.demo;


import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.flamingmarshmallow.demo.gui.AppGui;
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
  	
    	InOutService<Long, SimpleDemoObject> service = DemoService.getBuilder().withDemoData("data.jsonl").build();


    	try {
    		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    	} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
    	}
    	
    	LOGGER.info("starting...");
    	
		JFrame frame = new AppGui(service, "demo app");
    	
    }
    
}

