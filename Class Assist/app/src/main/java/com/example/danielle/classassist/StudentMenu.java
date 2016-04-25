package com.example.danielle.classassist;

import android.app.ActivityManager;
import android.bluetooth.BluetoothAdapter;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.Timer;
import java.util.TimerTask;

public class StudentMenu extends AppCompatActivity implements View.OnClickListener
{
    private Button btnAttendance, btnLogout;

    //Bluetooth variables
    private boolean blueCompat = true; //bluetooth compatible, checked later
    private boolean blueEnabled = false; //off by default, used to identify if bluetooth is on
    private BluetoothAdapter bTooth;
    private final Handler macHandler = new Handler();
    private Timer timer;
    private boolean started = false;
    private StudentMenu student;
    private boolean wrongStudent = false;
    private boolean submitted = false;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentmenu);
        setupButtons();
        student = this;
    }

    /**
     * setupButtons initializes the buttons and registers the listener
     */
    private void setupButtons()
    {
        btnAttendance = (Button) findViewById(R.id.btnAttendance);
        btnLogout = (Button) findViewById(R.id.btnLogout);


        btnAttendance.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnLogout)
        {
            startActivity(new Intent(StudentMenu.this, Login.class));
            SelectedClass.resetClass();
        }
        else if(v.getId() == R.id.btnAttendance)
        {
            takeAttendance();
            if(!started)
                startTimer();
        }
    }

    private void startTimer()
    {
        started = true;
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask()
        {
            @Override
            public void run()
            {
                if(!submitted)
                    new CheckAttendSubmit(student).execute();
            }
        }, 100, 10000);
    }

    /**
     * sets submitted variable to param
     * @param b boolean
     */
    public void setSubmitted(boolean b)
    {
        this.submitted = b;
    }

    @Override
    public void onResume()
    {
        super.onResume();

        if(!started)
            startTimer();
    }

    @Override
    public void onPause()
    {
        super.onPause();

        stopTimer();
    }

    /**
     * stops timer checking for attendance submitted
     */
    public void stopTimer()
    {
        started = false;
        timer.cancel();
        timer.purge();
    }

    //begins taking attendance process for student side
    private void takeAttendance()
    {
        enableBluetooth();
        checkBlueTooth();
        getMacAddress();
    }

    //gets devices default adapter
    private void enableBluetooth()
    {
        bTooth = BluetoothAdapter.getDefaultAdapter();
    }

    //checks if bluetooth is already enabled
    private void checkBlueTooth()
    {
        blueEnabled = bTooth.isEnabled();
    }

    //lets users know their device does not support bluetooth
    private void checkBluetoothComp()
    {
        if(bTooth == null)
        {
            //Device does not support Bluetooth.
            blueCompat = false;
            new NewMessage("This device does not support bluetooth.", getBaseContext());
        }
    }

    /**
     * returns this
     */
    public StudentMenu getThis()
    {
        return this;
    }

    /**
     * Makes your device discoverable to other bluetooth devices if device is bluetooth compatible
     */
    public void broadcast()
    {
        if(!wrongStudent)
        {
            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            //next line of code will set the duration of discovery to 300 SECONDS. Default is 2 minutes
            //        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivity(discoverableIntent);
        }
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
                if (result == null || result.equals(""))
                {
                    addNewMac();
                }
                else if (!result.equals(normalizeMac(bTooth.getAddress())))
                {
                    new NewMessage("Device is already registered to another student. Please see the teacher.", student);
                    wrongStudent = true;
                }
                else
                {
                    checkBluetoothComp();
                    if(blueCompat)
                    {
                        broadcast();
                    }
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
        new NewMessage(msg, this); //should be a pop up or something
    }

    public void bluetoothOff()
    {
        if(blueEnabled)
            bTooth.disable();
    }
}
