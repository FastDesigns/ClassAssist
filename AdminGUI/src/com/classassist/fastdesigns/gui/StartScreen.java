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
	
	public void createWindow()
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
			clearAction();
//			userField.setForeground(Color.red);
//			userField.setText("Invalid Credentials");
			new NewMessage("Invalid Credentials", mainFrame);
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
	

	//http://www.java2s.com/Code/Java/2D-Graphics-GUI/RoundGradientPaintFilldemo.htm
	class RoundGradientPaint implements Paint {
	    protected Point2D point;

	    protected Point2D mRadius;

	    protected Color mPointColor, mBackgroundColor;

	    public RoundGradientPaint(double x, double y, Color pointColor,
	        Point2D radius, Color backgroundColor) {
	      if (radius.distance(0, 0) <= 0)
	        throw new IllegalArgumentException("Radius must be greater than 0.");
	      point = new Point2D.Double(x, y);
	      mPointColor = pointColor;
	      mRadius = radius;
	      mBackgroundColor = backgroundColor;
	    }

	    public PaintContext createContext(ColorModel cm, Rectangle deviceBounds,
	        Rectangle2D userBounds, AffineTransform xform, RenderingHints hints) {
	      Point2D transformedPoint = xform.transform(point, null);
	      Point2D transformedRadius = xform.deltaTransform(mRadius, null);
	      return new RoundGradientContext(transformedPoint, mPointColor,
	          transformedRadius, mBackgroundColor);
	    }

	    public int getTransparency() {
	      int a1 = mPointColor.getAlpha();
	      int a2 = mBackgroundColor.getAlpha();
	      return (((a1 & a2) == 0xff) ? OPAQUE : TRANSLUCENT);
	    }
	  }
	  public class RoundGradientContext implements PaintContext {
	    protected Point2D mPoint;

	    protected Point2D mRadius;

	    protected Color color1, color2;

	    public RoundGradientContext(Point2D p, Color c1, Point2D r, Color c2) {
	      mPoint = p;
	      color1 = c1;
	      mRadius = r;
	      color2 = c2;
	    }

	    public void dispose() {
	    }

	    public ColorModel getColorModel() {
	      return ColorModel.getRGBdefault();
	    }

	    public Raster getRaster(int x, int y, int w, int h) {
	      WritableRaster raster = getColorModel().createCompatibleWritableRaster(
	          w, h);

	      int[] data = new int[w * h * 4];
	      for (int j = 0; j < h; j++) {
	        for (int i = 0; i < w; i++) {
	          double distance = mPoint.distance(x + i, y + j);
	          double radius = mRadius.distance(0, 0);
	          double ratio = distance / radius;
	          if (ratio > 1.0)
	            ratio = 1.0;

	          int base = (j * w + i) * 4;
	          data[base + 0] = (int) (color1.getRed() + ratio
	              * (color2.getRed() - color1.getRed()));
	          data[base + 1] = (int) (color1.getGreen() + ratio
	              * (color2.getGreen() - color1.getGreen()));
	          data[base + 2] = (int) (color1.getBlue() + ratio
	              * (color2.getBlue() - color1.getBlue()));
	          data[base + 3] = (int) (color1.getAlpha() + ratio
	              * (color2.getAlpha() - color1.getAlpha()));
	        }
	      }
	      raster.setPixels(0, 0, w, h, data);

	      return raster;
	    }
	  }
}
