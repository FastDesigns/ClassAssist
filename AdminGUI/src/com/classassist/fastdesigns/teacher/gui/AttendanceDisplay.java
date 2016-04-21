package com.classassist.fastdesigns.teacher.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.classassist.fastdesigns.gui.MyScrollBarUI;
import com.classassist.fastdesigns.gui.SelectClassScreen;
import com.classassist.fastdesigns.logic.GetAttendance;
import com.classassist.fastdesigns.logic.UpdateStatus;



/**
 * 
 * @author Chase Abe
 * 
 * _________ .__                            _____                .__          __   
 * \_   ___ \|  | _____    ______ ______   /  _  \   ______ _____|__| _______/  |_ 
 * /    \  \/|  | \__  \  /  ___//  ___/  /  /_\  \ /  ___//  ___/  |/  ___/\   __\
 * \     \___|  |__/ __ \_\___ \ \___ \  /    |    \\___ \ \___ \|  |\___ \  |  |  
 *  \______  /____(____  /____  >____  > \____|__  /____  >____  >__/____  > |__|  
 *         \/          \/     \/     \/          \/     \/     \/        \/        
 *
 */
public class AttendanceDisplay extends JPanel
{
	private ArrayList<String> names = new ArrayList<>();
	private ArrayList<StatusIndicator> statusList = new ArrayList<>();
	private JPanel content = new JPanel();
	private JPanel student = new JPanel();
	private JScrollPane studentsPane = new JScrollPane(student);
	private SelectClassScreen select;
	private Timer check;
	private Timer cancel;
	
	public AttendanceDisplay(String[] s, SelectClassScreen scs){
		this.select = scs;
		student.setLayout(new BorderLayout());
		setStudents(s);
		studentsPane.setBorder(BorderFactory.createLineBorder(Color.black));
		student.setBackground(Color.white);
		this.setLayout(new BorderLayout());
		this.setBorder(new EmptyBorder(20, 20, 20, 20));
		addPanels();
		
	}
	
	private void addPanels()
	{
		this.add(content, BorderLayout.CENTER);
		content.setLayout(new BorderLayout());
		content.add(studentsPane, BorderLayout.CENTER);
		this.setBackground(Color.darkGray);
		studentsPane.getVerticalScrollBar().setUI(new MyScrollBarUI());
		studentsPane.getVerticalScrollBar().setUnitIncrement(8);
		studentsPane.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));
	}
	
	private void populateStudents()
	{
		student.removeAll();
		JPanel start = new JPanel();
		start.setLayout(new BoxLayout(start, BoxLayout.Y_AXIS));
		for(String n : names)
		{
			final String nam = n;
			JLabel name = new JLabel(n);
			JLabel mac = new JLabel("MAC ADDRESS HERE");
			name.setFont(new Font(name.getName(), Font.PLAIN, 24));
			mac.setFont(new Font(name.getName(), Font.PLAIN, 24));
			JPanel s = new JPanel();
			s.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.white));
			s.setBackground(Color.white);
			s.setLayout(new GridLayout(1, 3));
			s.add(name);
			final StatusIndicator status = new StatusIndicator(n, select.getSelectedClass());
			statusList.add(status);
			Thread thread = new Thread(new Runnable()
			{
				@Override
				public void run()
				{
					new UpdateStatus(nam, status, select.getSelectedClass());
				}
			});
			thread.start();
			s.add(status);
			s.add(mac);
			start.add(s);

		}
		student.add(start, BorderLayout.PAGE_START);
	}
	
	public void setStudents(String[] l)
	{
		names.clear();
		for(String pres : l)
		{
			names.add(pres);
		}
		populateStudents();
	}
	
	/**
	 * timer checks each students attendance and updates status indicator to display accordingly
	 */
	public void startTimer()
	{
		Thread timerThread = new Thread(new Runnable()
		{
			public void run()
			{
				cancelTimer();
				check = new Timer();
				check.scheduleAtFixedRate(new TimerTask()
				{
					@Override
					public void run()
					{
						for(int i = 0; i < names.size(); i++)
						{
							new UpdateStatus(names.get(i), statusList.get(i), select.getSelectedClass());
						}
					}
				}, 20, 3);
			}
		});
		timerThread.start();
	}
	
	private void cancelTimer()
	{
		cancel = new Timer();
		cancel.schedule(new TimerTask()
		{
			@Override
			public void run()
			{
				check.cancel();
				select.notScanning();
				check.purge();
			}
		}, 300000);
	}
	
}
