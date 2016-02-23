package com.ijh165.ene;

/**
 * Created by IvanJonathan on 2016-02-21.
 */
public class SortChecker
{
    //methods
    public static boolean check(String[] data)
    {
        for(int i=0; i<data.length-1; i++)
            if(data[i].compareTo(data[i+1])>0)
                return false;
        return true;
    }
}