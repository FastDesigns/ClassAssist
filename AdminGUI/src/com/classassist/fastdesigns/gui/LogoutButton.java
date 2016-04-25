package com.classassist.fastdesigns.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.classassist.fastdesigns.gui.StartScreen;

/**
 * The button that logs the user out of the system.
 * @author Fast Designs
 * @version 1.0
 */
public class LogoutButton extends JButton implements ActionListener
{

	/**
	 * HomeButton Version 1
	 */
	private static final long serialVersionUID = 1L;
	//private JFrame main;
	
	/**
	 * Sets up the button with text and an action listener.
	 * @param text The button text
	 */
	public LogoutButton(String text)
	{
		super(text);
		this.addActionListener(this);
		//this.main = chases;
	}

	@Override
	/**
	 * ActionListener that returns the program to the StartScreen.
	 */
	public void actionPerformed(ActionEvent e)
	{
		JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
		frame.getContentPane().removeAll();
		frame.dispose();
		@SuppressWarnings("unused")
		//desktopGUI frames = desktopGUI.getFrame();
		//frames.setVisible(true);
		StartScreen ss = new StartScreen(frame);
	}
}
