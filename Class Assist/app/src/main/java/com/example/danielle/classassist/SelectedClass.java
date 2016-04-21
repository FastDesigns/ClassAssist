package com.example.danielle.classassist;

public final class SelectedClass
{
    private static String cl;

    public SelectedClass(String c)
    {
        cl = c;
    }

    public static String getSelectedClass()
    {
        return cl;
    }

    public static void resetClass()
    {
        cl = "";
    }
}