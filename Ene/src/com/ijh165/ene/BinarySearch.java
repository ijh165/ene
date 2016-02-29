/**
 * Filename: BinarySearch.java
 * Description: This file contains the binary search class
 * Created by IvanJonathan on 2016-02-21.
 */

package com.ijh165.ene;

import java.util.Arrays;

public class BinarySearch
{
    public static int searchString(String[] data, String target)
    {
        return Arrays.binarySearch(data, target);
    }
}