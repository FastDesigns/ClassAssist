package com.classassist.fastdesigns.teacher.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JPanel;
import com.classassist.fastdesigns.gui.MyButton;
import com.classassist.fastdesigns.gui.SelectClassScreen;
import com.classassist.fastdesigns.logic.TakeAttendance;
import com.classassist.fastdesigns.teacher.gui.AttendanceDisplay;
/**
 * Layout and operations for exporting attendance
 * @author Eddie Justice
 *
 */
public class StudentsLayout extends JPanel
{

	/**
	 * StudentsLayout version 1
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPanel = new JPanel();
	private JPanel content = new JPanel();
	private JPanel actionsPanel = new JPanel();
	private JButton attend = new MyButton("Take Attendance");
	private JPanel attendance;
	private SelectClassScreen select;
	
	public StudentsLayout(String[] s, AttendanceDisplay a, SelectClassScreen sel)
	{
		this.select = sel;
		attendance = a;
		setLayout(new BorderLayout());
		setup();
		add(contentPanel, BorderLayout.CENTER);
	}
	
	private void setup()
	{
		contentPanel.setLayout(new BorderLayout());
		contentPanel.add(content, BorderLayout.CENTER);
		contentPanel.add(actionsPanel, BorderLayout.PAGE_START);
		content.setLayout(new BorderLayout());
		content.add(attendance, BorderLayout.CENTER);
		
		actionsPanel.add(attend);
		actionsPanel.setBackground(Color.darkGray);
		addActionListeners();
	}
	
	private void addActionListeners()
	{
		attend.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
				Date date = new Date();
				TakeAttendance.recordAttendance(select.getSelectedClass(), dateFormat.format(date));
			}
		});
	}
}