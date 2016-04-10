package com.ijh165.ene;

import java.util.Collections;
import java.util.List;

/**
 * Class name: BinarySearch
 * Description: This is the binary search class which perform all binary searches in the program
 * Created by IvanJonathan on 2016-02-21.
 */
public class BinarySearch
{
    //search list of strings using binary search
    //returns the index of the target if found, (-(insertion_point) - 1) otherwise
    //insertion_point is defined as the index at which the target would be inserted into the list
    public static int searchStringList(List<String> data, String target)
    {
        return Collections.binarySearch(data, target);
    }
}