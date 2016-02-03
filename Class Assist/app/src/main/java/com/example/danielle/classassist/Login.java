package com.example.danielle.classassist;

//imports being used
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


//Tutorial used:
// http://www.tutorialspoint.com/android/android_login_screen.htm
//

//main class to Login
public class Login extends AppCompatActivity implements View.OnClickListener {
    private TextView incorrect;

    private Integer image[] = {R.drawable.rulogo};
    private int currImage = 0;

    //Declaring variables
    Button btnLogin;
    EditText emailAddress, password;

    private String username, pass;

    //method onCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Declaring the variables that are username, password, login button
        emailAddress = (EditText) findViewById(R.id.EmailAddress);
        password = (EditText) findViewById(R.id.Password);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        incorrect = (TextView) findViewById(R.id.incorrect);

        btnLogin.setOnClickListener(this);

       setInitialImage();

    }

    private void setInitialImage() { setCurrentImage();}

    private void setCurrentImage() {

        final ImageView imageView = (ImageView) findViewById(R.id.imageDisplay);
//        imageView.setImageResource(image[currImage]);
     }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                //gotta redo to make it work for the database
                username = emailAddress.getText().toString();
                pass = password.getText().toString();
                new SignInActivity(this).execute(username, pass);
        }
    }

    public void showCourses()
    {
        startActivity(new Intent(Login.this, SVCourses.class));
    }
    public void incorrectLogin()
    {
        incorrect.setText("Login Information Incorrect.");
    }
}








