package com.classassist.fastdesigns.teacher.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.classassist.fastdesigns.gui.MyButton;
import com.classassist.fastdesigns.gui.SelectClassScreen;
import com.classassist.fastdesigns.logic.DeleteClass;

public class DeleteClassScreen extends JPanel
{
	/**
	 * Create Student GUI version 1
	 */
	private static final long serialVersionUID = 1L;
	
	private String username; //teacher using program
	private SelectClassScreen select;
	
	//main panel
	private JPanel main = new JPanel();
	
	//fields panel
	private JPanel fieldsPanel = new JPanel();
	
	//Class Name
	private JPanel cNamePanel = new JPanel();
	private JComboBox<String> cName = new JComboBox<String>(new String[] {"Loading..."});
	
	//submit button fields
	private JPanel submitButtonPanel = new JPanel();
	private JButton submitButton = new MyButton("Submit");
	
	//confirmation dialog
	JFrame confirm;
	
	public DeleteClassScreen(String user, SelectClassScreen s)
	{
		getClassList();
		this.select = s;
		this.username = user;
		this.setLayout(new BorderLayout());
		setupComponents();
	}
	
	private void getClassList()
	{
		GetClasses getClasses = new GetClasses();
		getClasses.getClassList();
	}
	
	private void setupComponents()
	{
		//add panels
		this.add(main, BorderLayout.CENTER);
		main.setBackground(Color.darkGray);
		main.setLayout(new GridBagLayout());
		main.add(fieldsPanel);
		fieldsPanel.setLayout(new BoxLayout(fieldsPanel, BoxLayout.PAGE_AXIS));
		fieldsPanel.add(cNamePanel);
		cNamePanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		cNamePanel.setBackground(Color.darkGray);
		fieldsPanel.add(submitButtonPanel);
		submitButtonPanel.setBackground(Color.darkGray);
		
		//first name fields
		cNamePanel.add(cName);
		
		//submit fields
		submitButtonPanel.add(submitButton);
		
		submitButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				submitButton.setEnabled(false);
				confirm();
			}
		});
	}
	
	//confirmation dialog
	private void confirm()
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
		JLabel label2 = new JLabel(cName.getSelectedItem().toString());
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
		
		addActions(con, cancel);
	}
	
	//adds action listeners to confirm and cancel buttons
	private void addActions(JButton co, JButton ca)
	{
		co.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				submitButton.setEnabled(true);
				deleteClass();
				closeFrame();
			}
		});
		
		ca.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				submitButton.setEnabled(true);
				closeFrame();
			}
		});
	}
	
	private void deleteClass()
	{
		new DeleteClass(username, cName.getSelectedItem().toString());
		Thread thread = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
					Thread.sleep(3000);
					select.makeClasses();
				}
				catch(InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		});
		thread.start();
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
	
	public void setClasses(String[] l)
	{
		cName.removeAllItems();
		for(String item : l)
		{
			cName.addItem(item);
		}
	}
	
	public class GetClasses extends Thread
	{
		private String[] list;
			
		public void getClassList()
	    {
			start();
		}
		
		public void run()
		{
			try{
				String link = "https://php.radford.edu/~team05/getclasslistforteacher.php";
		        String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
		
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
		        	String[] result = {"No Classes Found"};
		        	list = result;
		        }
		        
		        setClasses(list);
		        
		    }
		    catch(Exception e)
		    {
		        e.printStackTrace();
		    }
//			return new String[] {""};
		}
		
	}
}