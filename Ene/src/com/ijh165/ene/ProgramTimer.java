package com.ijh165.ene;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by IvanJonathan on 2016-02-21.
 */
public class ProgramTimer
{
    private int milliseconds;
    private Timer timer;

    //start
    public void start()
    {
        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                milliseconds++;
            }
        };
        timer.scheduleAtFixedRate(task,1,1);
    }

    //stop
    public void stop()
    {
        timer.cancel();
        timer.purge();
    }

    //reset
    public void reset()
    {
        milliseconds = 0;
    }

    //getters
    public int getTime()
    {
        return milliseconds;
    }
}