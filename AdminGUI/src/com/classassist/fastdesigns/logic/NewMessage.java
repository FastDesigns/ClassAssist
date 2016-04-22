package com.classassist.fastdesigns.logic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Creates a message dialog that appears for a few seconds then is disposed
 * @author djust
 *
 */
public class NewMessage extends JPanel
{
	/**
	 * Version 1
	 */
	private static final long serialVersionUID = 1L;
	private JFrame m = new JFrame();
	private String mess;
	private int alpha = 255;
	private Timer fade;
	private Color color = new Color(255, 255, 255, alpha);
	private Color outline = new Color(0, 0, 0, alpha);

	public NewMessage(String msg)
	{
		this.mess = msg;
		this.setBackground(new Color(0, 0, 0, 0));
		create();
		center();
		startTimer();
	}
	
	private void center()
	{
		m.setLocation(FrameSize.getX() + (FrameSize.getWidth() / 2) - (m.getWidth() / 2), FrameSize.getY() + (FrameSize.getHeight() / 2) - (m.getHeight() / 2));
	}
	
	@Override
	protected void paintComponent(Graphics g)
	{
		Graphics2D g2d = (Graphics2D)g;
		g.setColor(color); //background color
		
		g.fillRect(0, 0, 300, 100); //300 and 100 is the width and height of frame
		
		g2d.setColor(outline); //text color
		
		FontMetrics fm = g2d.getFontMetrics();
		Rectangle2D r = fm.getStringBounds(mess, g2d);
		int x = (m.getWidth() - (int)r.getWidth()) / 2;
		int y = (m.getHeight() - (int)r.getHeight()) / 2 + fm.getAscent(); //getting center positions for drawing text
		g.drawString(mess, x, y);
		
		//border
		g.drawRect(0, 0, 299, 99); //must subtract 1 from width and height to be able to see right and bottom sides
		
		g.dispose();
	}
	
	private void create()
	{
		this.setOpaque(true);
		this.setPreferredSize(new Dimension(300, 100));
		m.add(this);
		m.setUndecorated(true); //don't want to see frame
		m.setBackground(new Color(0, 0, 0, 0)); //transparent background
		m.pack();
		m.setVisible(true);
	}
	
	private void startTimer()
	{
		Timer timer = new Timer();
		timer.schedule(new TimerTask()
		{
			@Override
			public void run()
			{
				fadeFrame();
			}
		}, 2000);
	}
	
	private void fadeFrame()
	{
		fade = new Timer();
		fade.scheduleAtFixedRate(new TimerTask()
		{
			@Override
			public void run()
			{
				if(alpha == 1)
				{
					closeFrame();
				}
				alpha = alpha - 1;
				fade();
			}
		}, 20, 3);
	}
	
	private void fade()
	{
		color = new Color(255, 255, 255, alpha);
		outline = new Color(0, 0, 0, alpha);
		m.repaint();
	}
	
	private void closeFrame()
	{
		m.dispose();
		fade.cancel();
		fade.purge();
	}
}