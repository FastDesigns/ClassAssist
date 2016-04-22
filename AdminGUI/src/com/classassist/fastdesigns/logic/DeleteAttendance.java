package com.classassist.fastdesigns.logic;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DeleteAttendance extends Thread
{
	private String user, cl;
	public DeleteAttendance(String[] args)
	{
		this.user = args[0];
		this.cl = args[1];
		start();
	}
	
	public void run()
	{
		try
		{
    		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    		Date date = new Date();
            String d = dateFormat.format(date);
            String student = user;
            GetStudentUsername get = new GetStudentUsername(student.split(" "));
            String username = get.getUser();
			String link = "https://php.radford.edu/~team05/deleteattendance.php";
	        String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
	        data += "&" + URLEncoder.encode("class", "UTF-8") + "=" + URLEncoder.encode(cl, "UTF-8");
	        data += "&" + URLEncoder.encode("date", "UTF-8") + "=" + URLEncoder.encode(d, "UTF-8");
	
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