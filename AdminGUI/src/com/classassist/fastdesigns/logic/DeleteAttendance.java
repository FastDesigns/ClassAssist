package com.classassist.fastdesigns.logic;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class DeleteAttendance extends Thread
{
	private String username, cl, date;
	public DeleteAttendance(String[] args)
	{
		this.username = args[0];
		this.cl = args[1];
		this.date = args[2];
		start();
	}
	
	public void run()
	{
		try
		{
			String link = "https://php.radford.edu/~team05/deleteattendance.php";
	        String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
	        data += "&" + URLEncoder.encode("class", "UTF-8") + "=" + URLEncoder.encode(cl, "UTF-8");
	        data += "&" + URLEncoder.encode("date", "UTF-8") + "=" + URLEncoder.encode(date, "UTF-8");
	
	        URL url = new URL(link);
	        URLConnection conn = url.openConnection();
	
	        conn.setDoOutput(true);
	        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
	
	        wr.write(data);
	        wr.flush();
	
	        BufferedReader reader = new BufferedReader(new InputStreamReader((conn.getInputStream())));
	        
	        String line = reader.readLine();
	
	        //read server response
	        if(line == null)
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