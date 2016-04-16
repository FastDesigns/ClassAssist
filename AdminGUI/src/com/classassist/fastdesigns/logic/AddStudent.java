package com.classassist.fastdesigns.logic;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class AddStudent extends Thread
{
	private String first, last, id, user, className;
	
	public AddStudent(String f, String l, String i, String u, String c)
	{
		this.first = f;
		this.last = l;
		this.id = i;
		this.user = u;
		this.className = c;
		this.start();
	}
	
	public void run()
	{
		if(first.equals("") || last.equals("") || id.equals("") || user.equals(""))
			failure("Please enter information into all fields");
		else if(isNotNum(id))
			failure("Please enter a number for student ID");
		else
		{
			try
			{
				String link = "https://php.radford.edu/~team05/addstudent.php";
		        String data = URLEncoder.encode("first", "UTF-8") + "=" + URLEncoder.encode(first, "UTF-8");
		        data += "&" + URLEncoder.encode("last", "UTF-8") + "=" + URLEncoder.encode(last, "UTF-8");
		        data += "&" + URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");
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
	
	private boolean isNotNum(String check)
	{
		try
		{
			Integer.parseInt(check);
		}
		catch(NumberFormatException e)
		{
			return true;
		}
		
		return false;
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