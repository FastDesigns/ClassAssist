package com.example.danielle.classassist;

import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class StudentMenu extends AppCompatActivity implements View.OnClickListener
{
    private Button btnAttendance, btnLogout;

    //Bluetooth variables
    private boolean blueCompat = true; //bluetooth compatible, checked later
    private boolean blueEnabled = false; //off by default, used to identify if bluetooth is on
    private BluetoothAdapter bTooth;

    final Handler macHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentmenu);
        setupButtons();
    }

    /**
     * setupButtons initializes the buttons and registers the listener
     */
    private void setupButtons()
    {
        btnAttendance = (Button) findViewById(R.id.btnAttendance);
        //btnQuizzes = (Button) findViewById(R.id.btnQuizzes);
        btnLogout = (Button) findViewById(R.id.btnLogout);


        btnAttendance.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnLogout)
            startActivity(new Intent(StudentMenu.this, Login.class));
        else if(v.getId() == R.id.btnAttendance)
        {
            takeAttendance();
            btnAttendance.setText("Submit Again");
        }
//        switch (v.getId()) {
//            case R.id.btnLogout:
//                startActivity(new Intent(StudentMenu.this, Login.class));
//            case R.id.btnAttendance:
//                takeAttendance();
//                btnAttendance.setText("Submit Again");
//                //startActivity(new Intent(StudentMenu.this, AttendanceActivity.class));
//        }
    }

    //begins taking attendance process for student side
    private void takeAttendance()
    {
        enableBluetooth();
        checkBlueTooth();
        setup();
    }

    //gets devices default adapter
    private void enableBluetooth()
    {
        bTooth = BluetoothAdapter.getDefaultAdapter();
    }

    //checks if bluetooth is already enabled
    private void checkBlueTooth()
    {
        if(bTooth.isEnabled())
            blueEnabled = true;
        else
            blueEnabled = false;
    }

    //starts bluetooth if compatible
    private void setup()
    {
        checkBluetoothComp();
        if(blueCompat)
        {
            //start bluetooth
            broadcast();
        }
    }

    //lets users know their device does not support bluetooth
    private void checkBluetoothComp()
    {
        if(bTooth == null)
        {
            //Device does not support Bluetooth. We need to gracefully disable bluetooth features.
            blueCompat = false;
            errorDialog(R.layout.incomp_blue);
        }
    }

    //makes the phone discoverable to other devices
    private void broadcast()
    {
        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        //next line of code will set the duration of discovery to 300 SECONDS. Default is 2 minutes
//        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        startActivity(discoverableIntent);
        getMacAddress();
    }

    //retrieves mac address from database
    private void getMacAddress()
    {
        new RequestMacAddress(this).execute();
    }

    /**
     * if mac address is missing, adds to database. Else, it's different and cannot be changed by
     * user
     * @param result Message informing user of Mac mis-match
     */
    public void checkMacAddress(final String result)
    {
        macHandler.post(new Runnable()
        {
            public void run()
            {
                if (result.equals(""))
                    addNewMac();
                else if (!result.equals(normalizeMac(bTooth.getAddress())))
                {
                    errorDialog(R.layout.wrong_user);
                }
            }
        });
    }

    //takes out colons from internal bluetooth mac address
    private String normalizeMac(String mac)
    {
        String result = "";
        String[] split = mac.split(":");
        for(int i = 0; i < split.length; i++)
        {
            result += split[i];
        }
        return result;
    }

    //Creates an error message
    private void errorDialog(int layout)
    {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(layout);
        dialog.setTitle("Error");
        Button btnDismiss = (Button) dialog.findViewById(R.id.dismiss);

        btnDismiss.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                dialog.cancel();
            }
        });;

        dialog.show();
    }

    //adds user's mac address to database
    private void addNewMac()
    {
        new AddMacAddress(this).execute(bTooth.getAddress());
    }

    /**
     * displays error to user if mac address has failed to be added to database
     */
    public void failedToAddMac(String msg)
    {
        System.out.println(msg); //should be a pop up or something
    }
}
