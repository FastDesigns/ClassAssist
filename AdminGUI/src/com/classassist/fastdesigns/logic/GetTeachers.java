package com.classassist.fastdesigns.logic;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import com.classassist.fastdesigns.gui.SelectClassScreen;

public class GetTeachers extends Thread
{
	private SelectClassScreen select;
	private String[] list;
	
	public GetTeachers(SelectClassScreen s)
	{
		this.select = s;
		this.start();
	}
	
	public void run()
	{
		try
		{
			String link = "https://php.radford.edu/~team05/teacherlist.php";
	        String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode("admin", "UTF-8");
	
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
	            sb.append(line);
	            list = sb.toString().split("&");
	        }
	        else
	        {
	        	String[] result = {"No Classes Found"};
	        	list = result;
	        }
	        select.setTeachers(list);
	        this.interrupt();
	    }
	    catch(Exception e)
	    {
	        e.printStackTrace();
	        this.interrupt();
	    }
	}
	
	public String[] getList()
	{
		return list;
	}
}