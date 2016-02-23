package com.ijh165.ene;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by IvanJonathan on 2016-02-21.
 */
public class ProgramTimer
{
    private int milliseconds;
    private Timer timer = new Timer();
    private TimerTask task = new TimerTask() {
        @Override
        public void run() {
            milliseconds++;
        }
    };

    //start/stop
    public void start()
    {
        timer.scheduleAtFixedRate(task,1,1);
    }
    public void stop()
    {
        timer.cancel();
        timer.purge();
    }

    //getters
    public int getTime()
    {
        return milliseconds;
    }
}