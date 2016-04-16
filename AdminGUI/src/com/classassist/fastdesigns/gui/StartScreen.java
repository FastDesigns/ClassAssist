package com.classassist.fastdesigns.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

import com.classassist.fastdesigns.gui.SelectClassScreen;
import com.classassist.fastdesigns.logic.DesktopSignInActivity;

public class StartScreen
{
	// Buttons
	private JButton loginBtn;
	private JButton clearBtn;
	// TextFields
	private JTextField userField;
	private JPasswordField passField;
	// Labels
	private JLabel userLabel;
	private JLabel passLabel;
	private JLabel bufferLabel; // puts a space between login & clear buttons
	// Panel
	private JPanel startPanel = new JPanel();//PanelBackground("coolblue.jpg");
	private JPanel loginPanel = new JPanel();
	private JPanel contentPanel = new JPanel();
	private JPanel userPanel = new JPanel();
	private JPanel passPanel = new JPanel();
	private JPanel acceptPanel = new JPanel();
	// Frame
	private JFrame mainFrame; // Add/remove all panels from this window
	
	public StartScreen(JFrame main)
	{
		this.mainFrame= main;
		mainFrame.add(startPanel);
		setWindow();
		makeButtons();
		makeFields();
		makeLabels();
		guiBuilder();
	}
	
	private void setWindow()
	{
		mainFrame.setTitle("Class Assist");	
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void makeButtons()
	{
		loginBtn = new MyButton("Login");
		clearBtn = new MyButton("Clear");
		
		// Set Button size
		loginBtn.setPreferredSize(new Dimension(110, 26));
		clearBtn.setPreferredSize(new Dimension(110, 26));
		
		// Add action listeners to each button
		loginBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				loginAction(); // helper function for loginBtn
				}});
		clearBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				clearAction(); // helper function for clearBtn
				}});
		
	}
	
	
	public void makeFields()
	{
		userField = new JTextField();
		passField = new JPasswordField();
		// Set TextField width
		userField.setColumns(15);
		passField.setColumns(15);
		
		// Code to add key listener to "Enter Key"
		//http://stackoverflow.com/questions/4419667/detect-enter-press-in-jtextfield
		Action submit = new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e){
				loginAction();}
		};
		userField.addActionListener(submit);
		passField.addActionListener(submit);
	}
	
	public void makeLabels()
	{
		userLabel = new JLabel("Username: ");
		passLabel = new JLabel("Password: ");
		bufferLabel = new JLabel("   ");
		// Set Label Font color
		userLabel.setForeground(Color.white);
		passLabel.setForeground(Color.white);
	}
	
	public void guiBuilder()
	{
		startPanel.setLayout(new GridBagLayout());
		startPanel.setBackground(Color.darkGray);
		//add panels	
		startPanel.add(contentPanel);//, BorderLayout.CENTER);
		contentPanel.setLayout(new GridBagLayout());
		contentPanel.add(loginPanel);
		loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
		loginPanel.add(userPanel);
		userPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		loginPanel.add(passPanel);
		passPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		loginPanel.add(acceptPanel);
		acceptPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		//add components to panels
		userPanel.add(userLabel);
		userPanel.add(userField);
		passPanel.add(passLabel);
		passPanel.add(passField);
		acceptPanel.add(loginBtn);
		acceptPanel.add(bufferLabel);
		acceptPanel.add(clearBtn);
		
		loginPanel.setOpaque(false);
		contentPanel.setOpaque(false);
		userPanel.setOpaque(false);
		passPanel.setOpaque(false);
		acceptPanel.setOpaque(false);
		frameSetting();
	}
	
	
	public void loginAction()
	{
		String userName = userField.getText();
		char[] passWord = passField.getPassword();
		String passString = "";
		for (int i = 0; i < passWord.length;i++) // Alex's code was length-1???
			passString=passString+passWord[i];
		// check input
		if (DesktopSignInActivity.logIn(new String[] {userName,passString}))
		{
			if (userName.equals("admin"))
				loadAdmin();
			
			else
			// needs to do teacher version here
				loadTeacher(userName);
		}
		else
		{
			userField.setForeground(Color.red);
			userField.setText("Invalid Credentials");
		}
	}
	
	public void clearAction()
	{
		userField.setText("");
		userField.setForeground(Color.BLACK);
		passField.setText("");
		userField.requestFocusInWindow();
	}
	
	public void frameSetting()
	{
		mainFrame.pack();
		mainFrame.setSize(1280,720);
		mainFrame.setVisible(true);
	}
	
	public void loadAdmin()
	{
		mainFrame.getContentPane().removeAll();
		View v = new View(mainFrame);
	}
	
	public void loadTeacher(String userName)
	{
		SelectClassScreen classes = new SelectClassScreen(mainFrame, userName);
		mainFrame.getContentPane().removeAll();
		mainFrame.add(classes); // Displays teacher view of classes
		frameSetting();
	}
	

}
