package com.classassist.fastdesigns.logic;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class TakingAttendance extends Thread
{
	private String user, cl;
	
	public TakingAttendance(String u, String c)
	{
		this.user = u;
		this.cl = c;
		this.start();
	}
	
	public void run()
	{
		try
		{
			String link = "https://php.radford.edu/~team05/takingattendance.php";
	        String data = URLEncoder.encode("user", "UTF-8") + "=" + URLEncoder.encode(user, "UTF-8");
	        data += "&" + URLEncoder.encode("class", "UTF-8") + "=" + URLEncoder.encode(cl, "UTF-8");
	        
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