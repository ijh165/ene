package com.ijh165.ene;

import java.io.IOException;

public class A3
{
    //constants
    private static final String LOAD_PATTERNS_FAIL_ERR = "Failed to load patterns. Make sure the pattern file \"patterns.txt\" exist! Quitting program...";

    //doctor and patient
    private static Doctor doctor;
    private static Patient patient;

    public static void main(String[] args)
    {
        //initialize doctor and patient objects
        doctor = new Doctor();
        patient =  new Patient();

        //start event loop
        try {
            doctor.loadPatterns();
        }
        catch (IOException e) {
            System.out.println(LOAD_PATTERNS_FAIL_ERR);
            return;
        }
        doctor.greet();
        while(true)
        {
            //prompts the user to input stuff
            patient.promptInput();

            //special cases
            if(patient.inputEmpty()) {
                doctor.respondNoInput();
                continue;
            }
            if(patient.exitingProgram()) {
                doctor.goodbye();
                break;
            }

            //pattern matching
            doctor.respond(patient.getInput());
        }
    }
}