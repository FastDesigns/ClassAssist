package com.classassist.fastdesigns.logic;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * ChangePassword.java is used to change passwords and store new password in the
 * database using the php file on the server.
 * @author Eddie Justice
 *
 */
public class ChangePassword extends Thread
{
	private String user, oldPass, newPass;
	
	/**
	 * ChangePassword() constructs the necessary variables to process changing a 
	 * password
	 * @param u String username
	 * @param o String old password
	 * @param n String new password
	 */
	public ChangePassword(String u, String o, String n)
	{
		this.user = u;
		this.oldPass = o;
		this.newPass = n;
		this.start();
	}
	
	/**
	 * run() establishes a connection to the database and stores the new password.
	 */
	public void run()
	{
		try
		{
			String link = "https://php.radford.edu/~team05/changepassword.php";
	        String data = URLEncoder.encode("user", "UTF-8") + "=" + URLEncoder.encode(user, "UTF-8");
	        data += "&" + URLEncoder.encode("oldPass", "UTF-8") + "=" + URLEncoder.encode(oldPass, "UTF-8");
	        data += "&" + URLEncoder.encode("newPass", "UTF-8") + "=" + URLEncoder.encode(newPass, "UTF-8");
	
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
	
	private void failure(String msg)
	{
		new NewMessage(msg);
	}
	
	private void success()
	{
		new NewMessage("Changed Password Successfully");
	}
}