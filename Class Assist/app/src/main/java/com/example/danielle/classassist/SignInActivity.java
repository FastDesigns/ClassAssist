package com.example.danielle.classassist;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class SignInActivity extends AsyncTask<String, Void, String>
{
    private Login login;

    public SignInActivity(Login log)
    {
        this.login = log;
    }

    protected void onPreExecute()
    {

    }

    @Override
    protected String doInBackground(String... arg0)
    {
        try
        {
            String username = arg0[0];
            String password = arg0[1];
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
            try
            {
                int r = Integer.parseInt(sb.toString());
                if(r == 1)
                {
                    User.setUsername(username);
                    login.showCourses();
                }
            }
            catch(NumberFormatException e)
            {
                login.incorrectLogin();
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

    }
}