package com.classassist.fastdesigns;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.ColorUIResource;

import com.classassist.fastdesigns.gui.ChangeLook;
import com.classassist.fastdesigns.gui.StartScreen;

/**
 * Driver.java
 * 
 * The driver for the Class Assist attendance tracking software.
 * @author Fast Designs
 * @version 1.0
 */
public class Driver
{
	/**
	 * Initiates the program by invoking the StartScreen.
	 * @param args No arguments
	 */
	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(new Runnable()
		{	
			@Override
			public void run()
			{
				UIManager.put("ComboBox.background", new ColorUIResource(Color.white));
				UIManager.put("ComboBox.foreground", new ColorUIResource(Color.black));
				new ChangeLook().programLook();
				JFrame main = new JFrame("Class Assist");
				StartScreen start = new StartScreen(main);
				start.createWindow();
			}
		});
		
	}
}
