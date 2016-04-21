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

import com.classassist.fastdesigns.teacher.gui.StatusIndicator;

public class UpdateStatus
{
	private String name, cl;
	private StatusIndicator status;
	
	public UpdateStatus(String n, StatusIndicator s, String c)
	{
		this.name = n;
		this.status = s;
		this.cl = c;
		this.start();
	}
	
	public void start()
	{
		try
		{
			String[] n = name.split(" ");
			String fName = n[0];
			String lName = n[1];
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			String d = dateFormat.format(date);
			String link = "https://php.radford.edu/~team05/checkattendance.php";
	        String data = URLEncoder.encode("first", "UTF-8") + "=" + URLEncoder.encode(fName, "UTF-8");
	        data += "&" + URLEncoder.encode("last", "UTF-8") + "=" + URLEncoder.encode(lName, "UTF-8");
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
	        if(line != null && line.equals(d))
	        {
	        	status.setStatus(true);
	        }
	        else
	        {
				status.setStatus(false);
			}
	    }
	    catch(Exception e)
	    {
	        //no problem, just wasn't loaded yet
	    }
	}
}