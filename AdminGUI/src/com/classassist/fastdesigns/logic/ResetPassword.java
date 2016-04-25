package com.classassist.fastdesigns.logic;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Resets a users password
 * @author Eddie Justice
 *
 */
public class ResetPassword extends Thread
{
	private String user;
	
	/**
	 * Resets the given users password
	 * @param u Username
	 */
	public ResetPassword(String u)
	{
		this.user = u;
		this.start();
	}
	
	/**
	 * Run method
	 */
	public void run()
	{
		try
		{
			String link = "https://php.radford.edu/~team05/resetpassword.php";
	        String data = URLEncoder.encode("user", "UTF-8") + "=" + URLEncoder.encode(user, "UTF-8");
	
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
	
	/**
	 * Provides a message that the password reset has failed
	 * @param msg Failure message
	 */
	private void failure(String msg)
	{
		new NewMessage(msg);
	}
	
	/**
	 * Provides a message that the password reset was sucessful
	 */
	private void success()
	{
		new NewMessage("Reset Password Successfully");
	}
}