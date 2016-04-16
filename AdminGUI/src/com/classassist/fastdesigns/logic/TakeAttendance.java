package com.classassist.fastdesigns.logic;

import java.util.Vector;

public class TakeAttendance {

	public static void recordAttendance(String className, String date)
	{
		Vector<String> testVector = BluetoothDeviceVector.getMacVector();
		for(int x = 0; x < testVector.size(); x++)
		{
			String[] attendance = {"",className,date};
			attendance[0]=GetUserFromMac.getUser(testVector.elementAt(x));
			SetAttendance.setAttendance(attendance);
		}
	}
}