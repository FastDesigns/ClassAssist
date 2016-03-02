package com.example.danielle.classassist;

public final class User
{
    private static String username;

    public User(String user)
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