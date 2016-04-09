package com.ijh165.ene;

/**
 * Class name: Timer.java
 * Description: This is the timer class to do the timing.
 * Created by IvanJonathan on 2016-02-21.
 */
public class Timer
{
    //error msg constants
    private static final String NEG_TIMING_ERR = "stopTimeInMillis - startTimeInMillis is negative!";

    //attributes
    private long startTimeInMillis;
    private long stopTimeInMillis;
    private boolean stopped;

    //constructor
    public Timer()
    {
        startTimeInMillis = stopTimeInMillis = 0;
        stopped = false;
    }

    //start
    public void start()
    {
        startTimeInMillis = System.currentTimeMillis();
        stopped = false;
    }

    //stop
    public void stop()
    {
        if(!stopped) {
            stopTimeInMillis = System.currentTimeMillis();
            stopped = true;
        }
    }

    //reset
    public void reset()
    {
        startTimeInMillis = stopTimeInMillis = 0;
        stopped = false;
    }

    //getters
    public long getRecordedTime() throws Exception
    {
        if(startTimeInMillis > stopTimeInMillis) {
            throw new Exception(NEG_TIMING_ERR);
        }
        return stopTimeInMillis - startTimeInMillis;
    }
}