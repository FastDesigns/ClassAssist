package com.classassist.fastdesigns.logic;

import java.util.Vector;

/**
 * BluetoothDeviceVector.java is used to scan for Bluetooth devices and store.
 * @author BLUECOVE editted by Chase Abe
 *
 */
public class BluetoothDeviceVector {
	/**
	 * getMacVector() finds all available Bluetooth devices and returns a Vector<String>
	 * of each device with name and MAC address.
	 * @return list of found bluetooth devices
	 */
	public static Vector<String> getMacVector(){
//		ServicesSearch ss = new ServicesSearch();
		Vector<String> finalVector = new Vector<String>();
		for(int x=0;x<1;x++){
//			System.out.println("Starting device search");
			Vector<String> tempVector = ServicesSearch.getMacs();
//			System.out.println("Ending device search");
			for (int y = 0; y<tempVector.size();y++){
//				System.out.println("Vector search");
				if (!finalVector.contains(tempVector.elementAt(y))){
					finalVector.add(tempVector.elementAt(y));
				}
			}
//			System.out.println("Vector search complete, sleeping");
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
			}
		}
		return finalVector;
	}
}