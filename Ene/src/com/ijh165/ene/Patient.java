package com.ijh165.ene;

import java.util.Scanner;

/**
 * Created by IvanJonathan on 2016-02-21.
 */
public class Patient
{
    //constants
    private static final String PROMPT_STR = ">>>> ";
    private static final String EXIT_CMD = "exit";

    //buffer holding the user input
    private String input;

    //constructor
    public Patient()
    {
        super();
    }

    //prompts the user to type some input
    public void promptInput() {
        System.out.print(PROMPT_STR);
        Scanner input = new Scanner(System.in);
        this.input = input.nextLine();
    }

    //check if user wants to exit
    public boolean exitingProgram()
    {
        return input.equals(EXIT_CMD);
    }

    //check if input is empty
    public boolean inputEmpty()
    {
        return (input.length()==0);
    }

    //getters
    public String getInput()
    {
        return input;
    }
}