package com.classassist.fastdesigns.teacher.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.classassist.fastdesigns.gui.MyButton;
import com.classassist.fastdesigns.gui.MyScrollBarUI;
import com.classassist.fastdesigns.gui.SelectClassScreen;
import com.classassist.fastdesigns.logic.AddStudent;
import com.classassist.fastdesigns.logic.DeleteStudent;
import com.classassist.fastdesigns.logic.GetAttendance;
import com.classassist.fastdesigns.logic.ItemListRenderer;

/**
 * Delete students GUI
 * @author djust
 *
 */
public class DeleteStudentScreen extends JPanel
{
	/**
	 * Delete Student GUI version 1
	 */
	private static final long serialVersionUID = 1L;
	
	private SelectClassScreen select;
	private JFrame confirm;
	private MouseAdapter adapter;
	
	//main panel
	private JPanel main = new JPanel();
	
	//content list of students
	private JList<String> students = new JList<String>(new String[]{"Loading..."});
	private JScrollPane studentsScroll = new JScrollPane(students);
	
	public DeleteStudentScreen(SelectClassScreen s)
	{
		this.select = s;
		this.setLayout(new BorderLayout());
		setupComponents();
	}
	
	private void setupComponents()
	{
		//add panels
		addPanels();
	}
	
	private void addPanels()
	{
		
		this.add(main, BorderLayout.CENTER);
		this.setBorder(new EmptyBorder(20, 20, 20, 20));
		main.setBackground(Color.darkGray);
		main.setLayout(new BorderLayout());
		main.add(studentsScroll, BorderLayout.CENTER);
		this.setBackground(Color.darkGray);
		students.setCellRenderer(new ItemListRenderer(true));
		studentsScroll.getVerticalScrollBar().setUI(new MyScrollBarUI());
		students.setBorder(BorderFactory.createLineBorder(Color.black));
		adapter = new MouseAdapter()
		{
			Point p;
			int index;
			public void mouseMoved(MouseEvent e)
			{
				p = new Point(e.getX(), e.getY());
				index = students.locationToIndex(p);
				students.setSelectedIndex(index);
			}
			
			public void mouseReleased(MouseEvent e)
			{
				students.removeMouseListener(adapter);
				students.removeMouseMotionListener(adapter);
				confirm(students.getSelectedValue());
			}
		};
		students.addMouseMotionListener(adapter);
		students.addMouseListener(adapter);
		createList();
	}
	
	//confirmation dialog
	private void confirm(String s)
	{
		confirm = new JFrame("Confirm");
		JPanel cPanel = new JPanel(); //holds the content
		
		confirm.add(cPanel);
		cPanel.setBackground(Color.darkGray); //color scheme
		cPanel.setBorder(new EmptyBorder(30, 30, 30, 30));
		cPanel.setLayout(new BoxLayout(cPanel, BoxLayout.Y_AXIS));

		JPanel lPane = new JPanel();
		JPanel lPane2 = new JPanel();
		lPane.setBackground(Color.darkGray);
		lPane2.setBackground(Color.darkGray);
		
		JLabel label = new JLabel("Are you sure you want to delete:");
		JLabel label2 = new JLabel(s);
		label.setForeground(Color.white);
		label2.setForeground(Color.white);
		
		lPane.add(label);
		lPane2.add(label2);
		
		JButton con = new MyButton("Confirm");
		JButton cancel = new MyButton("Cancel");
		JPanel actionPane = new JPanel(); //holds confirm and cancel buttons
		actionPane.setBackground(Color.darkGray);
		actionPane.add(con);
		actionPane.add(cancel);
		
		cPanel.add(lPane);
		cPanel.add(lPane2);
		cPanel.add(actionPane);
		
		confirm.pack();
		confirm.setVisible(true);
		centerConfirm(confirm);
		
		addActions(con, cancel, s);
	}
	
	//adds action listeners to confirm and cancel buttons
	private void addActions(JButton co, JButton ca, final String stude)
	{
		co.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				students.addMouseMotionListener(adapter);
				students.addMouseListener(adapter);
				deleteStudent(stude);
				closeFrame();
				createList();
			}
		});
		
		ca.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				students.addMouseMotionListener(adapter);
				students.addMouseListener(adapter);
				closeFrame();
			}
		});
	}
	
	private void deleteStudent(String s)
	{
		new DeleteStudent(s, select.getSelectedClass());
	}
	
	private void closeFrame()
	{
		confirm.dispose();
	}
	
	//centers confirmation dialog on mouses current position
	private void centerConfirm(JFrame m)
	{
		Point p = MouseInfo.getPointerInfo().getLocation();
		int x = p.x - (m.getWidth() / 2);
		int y = p.y - (m.getHeight() / 2);
		m.setLocation(new Point(x, y));
	}
	
	private void createList()
	{
		new GetClasses();
	}
	
	public void makeStudents(String[] l)
	{
		students.setListData(l);
		refresh();
	}
	
	public class GetClasses extends Thread
	{
		private String clas;
		
		public GetClasses()
		{
			clas = select.getSelectedClass();
			this.start();
		}
		
		public void run()
		{
			try
			{
				String[] students;
				String link = "https://php.radford.edu/~team05/getclassstudents.php";
		        String data = URLEncoder.encode("class", "UTF-8") + "=" + URLEncoder.encode(clas, "UTF-8");
		        
		        URL url = new URL(link);
		        URLConnection conn = url.openConnection();
		        
		        conn.setDoOutput(true);
		        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
		
		        wr.write(data);
		        wr.flush();
		
		        BufferedReader reader = new BufferedReader(new InputStreamReader((conn.getInputStream())));
		
		        StringBuilder sb = new StringBuilder();
		        String line;
		
		        //read server response
		        if((line = reader.readLine()) != null)
		        {
		            sb.append(line);
		            students = sb.toString().split("&");
		        }
		        else
		        {
		        	students = new String[] {"No Students Found"};
		        }
		        
		        setStudents(students);
		        this.interrupt();
		    }
		    catch(Exception e)
		    {
		        e.printStackTrace();
		        this.interrupt();
		    }
		}
		
		private void setStudents(String[] l)
		{
			makeStudents(l);
		}
	}
	
	private void refresh()
	{
		repaint();
	}
}