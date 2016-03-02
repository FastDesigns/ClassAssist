package com.example.danielle.classassist;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Eddie Justice
 * Converts plain text into a specified encryption method
 */
public class Encrypt
{
    public String encrypt(String pass)
    {
        try
        {
            MessageDigest mdg = MessageDigest.getInstance("MD5"); //change this to change encryption method. May need to alter class for certain encryption algorithms)

            return convertByteArrayToHex(mdg.digest(pass.getBytes("UTF-8")));
        }
        catch(NoSuchAlgorithmException ex)
        {
            System.out.println("Cannot find encryption algorithm.");
        }
        catch(UnsupportedEncodingException ex)
        {
            System.out.println("UTF-8 encoding not supported");
        }
        return null;
    }

    private String convertByteArrayToHex(byte[] bytes)
    {
        StringBuffer sb = new StringBuffer();

        for(int i = 0; i < bytes.length; i++)
        {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }
}