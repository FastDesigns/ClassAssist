package com.example.danielle.classassist;

public final class User
{
    private static String username;

    private User()
    {
        username = "";
    }

    public static void setUsername(String user)
    {
        username = user;
    }

    public static String getUsername()
    {
        return username;
    }

    public static void resetUser()
    {
        username = "";
    }
}