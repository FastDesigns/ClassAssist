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
import com.classassist.fastdesigns.logic.AddTeacher;
import com.classassist.fastdesigns.logic.CreateClass;
import com.classassist.fastdesigns.logic.NewMessage;

/**
 * Add students GUI
 * @author djust
 *
 */
public class AddTeacherScreen extends JPanel
{
	/**
	 * Create Student GUI version 1
	 */
	private static final long serialVersionUID = 1L;
	
	private SelectClassScreen select;
	
	//main panel
	private JPanel main = new JPanel();
	
	//fields panel
	private JPanel fieldsPanel = new JPanel();
	
	//Student First Name
	private JPanel tFNamePanel = new JPanel();
	private JLabel tFName = new JLabel("Teacher First Name: ");
	private JTextField tFNameText = new JTextField(20);
	
	//Student Last Name
	private JPanel tLNamePanel = new JPanel();
	private JLabel tLName = new JLabel("Teacher Last Name: ");
	private JTextField tLNameText = new JTextField(20);
	
	//Username
	private JPanel uNamePanel = new JPanel();
	private JLabel uName = new JLabel("Teacher Username: ");
	private JTextField uNameText = new JTextField(20);
	
	//submit button fields
	private JPanel submitButtonPanel = new JPanel();
	private JButton submitButton = new MyButton("Submit");
	
	public AddTeacherScreen(SelectClassScreen s)
	{
		this.select = s;
		this.setLayout(new BorderLayout());
		setupComponents();
	}
	
	private void setupComponents()
	{
		//add panels
		addPanels();
		addFields(); //add first and last name, ID, username, and submit to panels
		
		submitButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(tFNameText.getText().split(" ").length > 1 || tLNameText.getText().split(" ").length > 1)
				{
					new NewMessage("Cannot have spaces in names");
				}
				else
				{
					new AddTeacher(tFNameText.getText(), tLNameText.getText(), uNameText.getText(), AddTeacherScreen.this);
					Thread thread = new Thread(new Runnable()
					{
						@Override
						public void run()
						{
							try
							{
								Thread.sleep(3000);
								select.makeTeachers();
							}
							catch(InterruptedException e)
							{
								e.printStackTrace();
							}
						}
					});
					thread.start();
				}
			}
		});
	}
	
	private void addPanels()
	{

		this.add(main, BorderLayout.CENTER);
		main.setBackground(Color.darkGray);
		main.setLayout(new GridBagLayout());
		main.add(fieldsPanel);
		fieldsPanel.setLayout(new BoxLayout(fieldsPanel, BoxLayout.PAGE_AXIS));
		//first name panel
		fieldsPanel.add(tFNamePanel);
		tFNamePanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		tFNamePanel.setBackground(Color.darkGray);
		//last name panel
		fieldsPanel.add(tLNamePanel);
		tLNamePanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		tLNamePanel.setBackground(Color.darkGray);
		//username panel
		fieldsPanel.add(uNamePanel);
		uNamePanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		uNamePanel.setBackground(Color.darkGray);
		//submit button
		fieldsPanel.add(submitButtonPanel);
		submitButtonPanel.setBackground(Color.darkGray);
	}
	
	private void addFields()
	{
		//first name fields
		tFNamePanel.add(tFName);
		tFName.setForeground(Color.white);
		tFNamePanel.add(tFNameText);
		//last name fields
		tLNamePanel.add(tLName);
		tLName.setForeground(Color.white);
		tLNamePanel.add(tLNameText);
		//username fields
		uNamePanel.add(uName);
		uName.setForeground(Color.white);
		uNamePanel.add(uNameText);
		
		//submit fields
		submitButtonPanel.add(submitButton);
	}
}