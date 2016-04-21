package com.classassist.fastdesigns.logic;

import javax.swing.JFrame;

public class FrameSize
{
	private static JFrame main;
	
	public FrameSize(JFrame m)
	{
		FrameSize.main = m;
	}
	
	/**
	 * Get the X value of the window
	 * @return int
	 */
	public static int getX()
	{
		return main.getX();
	}
	
	/**
	 * Get the Y value of the window
	 * @return int
	 */
	public static int getY()
	{
		return main.getY();
	}
	
	/**
	 * Get the width of the window
	 * @return int
	 */
	public static int getWidth()
	{
		return main.getWidth();
	}
	
	/**
	 * Get the height of the window
	 * @return int
	 */
	public static int getHeight()
	{
		return main.getHeight();
	}
}