package com.classassist.fastdesigns.logic;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
/**
 * DeleteTeacher.java is used to remove a teacher from the database.
 * @author Eddie Justice
 *
 */
public class DeleteTeacher extends Thread
{
	private String name;
	/**
	 * DeleteTeacher() is used to construct the necessary variables needed 
	 * to remove a teacher from teh database.
	 * @param n
	 */
	public DeleteTeacher(String n)
	{
		this.name = n;
		this.start();
	}
	/**
	 * run() is used to connect to the database and remove a teacher.
	 */
	public void run()
	{
		try
		{
			String[] fl = name.split(" ");
			String fName = fl[0];
			String lName = fl[1];
			String link = "https://php.radford.edu/~team05/deleteteacher.php";
	        String data = URLEncoder.encode("first", "UTF-8") + "=" + URLEncoder.encode(fName, "UTF-8");
	        data += "&" + URLEncoder.encode("last", "UTF-8") + "=" + URLEncoder.encode(lName, "UTF-8");
	
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
	
	private void failure(String msg)
	{
		new NewMessage("More than one Teacher was found with that name. Please contact the Database Administrator.");
	}
	
	private void success()
	{
		new NewMessage("Removed Teacher Successfully");
	}
}