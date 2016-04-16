package com.classassist.fastdesigns;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.ColorUIResource;

import com.classassist.fastdesigns.gui.StartScreen;

public class Driver
{
	public static void main(String[] args)
	{
		UIManager.put("ComboBox.background", new ColorUIResource(Color.white));
		UIManager.put("ComboBox.foreground", new ColorUIResource(Color.black));
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		JFrame main = new JFrame();
		StartScreen start = new StartScreen(main);
		
	}
}