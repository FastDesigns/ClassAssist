package com.classassist.fastdesigns.logic;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * AddStudent.java is responsible for adding students to the database using php file on server.
 * @author Eddie Justice 
 *
 */
public class AddStudent extends Thread
{
	private String first, last, user, className;
	
	public AddStudent(String f, String l, String u, String c)
	{
		this.first = f;
		this.last = l;
		this.user = u;
		this.className = c;
		this.start();
	}
	
	/** 
	 * run() establishes a connection and writes the data to the database
	 * 
	 */
	public void run()
	{
		if(first.equals("") || last.equals("") || user.equals(""))
			failure("Please enter information into all fields");
		else
		{
			try
			{
				String link = "https://php.radford.edu/~team05/addstudent.php";
		        String data = URLEncoder.encode("first", "UTF-8") + "=" + URLEncoder.encode(first, "UTF-8");
		        data += "&" + URLEncoder.encode("last", "UTF-8") + "=" + URLEncoder.encode(last, "UTF-8");
		        data += "&" + URLEncoder.encode("user", "UTF-8") + "=" + URLEncoder.encode(user, "UTF-8");
		        data += "&" + URLEncoder.encode("class", "UTF-8") + "=" + URLEncoder.encode(className, "UTF-8");
		
		        URL url = new URL(link);
		        URLConnection conn = url.openConnection();
		
		        conn.setDoOutput(true);
		        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
		
		        wr.write(data);
		        wr.flush();
		
		        BufferedReader reader = new BufferedReader(new InputStreamReader((conn.getInputStream())));
		        
		        String line;
		
		        //read server response
		        if((line = reader.readLine()) != null)
		        {
		            failure(line);
		        }
		        else
		        {
		        	success();
		        }
		        this.interrupt();
		    }
		    catch(Exception e)
		    {
		        e.printStackTrace();
		        this.interrupt();
		    }
		}
	}
	
	private void failure(String msg)
	{
		new NewMessage(msg);
	}
	
	private void success()
	{
		new NewMessage("Added Student Successfully");
	}
}