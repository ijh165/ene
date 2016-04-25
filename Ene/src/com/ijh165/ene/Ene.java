package com.ijh165.ene;

import java.io.*;

/**
 * Class name: Ene
 * Description: This is the class that contains the main method.
 * Created by IvanJonathan on 2016-02-21.
 */
public class Ene
{
    //time constraint and exit message
    private static final long TIME_CONSTRAINT_IN_MS = 5;
    private static final String EXIT_MSG = "Exiting program...";

    //log entries
    private static final String START_TIMING_LOG_ENTRY = "-----USER INPUT: %s-----";
    private static final String TIMING_VIOLATION_LOG_ENTRY = "---Timing violation detected! Input processing takes %s milliseconds!---";
    private static final String STAT_SUMMARY_LOG_ENTRY = "-----%s out of 1000000 simulations exceeded " + TIME_CONSTRAINT_IN_MS + " milliseconds! INPUT: %s-----";

    //error messages (shouldn't be printed if no file I/O exceptions)
    private static final String LOAD_PATTERNS_FAIL_ERR = "Failed to load patterns. Make sure the pattern file \"patterns.txt\" exist!";
    private static final String LOG_FILE_CREATION_ERR = "Failed to create log files.";
    private static final String WRITE_TIMING_LOG_ENTRY_ERR = "Failed to write timing log entries.";

    //paths
    private static final String PATTERN_FILE_PATH = System.getProperty("user.dir") + "/patterns.txt";
    private static final String TIMING_ERR_LOG_FILE_PATH = System.getProperty("user.dir") + "/timing_violation_log.txt";

    //program objects
    private static Doctor doctor;
    private static Patient patient;
    private static Timer timer;

    public static void main(String[] args)
    {
        //for debugging only
        /*testSortRelatedClasses();*/

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
            System.out.println(LOAD_PATTERNS_FAIL_ERR);
            System.out.println(EXIT_MSG);
            System.exit(1);
        }

        //greet user
        doctor.greet();

        //start event loop
        while(true)
        {
            //turn off simulation button...
            doctor.simulationOff();

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
            doctor.respond(input);
            //stop timer
            timer.stop();

            //check for timing violations and log it (includes simulations)
            logTimingViolation(input);
        }

        System.out.println(EXIT_MSG);
    }

    //create log files and store them in the "log" folder
    private static void initLogFiles()
    {
        try {
            //create timing log file (or replace if already exist)
            File f = new File(TIMING_ERR_LOG_FILE_PATH);
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
            System.out.println(LOG_FILE_CREATION_ERR);
            System.out.println(EXIT_MSG);
            System.exit(1);
        }
    }

    //time violations logging
    private static void logTimingViolation(String input)
    {
        try {
            //prepare writer
            FileWriter fw = new FileWriter(TIMING_ERR_LOG_FILE_PATH, true);
            PrintWriter pw = new PrintWriter(fw);

            //write start timing log entry
            pw.println(String.format(START_TIMING_LOG_ENTRY,input));

            //timing violation counter
            int violationCtr = 0;

            //recorded time in ms
            final float recordedTimeInMilliSec = timer.getRecordedTimeInNanoSec()/1000000.0f;

            //check for timing violation for the actual input processing
            if(recordedTimeInMilliSec > TIME_CONSTRAINT_IN_MS) {
                //write timing violation entry
                pw.println(String.format(TIMING_VIOLATION_LOG_ENTRY, recordedTimeInMilliSec));
                //increment violation counter
                violationCtr++;
            }

            //turn on simulation button...
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

                //recorded time in ms for simulation
                final float recordedTimeInMilliSecSim = timer.getRecordedTimeInNanoSec()/1000000.0f;

                //check for timing violation
                if(recordedTimeInMilliSecSim > TIME_CONSTRAINT_IN_MS) {
                    //write timing violation entries
                    pw.println(String.format(TIMING_VIOLATION_LOG_ENTRY, recordedTimeInMilliSecSim));
                    //increment violation counter
                    violationCtr++;
                }
            }

            //write timing violations statistics
            pw.println(String.format(STAT_SUMMARY_LOG_ENTRY, violationCtr, input));
            pw.close();
        }
        catch(IOException e) {
            System.out.println(WRITE_TIMING_LOG_ENTRY_ERR);
            System.out.println(EXIT_MSG);
            System.exit(1);
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
            System.out.println(EXIT_MSG);
            System.exit(1);
        }
    }

    //testing BinarySearch class (for debugging purposes only)
    /*private static void testSortRelatedClasses()
    {
        String[] strArr = {"pear","apple","grape","candy","banana"};
        List<String> strList = Arrays.asList(strArr);
        Sort.sortStringList(strList);
        assert SortChecker.checkStringList(strList);
        assert strList.get(0).equals("apple");
        for(String element : strArr) {
            assert BinarySearch.searchStringList(strList, element) >= 0;
        }
        assert BinarySearch.searchStringList(strList, "steak") < 0;
    }*/
}