package com.classassist.fastdesigns.logic;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * DeleteClass.java is used to remove a class from the database.
 * @author Eddie Justice
 *
 */
public class DeleteClass extends Thread
{
	private String user, className;
	/**
	 * DeleteClass() constructs the necessary variables to process removing a 
	 * class from the database.
	 * @param u String user name (teacher)
	 * @param c String class name
	 */
	public DeleteClass(String u, String c)
	{
		this.user = u;
		this.className = c;
		this.start();
	}
	
	/**
	 * run() establishes a connection to the database and handles removing a 
	 * class from a teacher
	 */
	public void run()
	{
		if(className.equals(""))
			failure("Please enter a class name");
		else
		{
			try
			{
				String link = "https://php.radford.edu/~team05/deleteclass.php";
		        String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(user, "UTF-8");
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
		new NewMessage("Deleted Class Successfully");
	}
}