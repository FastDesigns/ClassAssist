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
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;

import com.classassist.fastdesigns.gui.SelectClassScreen;
import com.classassist.fastdesigns.logic.DesktopSignInActivity;
import com.classassist.fastdesigns.logic.FrameSize;
import com.classassist.fastdesigns.logic.NewMessage;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.PaintContext;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.ColorModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

/**
 * The initial login screen of the program.
 * @author Fast Designs
 * @version 1.0
 */
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
	private JPanel startPanel;// = new JPanel();//PanelBackground("coolblue.jpg");
	private JPanel loginPanel = new JPanel();
	private JPanel contentPanel = new JPanel();
	private JPanel userPanel = new JPanel();
	private JPanel passPanel = new JPanel();
	private JPanel acceptPanel = new JPanel();
	// Frame
	private JFrame mainFrame; // Add/remove all panels from this window
	
	private Image img; //background
	private boolean missingBackground = false; //becomes true if img cannot be found
	
	/**
	 * Sets the panel with its initial values.
	 * @param main The JFrame that holds this panel
	 */
	public StartScreen(JFrame main)
	{
		loadBackground("background.png");
		this.mainFrame= main;
		startPanel = new JPanel() //create the background
		{
			private static final long serialVersionUID = 1L;
			@Override
			protected void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				if(!missingBackground)
				{
					for(int x = 0; x < mainFrame.getWidth(); x+= img.getWidth(null))
					{
						for(int y = 0; y < mainFrame.getHeight(); y+= img.getHeight(null))
						{
							g.drawImage(img, x, y, null);
						}
					}
				}
			}
		};
		mainFrame.add(startPanel);
		makeButtons();
		makeFields();
		makeLabels();
		guiBuilder();
	}
	
	private void loadBackground(String name)
	{
		try
		{
			img = ImageIO.read(this.getClass().getResource("res/" + name));
		}
		catch (IOException | IllegalArgumentException e)
		{
			missingBackground = true;
		}
	}
	
	/**
	 * Sets the parent JFrame's title and close behavior.
	 */
	public void createWindow()
	{
		mainFrame.setTitle("Class Assist");	
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/**
	 * Creates the buttons for the panel and sets their ActionListeners.
	 */
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
	
	/**
	 * Creates the text feilds for the panel and sets their ActionListeners.
	 */
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
	
	/**
	 * Creates the text labels for the panel.
	 */
	public void makeLabels()
	{
		userLabel = new JLabel("Username: ");
		passLabel = new JLabel("Password: ");
		bufferLabel = new JLabel("   ");
		// Set Label Font color
		userLabel.setForeground(Color.white);
		passLabel.setForeground(Color.white);
	}
	
	/**
	 * Defines the layouts for all of the various panels of the GUI.
	 */
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
		

		new FrameSize(mainFrame);
	}
	
	/**
	 * Quries the database and determines if the input credentials match a particular
	 * user; if so, log them in, else, display an error message,
	 */
	public void loginAction()
	{
		final String userName = userField.getText();
		char[] passWord = passField.getPassword();
		String passString = "";
		for (int i = 0; i < passWord.length;i++) // Alex's code was length-1???
			passString=passString+passWord[i];
		// check input
		final String pass = passString;
		Thread thread = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				if (DesktopSignInActivity.logIn(new String[] {userName,pass}))
				{
					if (userName.equals("admin"))
						loadAdmin();
					
					else
					// needs to do teacher version here
						loadTeacher(userName);
				}
				else
				{
					clearAction();
//					userField.setForeground(Color.red);
//					userField.setText("Invalid Credentials");
					reloadStart();
					new NewMessage("Invalid Credentials");
				}
			}
		});
		loading();
		thread.start();
	}
	
	private void loading()
	{
		mainFrame.getContentPane().removeAll();
		mainFrame.setLayout(new BorderLayout());
		mainFrame.add(new PanelLoading(), BorderLayout.CENTER);
		mainFrame.revalidate();
	}
	
	private void reloadStart()
	{
		mainFrame.getContentPane().removeAll();
		mainFrame.add(startPanel);
		mainFrame.revalidate();
		mainFrame.repaint();
	}
	
	/**
	 * Clears the text in the text fields.
	 */
	public void clearAction()
	{
		userField.setText("");
		userField.setForeground(Color.BLACK);
		passField.setText("");
		userField.requestFocusInWindow();
	}
	
	/**
	 * Sets the settings for the parent JFrame.
	 */
	public void frameSetting()
	{
		mainFrame.pack();
		mainFrame.setSize(1280,720);
		mainFrame.setVisible(true);
	}
	
	/**
	 * Loads the administrator view.
	 */
	public void loadAdmin()
	{
		SelectClassScreen classes = new SelectClassScreen(mainFrame);
		mainFrame.getContentPane().removeAll();
		mainFrame.add(classes);
		frameSetting();
	}
	
	/**
	 * Loads the teacher view.
	 * @param userName The username of the currently logged in user
	 */
	public void loadTeacher(String userName)
	{
		SelectClassScreen classes = new SelectClassScreen(mainFrame, userName);
		mainFrame.getContentPane().removeAll();
		mainFrame.add(classes); // Displays teacher view of classes
		frameSetting();
	}
}
