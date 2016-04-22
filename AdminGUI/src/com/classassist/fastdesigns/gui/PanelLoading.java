package com.classassist.fastdesigns.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

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
	
	private JLabel gif;
	
	public PanelLoading()
	{
		super();
		setBackground(Color.darkGray);
		this.setLayout(new BorderLayout());
		final Icon icon = new ImageIcon(this.getClass().getResource("/res/loading2.gif"));
		gif = new JLabel(icon);
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		panel.setBackground(Color.darkGray);
		final JLabel loading = new JLabel("Loading");
		loading.setFont(new Font(loading.getFont().getName(), Font.PLAIN, 72));
		loading.setForeground(new Color(47, 196, 205));
		panel.add(loading);
		gif.setBorder(new EmptyBorder((int)loading.getPreferredSize().getHeight() - 45, 0, 0, 0));
		panel.add(gif);
		this.add(panel, BorderLayout.CENTER);
		gif.setSize(new Dimension(icon.getIconWidth(), (int)loading.getPreferredSize().getHeight()));
	}
}