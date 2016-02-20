package com.example.danielle.classassist;

//imports being used
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

//Tutorial used:
// http://www.tutorialspoint.com/android/android_login_screen.htm

//main class to Dummy
public class Dummy extends AppCompatActivity implements View.OnClickListener {

    //Declaring variables
    private static final String TEACHERSTRING = "SCHOOL-LAPTOP"; //name of device creating bluetooth signal
    private Button btnAttendance, btnQuizzes, btnLogout;
    private boolean blueCompat = true; //bluetooth compatible, checked later
    private BluetoothAdapter bTooth;
    private ArrayList<String> discoverables = new ArrayList<>();
    //receiver is called anytime a new bluetooth device is detected
    private final BroadcastReceiver receiver = new BroadcastReceiver()
    {
        public void onReceive(Context context, Intent intent)
        {
            String action = intent.getAction();
            //when discovery finds a device
            if(BluetoothDevice.ACTION_FOUND.equals(action))
            {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                discoverables.add(device.getName());
            }
        }


    };


    private Timer timer;
    private TimerTask timerTask;

    final Handler handler = new Handler();


    //method onCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy);

        //bluetooth setup
        bTooth = BluetoothAdapter.getDefaultAdapter();
        if(bTooth == null)
        {
            //Device does not support Bluetooth. We need to gracefully disable bluetooth features.
            blueCompat = false;
        }

        //Declaring the variables that are attendance, logout button
        btnAttendance = (Button) findViewById(R.id.btnAttendance);
        btnQuizzes = (Button) findViewById(R.id.btnQuizzes);
        btnLogout = (Button) findViewById(R.id.btnLogout);


        btnAttendance.setOnClickListener(this);
        deactivateAttendance(); //button is disable until main bluetooth connection is detected
        btnLogout.setOnClickListener(this);

        if(blueCompat)
        {
            //request permission
//            requestPermissions();
            //start loop to check for bluetooth connection
            startTimer();
        }
    }

    @Override
    protected  void onResume()
    {
        super.onResume();
        stopBluetooth();
        startTimer();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        stopBluetooth();
        stopTimerTask();
    }

    public void startTimer()
    {
        if(timerTask != null)
            timerTask.cancel();
        timer = new Timer();
        initializeTimerTask();

        //start bluetooth
        startBluetooth();

        timer.schedule(timerTask, 1000, 3000);
    }

    public void stopTimerTask()
    {
        if(timer != null)
        {
            timer.cancel();
            timer.purge();
            timer = null;
        }
        stopBluetooth();
    }

    public void initializeTimerTask()
    {
        timerTask = new TimerTask()
        {
            @Override
            public void run()
            {
                handler.post(new Runnable()
                {
                    public void run()
                    {
                        //start discovery
                        if(bTooth.isDiscovering())
                            bTooth.cancelDiscovery();
                        bTooth.startDiscovery();

                        if(found())
                            activateAttendance();
                        else
                            deactivateAttendance();

                        emptyDevices();
                    }
                });
            }
        };
    }

    private boolean found()
    {
        for(int i = 0; i < getDevices().size(); i++)
        {
            System.out.println(getDevices().get(i));
            try
            {
                if (getDevices().get(i).equals(TEACHERSTRING))
                {
                    return true;
                }
            } catch (NullPointerException e)
            {
                //do something
            }
        }

        return false;
    }

    private void startBluetooth()
    {
        int REQUEST_ENABLE_BT = 1;
        //check if bluetooth is turned on
        if(!bTooth.isEnabled())
        {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }

        //register broadcast receiver
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(receiver, filter); //unregister during onDestroy???
    }

    private void stopBluetooth()
    {
        //surround unregister receiver in try catch, in case there is no receiver registered
        try
        {
            unregisterReceiver(receiver);
        }
        catch(IllegalArgumentException e)
        {
            //don't need to do anything
        }
        if(bTooth.isDiscovering())
            bTooth.cancelDiscovery();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogout:
                startActivity(new Intent(Dummy.this, Login.class));
            case R.id.btnAttendance:
                startActivity(new Intent(Dummy.this, Login.class));
        }
    }

    public void activateAttendance()
    {
        btnAttendance.setEnabled(true);
    }

    public void deactivateAttendance()
    {
        btnAttendance.setEnabled(false);
    }

    public void emptyDevices()
    {
        discoverables = new ArrayList<>();
    }

    public ArrayList<String> getDevices()
    {
        return this.discoverables;
    }
}
