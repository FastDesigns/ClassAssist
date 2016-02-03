import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

public class Classes extends JPanel
{
	/**
	 * MainPage Version 1
	 */
	private static final long serialVersionUID = 1L;
	
	//parent class
	private View view;
	
	//main panel
	private JPanel main = new JPanel();

	//Admin Label
	private JPanel adminPanel = new JPanel();
	private JPanel adminPanel2 = new JPanel();
	private JPanel adminPanel3 = new JPanel();
	private JLabel admin = new JLabel("Admin");
	private JButton addTeacher;
	
	//Teacher dropdown box
	private String[] loading = {"Loading..."};
	private JComboBox teacherList = new JComboBox(loading);
	
	//List of classes (once teacher has been selected)
	private JPanel classesPanel = new JPanel();
	private String[] classList = {"Select a Teacher"};
	private JList classes = new JList(classList);
	private JScrollPane classPane = new JScrollPane(classes);
	
	//various edit class buttons
	private JPanel centerPanel = new JPanel();
	private JPanel buttonPanel = new JPanel();
	private JButton editClass = new JButton("Edit Class");
	private JButton addClass;
	private JButton importClass = new JButton("Import Classes");
	private JButton deleteClass = new JButton("Delete Class");
	private JButton createStudent;
	
	//logout
	private JPanel logoutPanel = new JPanel();
	private JButton logout = new LogoutButton("Log Out");
	
	public Classes(View v)
	{
		this.view = v;
		this.setLayout(new BorderLayout());
		new GetTeachers(this);
		setupComponents();
	}
	
	public JPanel getView()
	{
		return this;
	}
	
	private void setupComponents()
	{
		//add panels
		this.add(main);
		main.setLayout(new BorderLayout());
		main.add(adminPanel, BorderLayout.PAGE_START);
		main.add(centerPanel, BorderLayout.CENTER);
		main.add(logoutPanel, BorderLayout.PAGE_END);
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		
		//setup admin panel fields
		adminPanel.setLayout(new BoxLayout(adminPanel, BoxLayout.Y_AXIS));
		adminPanel.add(adminPanel3);
		adminPanel3.add(admin);
		adminPanel3.setLayout(new FlowLayout(FlowLayout.LEFT));
		admin.setFont(new Font("Serif", Font.PLAIN, 30));
		adminPanel.add(adminPanel2);
		adminPanel2.add(teacherList);
		addTeacher = new AddTeacherButton("Add Teacher", view);
		adminPanel2.setLayout(new FlowLayout(FlowLayout.LEFT));
		adminPanel2.add(addTeacher);
		createStudent = new CreateStudentButton("Create Student", view);
		adminPanel2.add(createStudent);
		
		//setup classes panel field
		centerPanel.add(classesPanel);
		classesPanel.setLayout(new BorderLayout());
		classesPanel.setBorder(new EmptyBorder(0, 30, 0, 30));
		classesPanel.add(classPane, BorderLayout.PAGE_START);
		
		//setup button panel fields
		classesPanel.add(buttonPanel, BorderLayout.CENTER);
		buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
//		buttonPanel.setBorder(new EmptyBorder(0, 20, 0, 30));
		buttonPanel.add(editClass);
		addClass = new CreateClassButton("Create Class", view);
		buttonPanel.add(addClass);
		buttonPanel.add(importClass);
		buttonPanel.add(deleteClass);
		
		//logout fields
		logoutPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		logoutPanel.add(logout);
	}
	
	public void setTeachers(String[] list)
	{
		ArrayList<String> l = new ArrayList<String>();
		l.add("Select Teacher");
		for(int i = 0; i < list.length; i++)
		{
			l.add(list[i]);
		}
		String[] leest = new String[l.size()];
		leest = l.toArray(leest);
		adminPanel2.remove(teacherList);
		adminPanel2.remove(addTeacher);
		adminPanel2.remove(createStudent);
		teacherList = new TList(leest, this);
		adminPanel2.add(teacherList);
		adminPanel2.add(new AddTeacherButton("Add Teacher", view));
		adminPanel2.add(createStudent);
		view.refresh();
	}
	
	public void setClasses(String[] l)
	{
		classesPanel.remove(classPane);
		classPane = new JScrollPane(new JList(l));
		classesPanel.add(classPane, BorderLayout.PAGE_START);
		view.refresh();
	}
}