import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.plaf.metal.MetalScrollBarUI;

import com.sun.javafx.geom.Rectangle;

public class SelectClassScreen extends JPanel
{
	// JButtons
	private JButton logoutBtn;
	private JButton attendBtn = new JButton("Take Attendance");//TeacherButton("attendance.png", this);
	private JButton importBtn = new TeacherButton("import.png", this);
	private JButton exportBtn = new TeacherButton("export.png", this);
	private JButton student = new TeacherButton("student.png", this);
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
	private String[] list;
	
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
	}
	
	private void makeButtons()
	{
		logoutBtn = new JButton("Logout");
		
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
		attendBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				attendanceAction(); // helper function for enterBtn
				}});
		importBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				importAction(); // helper function for exportBtn
				}});
		exportBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				exportAction(); // helper function for exportBtn			
				}});
	}
	
	private void makeLabel()
	{
		selectLabel = new JLabel("Select Class");
		selectLabel.setForeground(Color.white);
	}
	
	private void makeClasses()
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
		classesPanel.setBackground(new Color(78, 78, 78));
		this.add(buttonScroll, BorderLayout.WEST);
		buttonScroll.getVerticalScrollBar().setUI(new MyScrollBarUI());
		buttonScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		buttonScroll.getVerticalScrollBar().setUnitIncrement(10);
		buttonPanel.setBackground(Color.darkGray);
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		buttonPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.black));
		this.add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout());
		this.add(logoutPanel, BorderLayout.PAGE_END);
//		logoutPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.BLACK));
		logoutPanel.setBackground(new Color(78, 78, 78));
		logoutPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
	}
	
	private void addButtons()
	{
		mainFrame.setPreferredSize(new Dimension(1280, 720));
		classesPanel.add(selectLabel);
		classesPanel.add(classCombo);
		logoutPanel.add(logoutBtn);
		buttonPanel.add(student);
//		for(int i = 0; i < 20; i++)
//		{
//			student = new TeacherButton("student.png", this);
//			buttonPanel.add(student);
//		}
		buttonPanel.add(importBtn);
		buttonPanel.add(exportBtn);
		
		btnList.add(student);
//		btnList.add(enterBtn); prob won't need anymore
		btnList.add(importBtn);
		btnList.add(exportBtn);
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
		StartScreen start = new StartScreen(mainFrame);
	}
	
	private void attendanceAction()
	{
		getStudents();
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
		stud = new StudentsLayout(s, ad);
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
}
