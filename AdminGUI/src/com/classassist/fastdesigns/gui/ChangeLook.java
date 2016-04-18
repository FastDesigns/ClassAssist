package com.classassist.fastdesigns.gui;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

public class ChangeLook
{
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