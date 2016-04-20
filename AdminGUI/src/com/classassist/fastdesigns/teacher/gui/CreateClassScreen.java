package com.classassist.fastdesigns.teacher.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.classassist.fastdesigns.gui.MyButton;
import com.classassist.fastdesigns.gui.SelectClassScreen;
import com.classassist.fastdesigns.logic.CreateClass;

public class CreateClassScreen extends JPanel
{
	/**
	 * Create Student GUI version 1
	 */
	private static final long serialVersionUID = 1L;
	
	private String username; //teacher using program
	private SelectClassScreen select;
	
	//main panel
	private JPanel main = new JPanel();
	
	//fields panel
	private JPanel fieldsPanel = new JPanel();
	
	//Class Name
	private JPanel cNamePanel = new JPanel();
	private JLabel cName = new JLabel("Class Name: ");
	private JTextField cNameText = new JTextField(20);
	
	//submit button fields
	private JPanel submitButtonPanel = new JPanel();
	private JButton submitButton = new MyButton("Submit");
	
	public CreateClassScreen(String user, SelectClassScreen s)
	{
		this.username = user;
		this.select = s;
		this.setLayout(new BorderLayout());
		setupComponents();
	}
	
	public void setupComponents()
	{
		System.out.println(cName.getFont());
		//add panels
		this.add(main, BorderLayout.CENTER);
		main.setBackground(Color.darkGray);
		main.setLayout(new GridBagLayout());
		main.add(fieldsPanel);
		fieldsPanel.setLayout(new BoxLayout(fieldsPanel, BoxLayout.PAGE_AXIS));
		fieldsPanel.add(cNamePanel);
		cNamePanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		cNamePanel.setBackground(Color.darkGray);
		fieldsPanel.add(submitButtonPanel);
		submitButtonPanel.setBackground(Color.darkGray);
		
		//first name fields
		cNamePanel.add(cName);
		cName.setForeground(Color.white);
		cNamePanel.add(cNameText);
		
		//submit fields
		submitButtonPanel.add(submitButton);
		
		submitButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				new CreateClass(username, cNameText.getText());
				Thread thread = new Thread(new Runnable()
				{
					@Override
					public void run()
					{
						try
						{
							Thread.sleep(3000);
							select.makeClasses();
						}
						catch(InterruptedException e)
						{
							e.printStackTrace();
						}
					}
				});
				thread.start();
			}
		});
	}
}