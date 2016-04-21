package com.classassist.fastdesigns.teacher.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.classassist.fastdesigns.gui.MyButton;
import com.classassist.fastdesigns.gui.MyScrollBarUI;
import com.classassist.fastdesigns.gui.SelectClassScreen;
import com.classassist.fastdesigns.logic.AddStudent;
import com.classassist.fastdesigns.logic.ChangePassword;
import com.classassist.fastdesigns.logic.DeleteStudent;
import com.classassist.fastdesigns.logic.GetAttendance;
import com.classassist.fastdesigns.logic.ItemListRenderer;
import com.classassist.fastdesigns.logic.ResetPassword;

/**
 * Delete students GUI
 * @author djust
 *
 */
public class ResetPasswordScreen extends JPanel
{
	/**
	 * Delete Student GUI version 1
	 */
	private static final long serialVersionUID = 1L;
	
	private SelectClassScreen select;
	private JFrame confirm;
	private ActionListener l;
	private String user;
	
	//main panel
	private JPanel main = new JPanel();
	
	//reset
	private JButton reset = new MyButton("Reset Password");
	
	public ResetPasswordScreen(SelectClassScreen s, String u)
	{
		this.select = s;
		this.user = u;
		this.setLayout(new BorderLayout());
		setupComponents();
	}
	
	private void setupComponents()
	{
		//add panels
		addPanels();
		addComponents();
		addListener();
	}
	
	private void addPanels()
	{
		
		this.add(main, BorderLayout.CENTER);
		this.setBorder(new EmptyBorder(20, 20, 20, 20));
		main.setBackground(Color.darkGray);
		main.setLayout(new GridBagLayout());
		this.setBackground(Color.darkGray);
	}
	
	private void addComponents()
	{
		main.add(reset);
		reset.setFont(new Font(reset.getFont().getName(), Font.PLAIN, 24));
		reset.setBorder(new EmptyBorder(20, 20, 20, 20));
	}
	
	private void addListener()
	{
		l = new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				confirm();
			}
		};
		
		reset.addActionListener(l);
	}
	
	//confirmation dialog
	private void confirm()
	{
		confirm = new JFrame("Confirm");
		JPanel cPanel = new JPanel(); //holds the content
		
		confirm.add(cPanel);
		cPanel.setBackground(Color.darkGray); //color scheme
		cPanel.setBorder(new EmptyBorder(30, 30, 30, 30));
		cPanel.setLayout(new BoxLayout(cPanel, BoxLayout.Y_AXIS));

		JPanel lPane = new JPanel();
		JPanel lPane2 = new JPanel();
		lPane.setBackground(Color.darkGray);
		lPane2.setBackground(Color.darkGray);
		
		JLabel label = new JLabel("Are you sure you want to reset " + user + "'s password?");
		label.setForeground(Color.white);
		
		lPane.add(label);
		
		JButton con = new MyButton("Confirm");
		JButton cancel = new MyButton("Cancel");
		JPanel actionPane = new JPanel(); //holds confirm and cancel buttons
		actionPane.setBackground(Color.darkGray);
		actionPane.add(con);
		actionPane.add(cancel);
		
		cPanel.add(lPane);
		cPanel.add(lPane2);
		cPanel.add(actionPane);
		
		confirm.pack();
		confirm.setVisible(true);
		centerConfirm(confirm);
		reset.removeActionListener(l);
		addActions(con, cancel);
	}
	
	//adds action listeners to confirm and cancel buttons
	private void addActions(JButton co, JButton ca)
	{
		co.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				reset.addActionListener(l);
				confirmAction();
				closeFrame();
			}
		});
		
		ca.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				reset.addActionListener(l);
				closeFrame();
			}
		});
	}
	
	private void confirmAction()
	{
		new ResetPassword(user);
	}
	
	private void closeFrame()
	{
		confirm.dispose();
	}
	
	//centers confirmation dialog on mouses current position
	private void centerConfirm(JFrame m)
	{
		Point p = MouseInfo.getPointerInfo().getLocation();
		int x = p.x - (m.getWidth() / 2);
		int y = p.y - (m.getHeight() / 2);
		m.setLocation(new Point(x, y));
	}
	
	private void refresh()
	{
		repaint();
	}
}