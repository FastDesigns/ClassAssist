package com.classassist.fastdesigns.logic;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class DeleteTeacher extends Thread
{
	private String name;
	
	public DeleteTeacher(String n)
	{
		this.name = n;
		this.start();
	}
	
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