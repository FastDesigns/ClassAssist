package com.classassist.fastdesigns.logic;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import com.classassist.fastdesigns.gui.SelectClassScreen;
/** 
 * GetStudentList.java is used to get the student list from the database.
 * @author Alex Morris
 *
 */
public class GetStudentList extends Thread
{
	private SelectClassScreen screen;
	private String clas;
	/**
	 * GetStudentList() constructs the necessary variables to process getting the
	 * student list.
	 * @param s SelectClassScreen to display information in.
	 * @param cl String class name
	 */
	public GetStudentList(SelectClassScreen s, String cl)
	{
		this.screen = s;
		clas = cl;
		this.start();
	}
	/**
	 * run() connects to the database and get the student list.
	 */
	public void run()
	{
		try
		{
			String[] students;
			String link = "https://php.radford.edu/~team05/getclassstudents.php";
	        String data = URLEncoder.encode("class", "UTF-8") + "=" + URLEncoder.encode(clas, "UTF-8");
	        
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
	            students = sb.toString().split("&");
	        }
	        else
	        {
	        	students = new String[] {"No Students Found"};
	        }
	        
	        setStudents(students);
	        this.interrupt();
	    }
	    catch(Exception e)
	    {
	        e.printStackTrace();
	        this.interrupt();
	    }
	}
	
	private void setStudents(String[] l)
	{
		screen.setStudents(l);
	}
}