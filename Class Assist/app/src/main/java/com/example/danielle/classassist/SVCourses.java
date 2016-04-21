package com.example.danielle.classassist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class SVCourses extends AppCompatActivity implements View.OnClickListener{
    //declaring variables
    Button btnLogout;
    String result;
    String[] s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_svcourses);

        new RetrieveCourses(this).execute(User.getUsername());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogout:
                startActivity(new Intent(SVCourses.this, Login.class));
                User.resetUser();
        }
    }

    public void setResult(String r)
    {
        this.result = r;
    }

    public void split()
    {
        s = result.split("&");
        create();
    }

    public void create()
    {
        LinearLayout ll = (LinearLayout)findViewById(R.id.ll);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        for(int i = 0; i < s.length; i++)
        {
            final int index = i;
            Button myButton = new Button(this);
            myButton.setText(s[i]);
            myButton.setId(i);
            final int id = myButton.getId();
            ll.addView(myButton, lp);
            myButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v)
                {
                    startActivity(new Intent(SVCourses.this, StudentMenu.class));
                    new SelectedClass(s[index]);
                }
            });
        }

        //if s contains 1 item and that item is "Problem connecting to database", then there was an error connecting to database. Return to login.
        if(s.length == 1 && s[0].equals("Problem connecting to database"))
        {
            try
            {
                this.wait(3000);
                startActivity(new Intent(SVCourses.this, Login.class));
            }
            catch(InterruptedException e) //if wait is interrupted, go ahead to login
            {
                startActivity(new Intent(SVCourses.this, Login.class));
            }
        }
        else
        {
            btnLogout = (Button) findViewById(R.id.btnLogout);

            btnLogout.setOnClickListener(this);
        }
    }
}
