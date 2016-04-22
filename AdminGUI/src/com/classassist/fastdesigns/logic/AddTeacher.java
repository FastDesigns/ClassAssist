package com.classassist.fastdesigns.logic;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import com.classassist.fastdesigns.teacher.gui.AddTeacherScreen;

public class AddTeacher extends Thread
{
	private String first, last, user;
	private AddTeacherScreen add;
	
	public AddTeacher(String f, String l, String u, AddTeacherScreen a)
	{
		this.first = f;
		this.last = l;
		this.user = u;
		this.add = a;
		this.start();
	}
	
	public void run()
	{
		if(first.equals("") || last.equals("") || user.equals(""))
			message("Please enter information into all fields");
		else
		{
			try
			{
				String link = "https://php.radford.edu/~team05/addteacher.php";
		        String data = URLEncoder.encode("first", "UTF-8") + "=" + URLEncoder.encode(first, "UTF-8");
		        data += "&" + URLEncoder.encode("last", "UTF-8") + "=" + URLEncoder.encode(last, "UTF-8");
		        data += "&" + URLEncoder.encode("user", "UTF-8") + "=" + URLEncoder.encode(user, "UTF-8");
		
		        URL url = new URL(link);
		        URLConnection conn = url.openConnection();
		
		        conn.setDoOutput(true);
		        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
		
		        wr.write(data);
		        wr.flush();
		
		        BufferedReader reader = new BufferedReader(new InputStreamReader((conn.getInputStream())));
		        
		        String line = reader.readLine();
		        
		        message(line);
		        
		        this.interrupt();
		    }
		    catch(Exception e)
		    {
		        e.printStackTrace();
		        this.interrupt();
		    }
		}
	}
	
	private void message(String msg)
	{
		new NewMessage(msg);
	}
}