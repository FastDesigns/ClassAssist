package com.classassist.fastdesigns.logic;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import com.classassist.fastdesigns.teacher.gui.AddTeacherScreen;

/**
 * AddTeacher.java is used to add teachers to the database using php file on server
 * @author Eddie Justice
 *
 */
public class AddTeacher extends Thread
{
	private String first, last, user;
	private AddTeacherScreen add;
	
	/**
	 * AddTeacher() constructs the necessary variables to process and upload the 
	 * teacher to the database.
	 * @param f String for first name
	 * @param l String for last name
	 * @param u String for username
	 * @param a AddTeacherScreen to display the GUI for adding teachers
	 */
	public AddTeacher(String f, String l, String u, AddTeacherScreen a)
	{
		this.first = f;
		this.last = l;
		this.user = u;
		this.add = a;
		this.start();
	}
	
	/**
	 * run() is used to establish a connection to the database and upload the 
	 * teacher.
	 */
	public void run()
	{
		if(first.equals("") || last.equals("") || user.equals(""))
			message("Please enter information into all fields");
		else
		{
			try
			{
				String link = "https://php.radford.edu/~team05/addteacher.php";
		        String data = URLEncoder.encode("first", "UTF-8") + "=" + URLEncoder.encode(first, "UTF-8");
		        data += "&" + URLEncoder.encode("last", "UTF-8") + "=" + URLEncoder.encode(last, "UTF-8");
		        data += "&" + URLEncoder.encode("user", "UTF-8") + "=" + URLEncoder.encode(user, "UTF-8");
		
		        URL url = new URL(link);
		        URLConnection conn = url.openConnection();
		
		        conn.setDoOutput(true);
		        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
		
		        wr.write(data);
		        wr.flush();
		
		        BufferedReader reader = new BufferedReader(new InputStreamReader((conn.getInputStream())));
		        
		        String line = reader.readLine();
		        
		        message(line);
		        
		        this.interrupt();
		    }
		    catch(Exception e)
		    {
		        e.printStackTrace();
		        this.interrupt();
		    }
		}
	}
	
	private void message(String msg)
	{
		new NewMessage(msg);
	}
}