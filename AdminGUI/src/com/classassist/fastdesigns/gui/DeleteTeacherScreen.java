package com.classassist.fastdesigns.gui;

import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.border.EmptyBorder;

import com.classassist.fastdesigns.gui.MyButton;
import com.classassist.fastdesigns.gui.MyScrollBarUI;
import com.classassist.fastdesigns.gui.SelectClassScreen;
import com.classassist.fastdesigns.logic.DeleteTeacher;
import com.classassist.fastdesigns.logic.FrameSize;
import com.classassist.fastdesigns.logic.ItemListRenderer;

/**
 * The screen of the GUI responcible for deleting teachers.
 * @author djust
 * @version 1.0
 */
public class DeleteTeacherScreen extends JPanel
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
	
	//content list of teachers
	private JList<String> teachers = new JList<String>(new String[]{"Loading..."});
	private JScrollPane teachersScroll = new JScrollPane(teachers);
	
	/**
	 * Initiates the screen with the currently selected class.
	 * @param s The SelectClassScreen of the program
	 */
	public DeleteTeacherScreen(SelectClassScreen s)
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
		main.add(teachersScroll, BorderLayout.CENTER);
		this.setBackground(Color.darkGray);
		teachers.setCellRenderer(new ItemListRenderer(true));
		teachersScroll.getVerticalScrollBar().setUI(new MyScrollBarUI());
		teachers.setBorder(BorderFactory.createLineBorder(Color.black));
		adapter = new MouseAdapter()
		{
			Point p;
			int index;
			public void mouseMoved(MouseEvent e)
			{
				p = new Point(e.getX(), e.getY());
				index = teachers.locationToIndex(p);
				teachers.setSelectedIndex(index);
			}
			
			public void mouseReleased(MouseEvent e)
			{
				teachers.removeMouseListener(adapter);
				teachers.removeMouseMotionListener(adapter);
				confirm(teachers.getSelectedValue());
			}
		};
		teachers.addMouseMotionListener(adapter);
		teachers.addMouseListener(adapter);
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
		
		JLabel label = new JLabel("Deleting a teacher will also delete all associated classes. Are you sure you want to delete:");
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
				teachers.addMouseMotionListener(adapter);
				teachers.addMouseListener(adapter);
				deleteTeacher(stude);
				closeFrame();
				createList();
				Thread thread = new Thread(new Runnable()
				{
					@Override
					public void run()
					{
						try
						{
							Thread.sleep(3000);
							select.removeTeacher(stude);
						}
						catch(InterruptedException e)
						{
							e.printStackTrace();
						}
					}
				});
				thread.start();
			}
		});
		
		ca.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				teachers.addMouseMotionListener(adapter);
				teachers.addMouseListener(adapter);
				closeFrame();
			}
		});
	}
	
	private void deleteTeacher(String s)
	{
		new DeleteTeacher(s);
	}
	
	private void closeFrame()
	{
		confirm.dispose();
	}
	
	//centers confirmation dialog on mouses current position
	private void centerConfirm(JFrame m)
	{
		m.setLocation(FrameSize.getX() + (FrameSize.getWidth() / 2) - (m.getWidth() / 2), FrameSize.getY() + (FrameSize.getHeight() / 2) - (m.getHeight() / 2));
	}
	
	private void createList()
	{
		new GetTeachers();
	}
	
	/**
	 * Sets the list of teachers.
	 * @param l
	 */
	public void makeTeachers(String[] l)
	{
		teachers.setListData(l);
		refresh();
	}
	
	/**
	 * A new thread which queries the database and deletes the selected teacher.
	 * @author FastDesigns
	 * @version 1.0
	 */
	public class GetTeachers extends Thread
	{
		private String[] list;
		
		/**
		 * Initiates the threa on creation.
		 */
		public GetTeachers()
		{
			this.start();
		}
		
		/**
		 * Queries the database and remove the selected teacher.
		 */
		public void run()
		{
			try
			{
				String link = "https://php.radford.edu/~team05/teacherlist.php";
		        String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode("admin", "UTF-8");
		
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
		            list = sb.toString().split("&");
		        }
		        else
		        {
		        	String[] result = {"No Teachers Found"};
		        	list = result;
		        }
		        makeTeachers(list);
		        this.interrupt();
		    }
		    catch(Exception e)
		    {
		        e.printStackTrace();
		        this.interrupt();
		    }
		}
	}
	
	private void refresh()
	{
		repaint();
	}
}
