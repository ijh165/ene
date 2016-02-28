package com.ijh165.ene;

import java.io.*;
//import java.util.*;

/**
 * Created by IvanJonathan on 2016-02-21.
 */
public class A3
{
    //constants
    private static int TIME_CONSTRAINT = 5;
    private static final String EXIT_MSG = "Exiting program...";
    private static final String TIME_VIOLATION_LOG_FNAME = "timing_logs.txt";
    private static final String TIME_VIOLATION_LOG_ENTRY = "---Time violation detected! Input processed after %s seconds!---";

    public static void main(String[] args)
    {
        Doctor doctor = new Doctor();
        Patient patient =  new Patient();
        ProgramTimer timer = new ProgramTimer();

        //load patterns (exit program with error stat code if fail)
        if(!doctor.loadPatterns()) {
            System.out.println(EXIT_MSG);
            System.exit(1);
        }

        //greet user
        doctor.greet();

        //start event loop
        while(true)
        {
            //prompts the user to input stuff
            patient.promptInput();

            //if user quits or if user input nothing
            if(patient.inputEmpty()) {
                doctor.respondNoInput();
                //timer.stop();
                continue;
            }
            if(patient.exitingProgram()) {
                doctor.goodbye();
                //timer.stop();
                break;
            }

            //reset and start timer
            timer.reset();
            timer.start();

            //input processing
            String input = patient.getInput();
            String output = doctor.respond(input);
            doctor.speak(output);

            //stop timer
            timer.stop();

            //check for time violations
            if(timer.getTime() > TIME_CONSTRAINT) {
                File f = new File(TIME_VIOLATION_LOG_FNAME);
                try {
                    //create time violation log file (or not if already exist)
                    PrintWriter pw = new PrintWriter(f);
                    if(!f.exists()) {
                        f.createNewFile();
                    }
                    //write time entry logs to it
                    pw.println(String.format(TIME_VIOLATION_LOG_ENTRY,timer.getTime()));
                    pw.close();
                }
                catch(IOException e) {
                    e.printStackTrace();
                }

                System.out.println(timer.getTime());
            }

            //simulates inputs/commands processing 1000000-1 times
            /*for(int i=0; i<1000000-1; i++) {
                //reset and start timer
                timer.reset();
                timer.start();

                //input processing
                doctor.respond(input);

                //stop timer
                timer.stop();

                if(timer.getTime()>TIME_CONSTRAINT) {

                }
            }*/
        }
    }
}