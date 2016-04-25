package com.classassist.fastdesigns.gui;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

/**
 * Manages the look and feel of the GUI.
 * @author Fast Designs
 * @version 1.0
 */
public class ChangeLook
{
	/**
	 * Sets the LookAndFeel of the window to the File Chooser.
	 */
	public void fileChooserLook()
	{
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Sets the LookAndFeel of the window to Nimbus.
	 */
	public void programLook()
	{
		try
		{
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())
		    {
		        if ("Nimbus".equals(info.getName()))
		        {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} 
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
