package com.classassist.fastdesigns.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class MyScrollBarUI extends BasicScrollBarUI
{
	protected int scrollBarWidth = 500;
	private Image increase;
	private Image decrease;
	
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
	protected void paintThumb(Graphics g, JComponent c, Rectangle r)
	{
		g.setColor(new Color(47, 196, 205));
		g.fillRect(r.x, r.y, r.width, r.height);
	}
	
	@Override
	protected void paintTrack(Graphics g, JComponent c, Rectangle r)
	{
		g.setColor(Color.darkGray);
		g.fillRect(r.x, r.y, r.width, r.height);
	}
	
	@Override
	protected JButton createDecreaseButton(int orientation)
	{
		JButton decreaseButton = new JButton(new ImageIcon(increase))
		{
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
	protected JButton createIncreaseButton(int orientation)
	{
		JButton decreaseButton = new JButton(new ImageIcon(decrease))
		{
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