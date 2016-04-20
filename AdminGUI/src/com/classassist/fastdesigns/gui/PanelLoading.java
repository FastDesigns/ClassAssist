package com.classassist.fastdesigns.gui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Loading screen
 * @author djust
 *
 */
public class PanelLoading extends JPanel
{

	/**
	 * PanelLoading version 1
	 */
	private static final long serialVersionUID = 1L;
	
	public PanelLoading()
	{
		super();
		setBackground(Color.darkGray);
		this.setLayout(new BorderLayout());
		Icon icon = new ImageIcon(this.getClass().getResource("/res/loading.gif"));
		JLabel gif = new JLabel(icon);
		this.add(gif, BorderLayout.CENTER);
	}
}