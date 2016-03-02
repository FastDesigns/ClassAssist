package com.example.danielle.classassist;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class AddMacAddress extends AsyncTask<String, Void, String>
{
    private StudentMenu student;

    public AddMacAddress(StudentMenu s)
    {
        this.student = s;
    }

    protected void onPreExecute()
    {

    }

    @Override
    protected String doInBackground(String... arg0)
    {
        try
        {
            String mac = normalizeMac(arg0[0]);
            String link = "https://php.radford.edu/~team05/addmac/addmac.php";
            String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(User.getUsername(), "UTF-8");
            data += "&" + URLEncoder.encode("mac", "UTF-8") + "=" + URLEncoder.encode(mac, "UTF-8");
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
            return "Problem connecting to database";
        }
    }

    //removes colons from mac address string
    private String normalizeMac(String mac)
    {
        String result = "";
        String[] split = mac.split(":");
        for(int i = 0; i < split.length; i++)
        {
            result += split[i];
        }
        return result;
    }

    @Override
    protected void onPostExecute(String result)
    {
        if(result.equals("false"))
            student.failedToAddMac("Could not register device to user. Please contact teacher.");
    }

}