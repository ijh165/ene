package com.ijh165.ene;

/**
 * Created by IvanJonathan on 2016-02-21.
 */
public class A3
{
    //constants
    private static int TIME_CONSTRAINT = 5;
    private static final String EXIT_MSG = "Exiting program...";

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
                timer.stop();
                continue;
            }
            if(patient.exitingProgram()) {
                doctor.goodbye();
                timer.stop();
                break;
            }

            //reset and start timer
            timer.reset();
            timer.start();

            //pattern matching
            doctor.respond(patient.getInput());

            //stop timer
            timer.stop();

            //check for time violations
            if(timer.getTime() > TIME_CONSTRAINT) {
                System.out.println(timer.getTime());
                System.out.println("Timing violation");
            }
        }
    }
}