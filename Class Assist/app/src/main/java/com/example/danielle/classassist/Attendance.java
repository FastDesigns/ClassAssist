package com.example.danielle.classassist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by danielle on 1/28/16.
 */

//main class to Attendance
public class Attendance extends AppCompatActivity implements View.OnClickListener {

    //Declaring variables
    Button btnLogout;
    Button btnSM;
    Button btnSVC;
    Button btnTA;

    //method onCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        //Declaring the variables that are attendance, logout button

        btnLogout = (Button) findViewById(R.id.btnLogout);
        btnSM = (Button) findViewById(R.id.btnSM);
        btnSVC = (Button) findViewById(R.id.btnSVC);
        btnTA = (Button) findViewById(R.id.btnTA);

        btnLogout.setOnClickListener(this);
        btnSM.setOnClickListener(this);
        btnSVC.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnSM) {
            startActivity(new Intent(Attendance.this, StudentMenu.class));
        }
        else if (v.getId() == R.id.btnSVC) {
            startActivity(new Intent(Attendance.this, SVCourses.class));
        }
        else{ // for logout button
            startActivity(new Intent(Attendance.this, Login.class));
        }
    }
}
