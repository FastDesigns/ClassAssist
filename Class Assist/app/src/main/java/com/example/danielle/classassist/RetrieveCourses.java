package com.example.danielle.classassist;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class RetrieveCourses extends AsyncTask<String, Void, String>
{
    private SVCourses svc;

    public RetrieveCourses(SVCourses s)
    {
        this.svc = s;
    }

    protected void onPreExecute()
    {

    }

    @Override
    protected String doInBackground(String... arg0)
    {
        try
        {
            String link = "https://php.radford.edu/~team05/classes.php";
            String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(User.getUsername(), "UTF-8");
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

    @Override
    protected void onPostExecute(String result)
    {
        svc.setResult(result);
        svc.split();
    }

}