package com.ijh165.assignment3;

import java.util.List;

/**
 * Class name: SortChecker.java
 * Description: This is the sort checker class used to verify that the data is sorted at all times
 * Created by IvanJonathan on 2016-02-21.
 */
public class SortChecker
{
    //check if the string list is sorted lexicographically
    public static boolean checkStringList(List<String> data)
    {
        for(int i=0; i<data.size()-1; i++) {
            if( data.get(i).compareTo(data.get(i+1)) > 0 ) {
                return false;
            }
        }
        return true;
    }
}