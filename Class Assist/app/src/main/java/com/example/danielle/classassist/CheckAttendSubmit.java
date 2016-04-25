package com.example.danielle.classassist;

import android.app.Dialog;
import android.content.Context;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CheckAttendSubmit
{
    private Context context;
    private StudentMenu student;

    public CheckAttendSubmit(StudentMenu s)
    {
        this.context = s;
        this.student = s;
    }

    public void execute(String... arg0)
    {
        try
        {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            String d = dateFormat.format(date);

            String link = "https://php.radford.edu/~team05/checkstudentattendance.php";
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

            String line = reader.readLine();

            if(line != null)
            {
                onPostExecute("Attendance Successfully submitted!");
            }
        }
        catch(Exception e)
        {
            //no problem, just wasn't loaded yet
        }
    }

    protected void onPostExecute(String result)
    {
        //read server response
        if(result != null)
        {
            Looper.prepare();

            new NewMessage(result, student);
            Looper.loop();
            student.setSubmitted(true);
            student.bluetoothOff();
        }
    }
}