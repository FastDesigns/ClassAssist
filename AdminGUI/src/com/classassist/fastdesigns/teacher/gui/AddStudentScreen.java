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
import com.classassist.fastdesigns.logic.AddStudent;
import com.classassist.fastdesigns.logic.CreateClass;

/**
 * Add students GUI
 * @author djust
 *
 */
public class AddStudentScreen extends JPanel
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
	private JPanel sFNamePanel = new JPanel();
	private JLabel sFName = new JLabel("Student First Name: ");
	private JTextField sFNameText = new JTextField(20);
	
	//Student Last Name
	private JPanel sLNamePanel = new JPanel();
	private JLabel sLName = new JLabel("Student Last Name: ");
	private JTextField sLNameText = new JTextField(20);
	
	//Student ID
	private JPanel sIDPanel = new JPanel();
	private JLabel sID = new JLabel("Student ID: ");
	private JTextField sIDText = new JTextField(20);
	
	//Username
	private JPanel uNamePanel = new JPanel();
	private JLabel uName = new JLabel("Student Username: ");
	private JTextField uNameText = new JTextField(20);
	
	//submit button fields
	private JPanel submitButtonPanel = new JPanel();
	private JButton submitButton = new MyButton("Submit");
	
	public AddStudentScreen(SelectClassScreen s)
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
				new AddStudent(sFNameText.getText(), sLNameText.getText(), sIDText.getText(), uNameText.getText(), select.getSelectedClass());
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
		fieldsPanel.add(sFNamePanel);
		sFNamePanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		sFNamePanel.setBackground(Color.darkGray);
		//last name panel
		fieldsPanel.add(sLNamePanel);
		sLNamePanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		sLNamePanel.setBackground(Color.darkGray);
		//ID panel
		fieldsPanel.add(sIDPanel);
		sIDPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		sIDPanel.setBackground(Color.darkGray);
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
		sFNamePanel.add(sFName);
		sFName.setForeground(Color.white);
		sFNamePanel.add(sFNameText);
		//last name fields
		sLNamePanel.add(sLName);
		sLName.setForeground(Color.white);
		sLNamePanel.add(sLNameText);
		//ID fields
		sIDPanel.add(sID);
		sID.setForeground(Color.white);
		sIDPanel.add(sIDText);
		//username fields
		uNamePanel.add(uName);
		uName.setForeground(Color.white);
		uNamePanel.add(uNameText);
		
		//submit fields
		submitButtonPanel.add(submitButton);
	}
}