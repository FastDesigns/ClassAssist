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
import java.util.ArrayList;

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
public class AttendanceDisplay extends JPanel{
	private ArrayList<String> names = new ArrayList<>();
	private JPanel content = new JPanel();
	private JPanel student = new JPanel();
	private JScrollPane studentsPane = new JScrollPane(student);
	private SelectClassScreen select;
	
	public AttendanceDisplay(String[] s, SelectClassScreen sc){
		this.select = sc;
		student.setLayout(new BorderLayout());
		setStudents(s);
		studentsPane.setBorder(BorderFactory.createLineBorder(Color.black));
		student.setBackground(Color.white);
		
//		student.setCellRenderer(new AttendanceListRenderer());
		
//		for (int i = 0; i<students.length;i++){
//			final int temp = i;
//			names.add(new JLabel(students[temp]));
//			
//			present.add(new StatusIndicator());
//			status.add(new JLabel("<html><span style='font-size:20px'>"+"No Record"));
			// some kind of check for present or not
			//change radio button to reflect status
			
			
			//give each button an action listener
//			present.get(temp).addActionListener(new ActionListener() {
//			    public void actionPerformed(ActionEvent e) {
//			        StatusIndicator button = (StatusIndicator) e.getSource();
//			       // button.setFocusPainted(false);
//			        String text = names.get(temp).getText();
//			        int cut = text.lastIndexOf('>')+1;
//			        String name = text.substring(cut, text.length());
//			        
//			        boolean aqui = button.checkStatus();
//			        System.out.println(aqui);
//			        if (aqui == false){
//			        	button.setStatus(true);                       // reversed because state is changing
//			        	status.get(temp).setText("<html><span style='font-size:20px'>"+"Present");
////			        	System.out.println(name + " is not present");
//			        }
//			        else{
//			        	button.setStatus(false);
//			        	status.get(temp).setText("<html><span style='font-size:20px'>"+"Absent");
////			        	System.out.println(name + " is present");
//			        }
//			        
//			    }
//			});
//			
//			content.add(names.get(i));
//			content.add(present.get(i));
//			content.add(status.get(i));
//		}
		
		//this.add(test);
//		scroll.add(this);
//		scroll.setSize(800,800);
//		fdsa.getContentPane().add(scroll);
		//fdsa.add(scroll);
//		JScrollPane scroll = new JScrollPane();
//		scroll.setViewportView(content);
//		fdsa.setSize(1150,600);
		this.setLayout(new BorderLayout());
		this.setBorder(new EmptyBorder(20, 20, 20, 20));
		addPanels();
//		this.add(scroll, BorderLayout.CENTER);
		//fdsa.pack();
		//this.requestFocus();
//		fdsa.setVisible(true);
		
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
			JLabel name = new JLabel(n);
			JLabel mac = new JLabel("MAC ADDRESS HERE");
			name.setFont(new Font(name.getName(), Font.PLAIN, 24));
			mac.setFont(new Font(name.getName(), Font.PLAIN, 24));
			JPanel s = new JPanel();
			s.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.white));
			s.setBackground(Color.white);
			s.setLayout(new GridLayout(1, 3));
			s.add(name);
			s.add(new StatusIndicator(n, select.getSelectedClass()));
			s.add(mac);
			start.add(s);

		}
		student.add(start, BorderLayout.PAGE_START);
		
//		for(String n : names)
//		{
//			student.add(new JLabel(n));
//			student.add(new StatusIndicator());
//			student.add(new JLabel("MAC ADDRESS HERE"));
//		}
//		for(String n : names)
//		{
//			namePanel.add(new JLabel(n));
//		}
	}
	
	public void setStudents(String[] l)
	{
//		student.setListData(l);
		names.clear();
		for(String pres : l)
		{
			names.add(pres);
		}
		populateStudents();
	}
	
	
//	//http://www.coderanch.com/t/456966/GUI/java/give-color-JRadioButtons
//	public void paintIcon(Component c, Graphics g, int x, int y){
//		
//		g.setColor(Color.red);
//		g.fillOval(x, y, 10, 10); // 10 is the dot diameter
//	}
}
