package com.classassist.fastdesigns.logic;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import com.classassist.fastdesigns.gui.SelectClassScreen;
/**
 * GetClassListForTeacher.java is used to connect the database and get the list
 * of classes that a teacher has.
 * @author Alex Morris
 *
 */
public class GetClassListForTeacher
{
	private static String[] list;
	private static SelectClassScreen select;
	/**
	 * GetClassListForTeacher() is used to construct the variable used to 
	 * retrieving the class list for a specified teacher.
	 * @param s SelectClassScreen to display information in
	 */
	public GetClassListForTeacher(SelectClassScreen s)
	{
		select = s;
	}
	/**
	 * getClassList() is used to add a thread to the getClasses method.
	 * @param args String[0] user name
	 */
	public static void getClassList (final String[] args)
    {
		Thread thread = new Thread()
		{
			public void run()
			{
				getClasses(args);
			}
		};
		
		thread.start();
	}
	
	private static void getClasses(String[] args)
	{
		try{
			String uname = args[0];
			String link = "https://php.radford.edu/~team05/getclasslistforteacher.php";
	        String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(uname, "UTF-8");
	
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
	        
	        select.setClasses(list);
	        
	    }
	    catch(Exception e)
	    {
	        e.printStackTrace();
	    }
//		return new String[] {""};
	}
	
}