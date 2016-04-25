package com.classassist.fastdesigns.logic;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
/**
 * DesktopSignInActivity.java is used to connect to the databse and allow
 * a user to log into the app.
 * @author Eddie Justice
 *
 */
public class DesktopSignInActivity{
	/**
	 * logIn() is used to connect to the database and mark the user as 
	 * logged in.
	 * @param args String[0] = user name
	 * 			   String[1] = password
	 * @return boolean for whether login was successful or not
	 */
	public static boolean logIn (String[] args)
    {
        try
        {
            String username = args[0];
            String password = args[1];
            String link = "https://php.radford.edu/~team05/login.php";
            String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
            data += "&" + URLEncoder.encode("pwd", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");

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
            while((line = reader.readLine()) != null)
            {
                sb.append(line);
                break;
            }
            System.out.println("SFSDFSDF" + sb.toString());
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
//            return sb.toString();
            System.out.println(sb.toString());
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.out.println("db error");
            //return "Problem connecting to database";
        }
		return false;
    }

}