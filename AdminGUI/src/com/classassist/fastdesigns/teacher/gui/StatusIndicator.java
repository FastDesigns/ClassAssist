package com.classassist.fastdesigns.teacher.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.UIManager;


/**
 * 
 * @author Chase Abe
 *
 */
public class StatusIndicator extends JButton implements ActionListener {
	private boolean present;
	private ImageIcon here;
	private ImageIcon absent;
	private boolean status = false;
	
	public StatusIndicator(){
		// Code from Chase Abe's project 5 for ITEC 220
		present = false;
		this.setOpaque(true);
		this.addActionListener(this);
		//this.setBorder(null);
		//absent = new ImageIcon((new ImageIcon("res/bad.png").getImage().getScaledInstance(600,100,java.awt.Image.SCALE_SMOOTH)));
		//here  = new ImageIcon((new ImageIcon("res/good.png").getImage().getScaledInstance(600,100,java.awt.Image.SCALE_SMOOTH)));
		//this.setIcon(absent);
//		 try {
//			    UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName() );
//			   // this.setBackground(Color.RED);
//			 } catch (Exception e) {
//			            e.printStackTrace();
//			 }
		
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		if(status)
			g.setColor(Color.green);
		else
			g.setColor(Color.red);

		g.fillRect(0, 0, getWidth(), getHeight());
		
//		g.setColor(Color.black);
//		g.drawRect(1, 1, getWidth() - 1, getHeight() - 1);
	}
	
	public boolean checkStatus(){
		return status;
	}
	
	private void changeStatus(){
		status = !status;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		changeStatus();
	}
}
