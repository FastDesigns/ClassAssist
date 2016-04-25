package com.classassist.fastdesigns.logic;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Retrieves username for a student from the database
 * @author Alex Morris
 *
 */
public class GetStudentUsername
{
	private String first, last, username;
	
	
	/**
	 * Parses the students name into a first and last name
	 * @param t First and Last name seperated by a space
	 */
	public GetStudentUsername(String[] t)
	{
		if(t.length==2){
		this.first = t[0];
		this.last = t[1];
		}
	}
	
	/**
	 * Queries the database for the given students username
	 * @return Students username
	 */
	public String getUser()
	{
		try
		{
			String link = "https://php.radford.edu/~team05/getstudentusername.php";
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
	        return r;
	    }
	    catch(Exception e)
	    {
	        String result = "Username not found";
	    }
		return "";
	}
}