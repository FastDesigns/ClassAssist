package com.classassist.fastdesigns.teacher.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.classassist.fastdesigns.gui.MyButton;
import com.classassist.fastdesigns.gui.SelectClassScreen;
import com.classassist.fastdesigns.logic.TakeAttendance;
import com.classassist.fastdesigns.logic.TakingAttendance;
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
	private JPanel top = new JPanel();
	private JPanel actionsPanel = new JPanel();
	private JButton attend = new MyButton("Take Attendance");
	private JPanel scanPanel = new JPanel();
	private JPanel attendance;
	private AttendanceDisplay at;
	private SelectClassScreen select;
	
	public StudentsLayout(String[] s, AttendanceDisplay a, SelectClassScreen sel)
	{
		this.select = sel;
		attendance = a;
		at = a;
		setLayout(new BorderLayout());
		setup();
		add(contentPanel, BorderLayout.CENTER);
	}
	
	private void setup()
	{
		contentPanel.setLayout(new BorderLayout());
		contentPanel.add(content, BorderLayout.CENTER);
		contentPanel.add(top, BorderLayout.PAGE_START);
		content.setLayout(new BorderLayout());
		content.add(attendance, BorderLayout.CENTER);
		top.setBackground(Color.darkGray);
		top.add(actionsPanel);
		top.setBorder(new EmptyBorder(0, 0, 0, 0));
		actionsPanel.add(attend);
		actionsPanel.setLayout(new BoxLayout(actionsPanel, BoxLayout.Y_AXIS));
		actionsPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
		scanGif();
		actionsPanel.setBackground(Color.darkGray);
		addActionListeners();
	}
	
	private void scanGif()
	{
		scanPanel = new ScanPanel();
		scanPanel.setVisible(false);
		actionsPanel.add(scanPanel);
		attend.setAlignmentX(Component.CENTER_ALIGNMENT);
		scanPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
	}
	
	public class ScanPanel extends JPanel
	{
		/**
		 * ScanPanel version 1
		 */
		private static final long serialVersionUID = 1L;
		
		public ScanPanel()
		{
			super();
			setBackground(Color.darkGray);
//			this.setLayout(new BorderLayout());
			Icon icon = new ImageIcon(this.getClass().getResource("/res/scanning.gif"));
			JLabel gif = new JLabel(icon);
			this.add(gif, BorderLayout.CENTER);
			setBorder(new EmptyBorder(0, 0, 0, 0));
		}
	}
	
	public void scanning()
	{
		scanPanel.setVisible(true);
		new TakingAttendance(select.getUser(), select.getSelectedClass());
	}
	
	public void notScanning()
	{
		scanPanel.setVisible(false);
	}
	
	private void addActionListeners()
	{
		attend.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
				final Date date = new Date();
				Thread thread = new Thread(new Runnable()
				{
					public void run()
					{
						TakeAttendance.recordAttendance(select.getSelectedClass(), dateFormat.format(date));
					}
				});
				thread.start();
				at.startTimer();
				attendance.setBorder(new EmptyBorder(0, 20, 20, 20));
				scanning();
			}
		});
	}
}