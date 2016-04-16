package com.classassist.fastdesigns.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import com.classassist.fastdesigns.gui.SelectClassScreen;

public class TeacherButton extends JButton implements MouseListener
{

	/**
	 * Teacher Button base
	 */
	private static final long serialVersionUID = 1L;
	private SelectClassScreen select;
	private Image img;
	private boolean hover = false;
	
	public TeacherButton(String picName, SelectClassScreen s)
	{
		select = s;
//		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		try
		{
			img = ImageIO.read(this.getClass().getResource("/res/" + picName));
			setIcon(new ImageIcon(img));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		setFocusPainted(false);
		Border b = new LineBorder(Color.white, 2);
		this.setBorder(b);
		this.setBorderPainted(false);
		this.setContentAreaFilled(false);
		this.setBackground(new Color(0, 0, 0, 0));
		this.addMouseListener(this);
	}
	
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g.create();
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		Color cyan = new Color(47, 196, 205, 25);
		g2.setColor(cyan);
		if(hover)
			g2.fillRect(1, 1, 500, 500);
		g2.setColor(Color.white);
	}
	
	@Override
	public Dimension getPreferredSize()
	{
		return new Dimension(100, 100);
	}
	
	@Override
	public void mouseClicked(MouseEvent e)
	{
		select.repaint();
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		select.repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		select.turnBorderOff();
		setBorderPainted(true);
		select.repaint();
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
		hover = true;
		select.repaint();
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		hover = false;
		select.repaint();
	}
}