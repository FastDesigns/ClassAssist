package com.classassist.fastdesigns.teacher.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.UIManager;

import com.classassist.fastdesigns.logic.DeleteAttendance;
import com.classassist.fastdesigns.logic.SetAttendance;


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
	private String username;
	private String classname;
	
	public StatusIndicator(String username, String classname){
		// Code from Chase Abe's project 5 for ITEC 220
		this.username=username;
		this.classname=classname;
		this.setOpaque(true);
		this.addActionListener(this);
		
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		if(status)
			g.setColor(Color.green);
		else
			g.setColor(Color.red);

		g.fillRect(0, 0, getWidth(), getHeight());
		repaint();
	}
	
	public boolean checkStatus(){
		return status;
	}
	
	private void changeStatus(){
		status = !status;
		if(status==true)
			SetAttendance.setAttendance(new String[]{username, classname});
		if(status==false)
			new DeleteAttendance(new String[]{username,classname});
	}
	
	/**
	 * Changes status to true or false
	 * @param b	true or false
	 */
	public void setStatus(boolean b)
	{
		status = b;
		repaint();
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		changeStatus();
		
	}
}
