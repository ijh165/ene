/**
 * Filename: A3
 * Description: This is the class that contains the main method
 * Created by IvanJonathan on 2016-02-21.
 *
 * IMPORTANT: Please also take a look at the README.txt which contains file format and pattern documentation
 */

package com.ijh165.ene;

import java.io.*;

public class A3
{
    //constants
    private static int TIME_CONSTRAINT = 5;
    private static final String EXIT_MSG = "Exiting program...";
    private static final String TIMING_VIOLATION_LOG_ENTRY = "---Timing violation detected! Input processing takes %s milliseconds!---";
    private static final String VIOLATION_STATS_LOG_ENTRY = "-----%s out of 1000000 executions exceeds 5ms!-----";
    private static final String LOAD_PATTERNS_FAIL_ERR = "Failed to load patterns. Make sure the pattern file \"patterns.txt\" exist!";
    private static final String LOG_FILE_CREATION_ERR = "Failed to create log files";
    private static final String WRITE_TIMING_LOG_ENTRY_ERR = "Failed to write timing log entries. Make sure the file \"timing_log.txt\" still exist!";

    //file and dir name constants
    private static final String LOG_DIR = "log/";
    private static final String PATTERNS_FILENAME = "patterns.txt";
    private static final String IO_ERROR_LOG_FILENAME = "io_error_log.txt";
    private static final String TIMING_LOG_FILENAME = "timing_log.txt";

    //program objects
    private static Doctor doctor;
    private static Patient patient;
    private static ProgramTimer timer;

    public static void main(String[] args)
    {
        //initialize objects
        doctor = new Doctor();
        patient = new Patient();
        timer = new ProgramTimer();
        //initialize log files
        initLogFiles();
        //load patterns (exit program with error stat code if fail)
        try {
            doctor.loadPatterns(PATTERNS_FILENAME);
        }
        catch(IOException e) {
            logFileIOerror(LOAD_PATTERNS_FAIL_ERR);
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
                continue;
            }
            if(patient.exitingProgram()) {
                doctor.goodbye();
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

            //check for timing violations and log it (includes simulation)
            logTimingViolation(input);
        }

        System.out.println(EXIT_MSG);
    }

    //create log files and store them in the "log" folder
    public static void initLogFiles()
    {
        try {
            //create log dir (if not exists)
            File f = new File(LOG_DIR);
            if (!f.exists()) {
                f.mkdir();
            }
            //create timing log file (or replace if already exist)
            f = new File(LOG_DIR + TIMING_LOG_FILENAME);
            if (f.exists()) {
                PrintWriter pw = new PrintWriter(f);
                pw.print("");
                pw.close();
            }
            else {
                f.createNewFile();
            }
            //create io error log file (or replace if already exist)
            f = new File(LOG_DIR + IO_ERROR_LOG_FILENAME);
            if (f.exists()) {
                PrintWriter pw = new PrintWriter(f);
                pw.print("");
                pw.close();
            }
            else {
                f.createNewFile();
            }
        }
        catch(IOException e) {
            logFileIOerror(LOG_FILE_CREATION_ERR);
            System.out.println(EXIT_MSG);
            System.exit(1);
        }
    }

    //file IO logging
    public static void logFileIOerror(String ioLogEntry)
    {
        try {
            //write entry logs to it
            FileWriter fw = new FileWriter(LOG_DIR + IO_ERROR_LOG_FILENAME, true);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(ioLogEntry);
            pw.close();
        }
        catch(IOException e) {
            System.out.println(EXIT_MSG);
            System.exit(1);
        }
    }

    //time violations logging
    public static void logTimingViolation(String input)
    {
        try {
            //timing violation counter
            int violationCtr = 0;

            //check for timing violation once
            if(timer.getTime() > TIME_CONSTRAINT) {
                //write timing violation entries
                FileWriter fw = new FileWriter(LOG_DIR + TIMING_LOG_FILENAME, true);
                PrintWriter pw = new PrintWriter(fw);
                pw.println(String.format(TIMING_VIOLATION_LOG_ENTRY, timer.getTime()));
                pw.close();
                //increment counter
                violationCtr++;

                //debug stuffs
                /*System.out.println(timer.getTime());*/
            }

            //simulates input processing 1000000-1 times and check timing violations
            for(int i = 1; i < 1000000; i++) {
                //reset and start timer
                timer.reset();
                timer.start();
                //input processing
                doctor.respond(input);
                //stop timer
                timer.stop();

                //check for timing violation
                if(timer.getTime() > TIME_CONSTRAINT) {
                    //write timing violation entries
                    FileWriter fw = new FileWriter(LOG_DIR + TIMING_LOG_FILENAME, true);
                    PrintWriter pw = new PrintWriter(fw);
                    pw.println(String.format(TIMING_VIOLATION_LOG_ENTRY, timer.getTime()));
                    pw.close();
                    //increment counter
                    violationCtr++;

                    //debug stuffs
                    /*System.out.println(timer.getTime());*/
                }
            }

            //write timing violations statistics
            FileWriter fw = new FileWriter(LOG_DIR + TIMING_LOG_FILENAME, true);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(String.format(VIOLATION_STATS_LOG_ENTRY, violationCtr));
            pw.close();
        }
        catch(IOException e) {
            logFileIOerror(WRITE_TIMING_LOG_ENTRY_ERR);
            System.out.println(EXIT_MSG);
            System.exit(1);
        }
    }
}