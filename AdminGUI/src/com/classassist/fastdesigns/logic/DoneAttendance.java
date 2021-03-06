package com.classassist.fastdesigns.logic;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
/**
 * DoneAttendance.java is used to mark in the database the the teacher 
 * is no longer taking attendance.
 * @author Eddie Justice
 *
 */
public class DoneAttendance extends Thread
{
	private String user, cl;
	
	/**
	 * DoneAttendance() is used to contruct the necessary variables used to
	 * mark that the teacher is no longer taking attendance in the database.
	 * @param u String user (teacher)
	 * @param c String class name
	 */
	public DoneAttendance(String u, String c)
	{
		this.user = u;
		this.cl = c;
		this.start();
	}
	/**
	 * run() connects to the database and marks the teachers as not taking 
	 * attendance
	 */
	public void run()
	{
		try
		{
			String link = "https://php.radford.edu/~team05/doneattendance.php";
	        String data = URLEncoder.encode("user", "UTF-8") + "=" + URLEncoder.encode(user, "UTF-8");
	        data += "&" + URLEncoder.encode("class", "UTF-8") + "=" + URLEncoder.encode(cl, "UTF-8");
	        System.out.println("called");
	        URL url = new URL(link);
	        URLConnection conn = url.openConnection();
	        
	        conn.setDoOutput(true);
	        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
	        
	        wr.write(data);
	        wr.flush();
	        
	        BufferedReader reader = new BufferedReader(new InputStreamReader((conn.getInputStream())));
	        
	        String line = reader.readLine();
	
	        //read server response
	        if(line != null)
	        {
	            new NewMessage(line);
	        }
	    }
	    catch(Exception e)
	    {
	        e.printStackTrace();
	    }
	}
}