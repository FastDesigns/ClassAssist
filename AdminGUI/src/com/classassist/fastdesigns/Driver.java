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

public class Driver
{
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