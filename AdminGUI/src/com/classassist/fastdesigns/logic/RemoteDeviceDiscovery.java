package com.classassist.fastdesigns.logic;

import java.util.Vector;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DeviceClass;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.DiscoveryListener;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.ServiceRecord;

/**
 * Finds Bluetooth devices
 * 
 * @author BlueCove (Edited by Fast Designs)
 *
 */
public class RemoteDeviceDiscovery {

	/**
	 * Gets nearby Bluetooth devices mac addresses
	 * @return Vector of found devices
	 */
    public Vector getDevices() {
        /* Create Vector variable */
        final Vector devicesDiscovered = new Vector();
        try {
            final Object inquiryCompletedEvent = new Object();
            /* Clear Vector variable */
            devicesDiscovered.clear();

            /* Create an object of DiscoveryListener */
            DiscoveryListener listener = new DiscoveryListener() {

                public void deviceDiscovered(RemoteDevice btDevice, DeviceClass cod) {
                    /* Get devices paired with system or in range(Without Pair) */
                    devicesDiscovered.addElement(btDevice);
                }

                public void inquiryCompleted(int discType) {
                    /* Notify thread when inquiry completed */
                    synchronized (inquiryCompletedEvent) {
                        inquiryCompletedEvent.notifyAll();
                    }
                }

                /* To find service on bluetooth */
                public void serviceSearchCompleted(int transID, int respCode) {
                }

                /* To find service on bluetooth */
                public void servicesDiscovered(int transID, ServiceRecord[] servRecord) {
                }
            };

            synchronized (inquiryCompletedEvent) {
                /* Start device discovery */
            	boolean started = false;
            	try
            	{
            		started = LocalDevice.getLocalDevice().getDiscoveryAgent().startInquiry(DiscoveryAgent.GIAC, listener);
            	}
            	catch(BluetoothStateException e)
            	{
//            		new NewMessage("Bluetooth is not turned on"); if you press take attendance while it is running, it will make a lot of messages
            	}
                if (started) {
//                    System.out.println("wait for device inquiry to complete...");
                    inquiryCompletedEvent.wait();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        /* Return list of devices */
        return devicesDiscovered;
    }
}