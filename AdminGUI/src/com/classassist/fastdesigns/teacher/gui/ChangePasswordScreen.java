package com.classassist.fastdesigns.teacher.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
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

/**
 * Delete students GUI
 * @author djust
 *
 */
public class ChangePasswordScreen extends JPanel
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
	
	//password fields
	private JPanel fields = new JPanel();
	private JPanel oldPassPanel = new JPanel();
	private JLabel oldPass = new JLabel("Re-enter old password:");
	private JPasswordField oldPassText = new JPasswordField(20);
	private JPanel newPassPanel = new JPanel();
	private JLabel newPass = new JLabel("New Password:");
	private JPasswordField newPassText = new JPasswordField(20);
	private JPanel buttonPanel = new JPanel();
	private JButton changePassButton = new MyButton("Change Password");
	
	public ChangePasswordScreen(SelectClassScreen s, String u)
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
		fields.setLayout(new BoxLayout(fields, BoxLayout.Y_AXIS));
		this.setBackground(Color.darkGray);
	}
	
	private void addComponents()
	{
		main.add(fields);
		fields.setBackground(Color.darkGray);
		fields.add(oldPassPanel);
		oldPassPanel.setBackground(Color.darkGray);
		oldPassPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.setBackground(Color.darkGray);
		fields.add(newPassPanel);
		newPassPanel.setBackground(Color.darkGray);
		newPassPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		oldPassPanel.add(oldPass);
		oldPass.setForeground(Color.white);
		oldPassPanel.add(oldPassText);
		newPassPanel.add(newPass);
		newPass.setForeground(Color.white);
		newPassPanel.add(newPassText);
		fields.add(buttonPanel);
		buttonPanel.add(changePassButton);
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
		
		changePassButton.addActionListener(l);
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
		
		JLabel label = new JLabel("Are you sure you want to change your password?");
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
		changePassButton.removeActionListener(l);
		addActions(con, cancel, oldPassText.getPassword(), newPassText.getPassword());
	}
	
	//adds action listeners to confirm and cancel buttons
	private void addActions(JButton co, JButton ca, final char[] oldPass, final char[] newPass)
	{
		co.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				changePassButton.addActionListener(l);
				confirmAction(oldPass, newPass);
				closeFrame();
				clear();
			}
		});
		
		ca.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				changePassButton.addActionListener(l);
				closeFrame();
			}
		});
	}
	
	private void clear()
	{
		oldPassText.setText("");
		newPassText.setText("");
	}
	
	private void confirmAction(char[] oldP, char[] newP)
	{
		String oldPass = new String(oldP);
		String newPass = new String(newP);
		new ChangePassword(user, oldPass, newPass);
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