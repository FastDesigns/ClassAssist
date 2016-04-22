package com.classassist.fastdesigns.logic;

import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

public class TakeAttendance
{
	public static void recordAttendance(final String className, final String date)
	{
		final Timer timer = new Timer();
		Timer cancel = new Timer();
		
		timer.scheduleAtFixedRate(new TimerTask()
		{
			@Override
			public void run()
			{
				Vector<String> testVector = BluetoothDeviceVector.getMacVector();
				for(int x = 0; x < testVector.size(); x++)
				{
					String[] attendance = {"",className,date};
					attendance[0]=GetUserFromMac.getUser(testVector.elementAt(x));
					SetAttendance.setAttendance(attendance);
					System.out.println("Submitted attendance");
				}
			}
		}, 20, 3);
		
		cancel.schedule(new TimerTask()
		{
			@Override
			public void run()
			{
				timer.cancel();
				timer.purge();
			}
		}, 300000);
	}
}