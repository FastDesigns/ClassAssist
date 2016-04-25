package com.classassist.fastdesigns.logic;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * CreateClass.java is used to create a new class and record it in the database
 * @author Eddie Justice
 *
 */
public class CreateClass extends Thread
{
	private String user, className;
	
	/**
	 * CreateClass() constructs the necessary variables to create a new class 
	 * in the database.
	 * @param u String user name
	 * @param c String class name
	 */
	public CreateClass(String u, String c)
	{
		this.user = u;
		this.className = c;
		this.start();
	}
	
	/**
	 * run() establishes a connection to the database, and handles adding a
	 * class.
	 */
	public void run()
	{
		if(className.equals(""))
			failure("Please enter a class name");
		else
		{
			try
			{
				String link = "https://php.radford.edu/~team05/addclass.php";
		        String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(user, "UTF-8");
		        data += "&" + URLEncoder.encode("class", "UTF-8") + "=" + URLEncoder.encode(className, "UTF-8");
		
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
		new NewMessage("Created Class Successfully");
	}
}