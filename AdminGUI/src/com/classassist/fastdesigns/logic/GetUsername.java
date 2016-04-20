package com.classassist.fastdesigns.logic;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import com.classassist.fastdesigns.gui.SelectClassScreen;

public class GetUsername extends Thread
{
	private SelectClassScreen select;
	private String first, last;
	
	public GetUsername(SelectClassScreen s, String[] t)
	{
		this.select = s;
		this.first = t[0];
		this.last = t[1];
		this.start();
	}
	
	public void run()
	{
		try
		{
			String link = "https://php.radford.edu/~team05/getusername.php";
	        String data = URLEncoder.encode("first", "UTF-8") + "=" + URLEncoder.encode(first, "UTF-8");
	        data += "&" + URLEncoder.encode("last", "UTF-8") + "=" + URLEncoder.encode(last, "UTF-8");
	
	        URL url = new URL(link);
	        URLConnection conn = url.openConnection();
	
	        conn.setDoOutput(true);
	        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
	
	        wr.write(data);
	        wr.flush();
	
	        BufferedReader reader = new BufferedReader(new InputStreamReader((conn.getInputStream())));
	
	        StringBuilder sb = new StringBuilder();
	        String line, r;
	
	        //read server response
	        if((line = reader.readLine()) != null)
	        {
	            sb.append(line);
	            r = sb.toString();
	        }
	        else
	        {
	        	String result = "Username not found";
	        	r = result;
	        }
	        select.setUsername(r);
	        this.interrupt();
	    }
	    catch(Exception e)
	    {
	        e.printStackTrace();
	        this.interrupt();
	    }
	}
}