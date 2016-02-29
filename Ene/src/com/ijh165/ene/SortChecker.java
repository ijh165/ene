/**
 * Filename: SortChecker.java
 * Description: This file contains the sort checker class used to verify that the data is sorted at all times
 * Created by IvanJonathan on 2016-02-21.
 */

package com.ijh165.ene;

public class SortChecker
{
    //methods
    public static boolean checkString(String[] data)
    {
        for(int i=0; i<data.length-1; i++)
            if(data[i].compareTo(data[i+1])>0)
                return false;
        return true;
    }
}