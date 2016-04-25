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

/**
 * Marks a student as Present
 * @author Alex Morris
 *
 */
public class SetAttendance {
	/**
	 * Sets the attendance of the given student as present
	 * @param args[0] Student name
	 * @param args[1] Class name
	 * @return True if attendance set or False is attendance not set
	 */
	public static boolean setAttendance (String[] args)
    {
        try
        {
    		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    		Date date = new Date();
            String d = dateFormat.format(date);
            String student = args[0];
            GetStudentUsername get = new GetStudentUsername(student.split(" "));
            String username = get.getUser();
            if(get.getUser().equals(""))
            	username = args[0];
        	String classname = args[1];
        	
            String link = "https://php.radford.edu/~team05/studentattendance.php";
            String data = URLEncoder.encode("student", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
            data += "&" + URLEncoder.encode("class", "UTF-8") + "=" + URLEncoder.encode(classname, "UTF-8");
            data += "&" + URLEncoder.encode("date", "UTF-8") + "=" + URLEncoder.encode(d, "UTF-8");
           
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
          //read server response
            while((line = reader.readLine()) != null)
            {
                sb.append(line);
                break;
            }
            try
            {
                int r = Integer.parseInt(sb.toString());
                if(r == 1)
                {
                   return true;
                }
            }
            catch(NumberFormatException e)
            {
               return false;
            }
	     
	        
	    }
	    catch(Exception e)
	    {
	        e.printStackTrace();

	    }
		return false;
	}
}