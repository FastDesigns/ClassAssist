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

    //method onCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        //Declaring the variables that are attendance, logout button

        btnLogout = (Button) findViewById(R.id.btnLogout);


        btnLogout.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogout:
                startActivity(new Intent(Attendance.this, Login.class));
        }
    }
}
