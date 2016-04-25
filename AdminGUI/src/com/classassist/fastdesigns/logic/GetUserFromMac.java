package com.classassist.fastdesigns.logic;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Gets the username of a student based on their MAC address
 * 
 * @author Alex Morris
 *
 */
public class GetUserFromMac {
	
	/**
	 * Queries the database for a students username given their MAC address
	 * @param args MAC Address
	 * @return Username of the student or the null string if the MAC address is not in the database
	 */
	public static String getUser (String args)
    {
        try
        {
            String link = "https://php.radford.edu/~team05/getUserFromMac.php";
            String data = URLEncoder.encode("mac", "UTF-8") + "=" + URLEncoder.encode(args, "UTF-8");
           
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
	            
	        }
	       return sb.toString();
	        
	    }
	    catch(Exception e)
	    {
	        e.printStackTrace();

	    }
		return "";
	}
}