package com.ijh165.ene;

import java.io.*;

/**
 * Class name: A3
 * Description: This is the class that contains the main method.
 * Created by IvanJonathan on 2016-02-21.
 */
public class A3
{
    //time constraint and exit message
    private static final long TIME_CONSTRAINT = 5;
    private static final String EXIT_MSG = "Exiting program...";

    //log entries
    private static final String TIMING_VIOLATION_LOG_ENTRY = "---Timing violation detected! Input processing takes %s milliseconds!---";
    private static final String TIMING_VIOLATION_STATS_LOG_ENTRY = "-----%s out of 1000000 simulations exceeds 5ms!-----";
    private static final String LOAD_PATTERNS_FAIL_ERR = "Failed to load patterns. Make sure the pattern file \"patterns.txt\" exist!";
    private static final String LOG_FILE_CREATION_ERR = "Failed to create log files.";
    private static final String WRITE_TIMING_LOG_ENTRY_ERR = "Failed to write timing log entries.";

    //paths
    private static final String LOG_DIR = System.getProperty("user.dir") + "/log";
    private static final String PATTERN_FILE_PATH = System.getProperty("user.dir") + "/patterns.txt";
    private static final String IO_ERROR_LOG_FILE_PATH = LOG_DIR + "/io_error_log.txt";
    private static final String TIMING_ERR_LOG_FILE_PATH = LOG_DIR + "/timing_violation_log.txt";

    //program objects
    private static Doctor doctor;
    private static Patient patient;
    private static Timer timer;

    public static void main(String[] args)
    {
        //initialize objects
        doctor = new Doctor();
        patient = new Patient();
        timer = new Timer();

        //initialize log files
        initLogFiles();

        //load patterns (exit program with error stat code if fail)
        try {
            doctor.loadPatterns(PATTERN_FILE_PATH);
        }
        catch(IOException e) {
            logFileIOError(LOAD_PATTERNS_FAIL_ERR);
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

            //actual command processing
            doctor.simulationOff();

            //reset and start timer
            timer.reset();
            timer.start();
            //input processing
            String input = patient.getInput();
            doctor.respond(input);
            //stop timer
            timer.stop();

            //check for timing violations and log it (includes simulations)
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
            f = new File(TIMING_ERR_LOG_FILE_PATH);
            if (f.exists()) {
                PrintWriter pw = new PrintWriter(f);
                pw.print("");
                pw.close();
            }
            else {
                f.createNewFile();
            }
            //create io error log file (or replace if already exist)
            f = new File(IO_ERROR_LOG_FILE_PATH);
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
            logFileIOError(LOG_FILE_CREATION_ERR);
            System.out.println(EXIT_MSG);
            System.exit(1);
        }
    }

    //file IO logging
    public static void logFileIOError(String ioLogEntry)
    {
        try {
            //write entry logs to it
            FileWriter fw = new FileWriter(IO_ERROR_LOG_FILE_PATH, true);
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
            if(timer.getRecordedTime() > TIME_CONSTRAINT) {
                //write timing violation entries
                FileWriter fw = new FileWriter(TIMING_ERR_LOG_FILE_PATH, true);
                PrintWriter pw = new PrintWriter(fw);
                pw.println(String.format(TIMING_VIOLATION_LOG_ENTRY, timer.getRecordedTime()));
                pw.close();
                //increment counter
                violationCtr++;
            }

            //just a simulation
            doctor.simulationOn();

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
                if(timer.getRecordedTime() > TIME_CONSTRAINT) {
                    //write timing violation entries
                    FileWriter fw = new FileWriter(TIMING_ERR_LOG_FILE_PATH, true);
                    PrintWriter pw = new PrintWriter(fw);
                    pw.println(String.format(TIMING_VIOLATION_LOG_ENTRY, timer.getRecordedTime()));
                    pw.close();
                    //increment counter
                    violationCtr++;
                }
            }

            //write timing violations statistics
            FileWriter fw = new FileWriter(TIMING_ERR_LOG_FILE_PATH, true);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(String.format(TIMING_VIOLATION_STATS_LOG_ENTRY, violationCtr));
            pw.close();
        }
        catch(IOException e) {
            logFileIOError(WRITE_TIMING_LOG_ENTRY_ERR);
            System.out.println(EXIT_MSG);
            System.exit(1);
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
            System.out.println(EXIT_MSG);
            System.exit(1);
        }
    }
}