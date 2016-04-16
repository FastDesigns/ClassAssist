package com.classassist.fastdesigns.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import com.classassist.fastdesigns.logic.GetClassListForTeacher;
import com.classassist.fastdesigns.logic.GetStudentList;
import com.classassist.fastdesigns.logic.ReadFile;
import com.classassist.fastdesigns.teacher.gui.AddStudentScreen;
import com.classassist.fastdesigns.teacher.gui.AttendanceDisplay;
import com.classassist.fastdesigns.teacher.gui.CreateClassScreen;
import com.classassist.fastdesigns.teacher.gui.DeleteClassScreen;
import com.classassist.fastdesigns.teacher.gui.DeleteStudentScreen;
import com.classassist.fastdesigns.teacher.gui.ExportAttendance;
import com.classassist.fastdesigns.teacher.gui.StudentsLayout;

/**
 * Main Teacher display
 *
 */
public class SelectClassScreen extends JPanel
{
	/**
	 * Class Screen Version 1
	 */
	private static final long serialVersionUID = 1L;
	// JButtons
	private JButton logoutBtn;
//	private JButton attendBtn = new JButton("Take Attendance");//TeacherButton("attendance.png", this);
	private JButton importBtn = new TeacherButton("import.png", this);
	private JButton exportBtn = new TeacherButton("export.png", this);
	private JButton student = new TeacherButton("student.png", this);
	private JButton createClass = new TeacherButton("createclass.png", this);
	private JButton deleteClass = new TeacherButton("deleteclass.png", this);
	private JButton addStudent = new TeacherButton("addstudent.png", this);
	private JButton deleteStudent = new TeacherButton("deletestudent.png", this);
	private ArrayList<JButton> btnList = new ArrayList<>();
	// Swing Tools
	private JLabel selectLabel;
	private JComboBox<String> classCombo = new JComboBox<String>(new String[] {"Loading..."});
//	private String[] classes;
	// JFrame
	private JFrame mainFrame;
	// JPanels
	private JPanel classesPanel = new JPanel();
	private JPanel buttonPanel = new JPanel();
	private JScrollPane buttonScroll = new JScrollPane(buttonPanel);
	private JPanel contentPanel = new JPanel();
	private JPanel logoutPanel = new JPanel();
	private JPanel stud;// = new StudentsLayout(new String[] {"Loading..."});;
	// JFileChooser Tools
	private String user;
	private JFileChooser fc;
	
	public SelectClassScreen(JFrame main, String teacher)
	{
		this.mainFrame = main;
		this.user = teacher;
		makeButtons();
		makeLabel();
		makeClasses();
		addPanels();
		addButtons();
		
		fc = new JFileChooser();
		fc.setCurrentDirectory(new File(System.getProperty("user.home")));
		buttonPanel.setPreferredSize(new Dimension(125, buttonPanel.getPreferredSize().height)); //adding extra width to compensate for scrollbar
	}
	
	private void makeButtons()
	{
		logoutBtn = new MyButton("Logout");
		
		student.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				studentAction(); //helper function for student button
			}
		});
		logoutBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				logoutAction(); // helper function for loginBtn
				}});
//		attendBtn.addActionListener(new ActionListener(){
//			public void actionPerformed(ActionEvent e) {
//				attendanceAction(); // helper function for enterBtn
//				}});
		importBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				importAction(); // helper function for importBtn
				}});
		exportBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				exportAction(); // helper function for exportBtn			
				}});
		createClass.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				addClassAction();
			}
		});
		deleteClass.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				deleteClassAction();
			}
		});
		addStudent.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				addStudentAction();
			}
		});
		deleteStudent.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				deleteStudentAction();
			}
		});
	}
	
	private void makeLabel()
	{
		selectLabel = new JLabel("Select Class");
		selectLabel.setForeground(Color.white);
	}
	
	/**
	 * Generates a list of classes for classCombo
	 */
	@SuppressWarnings("static-access")
	public void makeClasses()
	{
		GetClassListForTeacher getClasses = new GetClassListForTeacher(this);
		getClasses.getClassList(new String[] {user});
	}
	
	/**
	 * changes the list of classes the user can see
	 * @param classes String array containing classes
	 */
	public void setClasses(String[] cl)
	{
		classCombo.removeAllItems();
		for(String clas : cl)
		{
			classCombo.addItem(clas);
		}
		getStudents();
	}
	
	private void addPanels()
	{
//		mainFrame.add(this);
		this.setLayout(new BorderLayout());
		this.add(classesPanel, BorderLayout.PAGE_START);
//		classesPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
		classesPanel.setBackground(Color.darkGray);
		this.add(buttonScroll, BorderLayout.WEST);
		buttonScroll.getVerticalScrollBar().setUI(new MyScrollBarUI());
		buttonScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		buttonScroll.getVerticalScrollBar().setUnitIncrement(10);
		buttonScroll.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.gray));
		buttonPanel.setBackground(Color.darkGray);
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		this.add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout());
		this.add(logoutPanel, BorderLayout.PAGE_END);
		logoutPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.gray));
		logoutPanel.setBackground(Color.darkGray);
		logoutPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
	}
	
	private void addButtons()
	{
		mainFrame.setPreferredSize(new Dimension(1280, 720));
		classesPanel.add(selectLabel);
		classesPanel.add(classCombo);
		classesPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.gray));
		logoutPanel.add(logoutBtn);
		
		btnList.add(student);
		btnList.add(importBtn);
		btnList.add(exportBtn);
		btnList.add(createClass);
		btnList.add(deleteClass);
		btnList.add(addStudent);
		btnList.add(deleteStudent);

		for(JButton b : btnList)
		{
			buttonPanel.add(b);
		}
	}
	
	/**
	 * sets all teacher buttons to not selected
	 */
	public void turnBorderOff()
	{
		for(JButton button : btnList)
		{
			button.setBorderPainted(false);
		}
	}
	
	//sets content panel to display list of students
	private void studentAction()
	{
		setStudents(new String[] {"Loading..."});
		getStudents();
	}
	
	private void logoutAction()
	{
		mainFrame.getContentPane().removeAll();
//		this.paintImmediately(0,0,1280,720); //https://community.oracle.com/thread/1350756?start=0&tstart=0
		@SuppressWarnings("unused")
		StartScreen start = new StartScreen(mainFrame);
	}
	
	private void getStudents()
	{
		new GetStudentList(this, classCombo.getSelectedItem().toString());
//		String[] Students = GetAttendance.getAttendance(new String[] {classCombo.getSelectedItem().toString(), dateFormat.format(date)}); 
	}
	
	/**
	 * creates a list of students enrolled in class
	 * @param students
	 */
	public void setStudents(String[] s)
	{
		AttendanceDisplay ad = new AttendanceDisplay(s);
		stud = new StudentsLayout(s, ad, this);
		contentPanel.removeAll();
		contentPanel.add(stud, BorderLayout.CENTER);
		contentPanel.revalidate();
	}
	
	private void importAction(){
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		String classname = (String) classCombo.getSelectedItem();
		JFrame pop = new JFrame();
		
		fc.setAcceptAllFileFilterUsed(false);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("xls", "xls");
		fc.setFileFilter(filter);
		filter = new FileNameExtensionFilter("xlsx", "xlsx");
		fc.addChoosableFileFilter(filter);
		
		int result = fc.showOpenDialog(pop);
		
		if ( result == JFileChooser.APPROVE_OPTION) {
			
			File file = fc.getSelectedFile();
			String filename = file.getName();
			ReadFile read = new ReadFile(file, filename, classname);
			read.setInputFile(filename);
			try {
				read.read();}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void exportAction()
	{
		String className = (String) classCombo.getSelectedItem();
		JPanel f = new ExportAttendance(className);
		contentPanel.removeAll();
		contentPanel.add(f, BorderLayout.CENTER);
		contentPanel.revalidate();
		contentPanel.repaint();
	}
	
	private void addClassAction()
	{
		JPanel addC = new CreateClassScreen(user, this);
		contentPanel.removeAll();
		contentPanel.add(addC, BorderLayout.CENTER);
		contentPanel.revalidate();
		contentPanel.repaint();
	}
	
	private void deleteClassAction()
	{
		JPanel deleteC = new DeleteClassScreen(user, this);
		contentPanel.removeAll();
		contentPanel.add(deleteC, BorderLayout.CENTER);
		contentPanel.revalidate();
		contentPanel.repaint();
	}
	
	private void addStudentAction()
	{
		JPanel addS = new AddStudentScreen(this);
		contentPanel.removeAll();
		contentPanel.add(addS, BorderLayout.CENTER);
		contentPanel.revalidate();
		contentPanel.repaint();
	}
	
	private void deleteStudentAction()
	{
		JPanel deleteS = new DeleteStudentScreen(this);
		contentPanel.removeAll();
		contentPanel.add(deleteS, BorderLayout.CENTER);
		contentPanel.revalidate();
		contentPanel.repaint();
	}
	
	public String getSelectedClass()
	{
		return classCombo.getSelectedItem().toString();
	}
}
