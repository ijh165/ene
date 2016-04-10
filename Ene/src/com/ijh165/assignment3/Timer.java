package com.ijh165.assignment3;

/**
 * Class name: Timer.java
 * Description: This is the timer class to do the timing. Timing measurement is done in nanoseconds to be accurate.
 * Created by IvanJonathan on 2016-02-21.
 */
public class Timer
{
    //exception msg constants
    private static final String NEG_TIMING_ERR = "stopTimeInNanoSec - startTimeInNanoSec is negative!";

    //attributes
    private long startTimeInNanoSec;
    private long stopTimeInNanoSec;
    private boolean stopped;

    //constructor
    public Timer()
    {
        startTimeInNanoSec = stopTimeInNanoSec = 0;
        stopped = false;
    }

    //start
    public void start()
    {
        startTimeInNanoSec = System.nanoTime();
        stopped = false;
    }

    //stop
    public void stop()
    {
        if(!stopped) {
            stopTimeInNanoSec = System.nanoTime();
            stopped = true;
        }
    }

    //reset
    public void reset()
    {
        startTimeInNanoSec = stopTimeInNanoSec = 0;
        stopped = false;
    }

    //getters
    public long getRecordedTimeInNanoSec() throws Exception
    {
        if(startTimeInNanoSec > stopTimeInNanoSec) {
            throw new Exception(NEG_TIMING_ERR);
        }
        return stopTimeInNanoSec - startTimeInNanoSec;
    }
}