package com.example.danielle.classassist;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class CheckAttendance extends AsyncTask<String, Void, String>
{
    private StudentMenu student;

    public CheckAttendance(StudentMenu s)
    {
        student = s;
    }
    protected void onPreExecute()
    {

    }

    @Override
    protected String doInBackground(String... arg0)
    {
        try
        {
            String link = "https://php.radford.edu/~team05/checktakingattendance.php";
            String data = URLEncoder.encode("class", "UTF-8") + "=" + URLEncoder.encode(arg0[0], "UTF-8");

            URL url = new URL(link);
            URLConnection conn = url.openConnection();

            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

            wr.write(data);
            wr.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String line = reader.readLine();
            return line;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return "Problem checking status of attendance taking";
        }
    }

    @Override
    protected void onPostExecute(String result)
    {

        //read server response
        if(result != null)
        {
            student.notBroadcasting();
        }
        else
        {
            student.enableButton();
        }
    }
}