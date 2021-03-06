package com.classassist.fastdesigns.gui;

import java.awt.Color; 
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicScrollBarUI;

/**
 * A custom scroll bar for the GUI.
 * @author Fast Designs
 * @version 1.0
 */
public class MyScrollBarUI extends BasicScrollBarUI
{
	protected int scrollBarWidth = 500;
	private Image increase;
	private Image decrease;
	
	/**
	 * Initializes the scroll bar with images.
	 */
	public MyScrollBarUI()
	{
		try
		{
			increase = ImageIO.read(this.getClass().getResource("/res/increasebutton.png"));
			decrease = ImageIO.read(this.getClass().getResource("/res/decreasebutton.png"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	/**
	 * Sets the color of the thumb button of the scroll bar.
	 */
	protected void paintThumb(Graphics g, JComponent c, Rectangle r)
	{
		g.setColor(new Color(47, 196, 205));
		g.fillRect(r.x, r.y, r.width, r.height);
	}
	
	@Override
	/**
	 * Sets the color of the track of the scroll bar.
	 */
	protected void paintTrack(Graphics g, JComponent c, Rectangle r)
	{
		g.setColor(Color.darkGray);
		g.fillRect(r.x, r.y, r.width, r.height);
	}
	
	@Override
	/**
	 * Creates the decrease button of the scroll bar.
	 */
	protected JButton createDecreaseButton(int orientation)
	{
		JButton decreaseButton = new JButton(new ImageIcon(increase))
		{
			private static final long serialVersionUID = 1L;

			@Override
			public Dimension getPreferredSize()
			{
                return new Dimension(22, 22);
            }
		};
		decreaseButton.setBorderPainted(false);
		decreaseButton.setContentAreaFilled(false);
		decreaseButton.setFocusPainted(false);
		return decreaseButton;
	}
	
	@Override
	/**
	 * Creates the increase button of the scroll bar.
	 */
	protected JButton createIncreaseButton(int orientation)
	{
		JButton decreaseButton = new JButton(new ImageIcon(decrease))
		{
			private static final long serialVersionUID = 1L;

			@Override
			public Dimension getPreferredSize()
			{
                return new Dimension(22, 22);
            }
		};
		decreaseButton.setBorderPainted(false);
		decreaseButton.setContentAreaFilled(false);
		decreaseButton.setFocusPainted(false);
		return decreaseButton;
	}
}
