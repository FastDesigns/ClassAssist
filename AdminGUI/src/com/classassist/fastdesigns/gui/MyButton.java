package com.classassist.fastdesigns.gui;

import java.awt.Color;

import javax.swing.JButton;

/**
 * Changes look and feel of buttons
 * @author djust
 * @version 1.0
 */
public class MyButton extends JButton
{

	/**
	 * Version 1
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Creates the button with its initial values.
	 * @param text The button text
	 */
	public MyButton(String text)
	{
		super(text);
		this.setContentAreaFilled(false);
		this.setOpaque(true);
		this.setBackground(new Color(47, 196, 205));
		this.setForeground(Color.white);
	}
}
