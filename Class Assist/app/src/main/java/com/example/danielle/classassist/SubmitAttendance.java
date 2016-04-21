package com.example.danielle.classassist;

import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SubmitAttendance extends AsyncTask<String, Void, String>
{
    private Context context;
    private StudentMenu student;
    private boolean studentCall = false;

    public SubmitAttendance(Context c)
    {
        context = c;
    }

    public SubmitAttendance(Context c, StudentMenu s)
    {
        student = s;
        studentCall = true;
    }

    protected void onPreExecute()
    {

    }

    @Override
    protected String doInBackground(String... arg0)
    {
        try
        {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            String d = dateFormat.format(date);
            String link = "https://php.radford.edu/~team05/studentattendance.php";
            String data = URLEncoder.encode("student", "UTF-8") + "=" + URLEncoder.encode(User.getUsername(), "UTF-8");
            data += "&" + URLEncoder.encode("class", "UTF-8") + "=" + URLEncoder.encode(SelectedClass.getSelectedClass(), "UTF-8");
            data += "&" + URLEncoder.encode("date", "UTF-8") + "=" + URLEncoder.encode(d, "UTF-8");
            URL url = new URL(link);
            URLConnection conn = url.openConnection();

            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

            wr.write(data);
            wr.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            StringBuilder sb = new StringBuilder();
            String line = reader.readLine();
            String problem = "Issue recording attendance";
            //read server response
            if(Integer.parseInt(line) == 1)
            {
                //success
                return "Recorded Attendance Successfully";
            }
            else
            {
                return problem;
            }
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
        new NewMessage(result, context);
        if(studentCall)
            student.bluetoothOff();
    }

}