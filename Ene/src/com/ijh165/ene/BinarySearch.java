package com.ijh165.ene;

import java.util.Arrays;

/**
 * Class name: BinarySearch.java
 * Description: This is the binary search class
 * Created by IvanJonathan on 2016-02-21.
 */
public class BinarySearch
{
    public static int searchString(String[] data, String target)
    {
        return Arrays.binarySearch(data, target);
    }
}