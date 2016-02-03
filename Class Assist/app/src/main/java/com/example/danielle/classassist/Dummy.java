package com.example.danielle.classassist;

//imports being used
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.graphics.Color;

//Tutorial used:
// http://www.tutorialspoint.com/android/android_login_screen.htm

//main class to Dummy
public class Dummy extends AppCompatActivity implements View.OnClickListener {

    //Declaring variables
    Button btnAttendance, btnQuizzes, btnLogout;



    //method onCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy);

        //Declaring the variables that are attendance, logout button
        btnAttendance = (Button) findViewById(R.id.btnAttendance);
        btnQuizzes = (Button) findViewById(R.id.btnQuizzes);
        btnLogout = (Button) findViewById(R.id.btnLogout);


        btnLogout.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogout:
                startActivity(new Intent(Dummy.this, Login.class));

        }
    }
}
