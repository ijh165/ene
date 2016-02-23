package com.ijh165.ene;

import java.io.*;
import java.util.*;
import java.util.regex.*;

/**
 * Created by IvanJonathan on 2016-02-21.
 */
public class Doctor
{
    //array index constants
    private static final int PATTERN_TOKEN = 0;
    private static final int RESPONSE_TOKEN = 1;
    //file names constants
    private static final String PATTERNS_FNAME = "patterns.txt";
    //output text constants
    private static final String DOCTOR_STR = "Doctor: ";
    private static final String GREETING = "Hello! ^_^";
    private static final String GOODBYE = "Goodbye! Have a nice day! ^_^";
    private static final String NO_INPUT_RESPONSE = "You didn't enter any input, please type again! ^_^";
    private static final String[] DEFAULT_RESPONSES = {"I didn't quite get that.",
                                                    "I'm not sure I understand what you're saying.",
                                                    "Sorry, I didn't get that."};

    //list of patterns
    private List<PatternResponse> patternResponseVec;

    //constructor
    public Doctor()
    {
        patternResponseVec = new ArrayList<>(128); //create empty list of large enough size initially
    }

    //output the words
    private void speak(final String words)
    {
        System.out.println(DOCTOR_STR + words);
    }

    //check for placeholders
    /*private boolean processPlaceholders(String pattern, String response)
    {
        Pattern p = Pattern.compile("%\\p{Alpha}%");
        Matcher m = p.matcher(pattern);

        while(m.find()) {

        }
    }*/

    //open the pattern file and load the patterns and corresponding responses to the patternResponseVec (return true on success)
    public void loadPatterns() throws IOException
    {
        FileReader fr = new FileReader(PATTERNS_FNAME);
        BufferedReader br = new BufferedReader(fr);
        String lineBuff = br.readLine();
        while(lineBuff != null) {
            String[] tokens = lineBuff.split("\\s->\\s");
            patternResponseVec.add(new PatternResponse(tokens[PATTERN_TOKEN], tokens[RESPONSE_TOKEN]));
            lineBuff = br.readLine();
        }
        br.close();
    }

    //find matching patterns and return responses vector based on matching patterns (default response if no match)
    public void respond(final String inputBuff)
    {
        List<String> responseVec = new ArrayList<>();
        boolean patternFound = false;

        //check for matching patterns
        for(PatternResponse patternResponseElement : patternResponseVec) {
            String pattern = patternResponseElement.getPattern();
            String response = patternResponseElement.getResponse();

            //do something for placeholders here

            Pattern p = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(inputBuff);
            if(m.find()) {
                patternFound = true;
                responseVec.add(response);
            }
        }

        //if no pattern is found just print a random default response
        if(!patternFound) {
            Random rand = new Random();
            speak(DEFAULT_RESPONSES[rand.nextInt(DEFAULT_RESPONSES.length)]);
            return;
        }

        //sort multiple responses (in case multiple patterns detected) and combine them to one response
        String[] responseArr = responseVec.toArray(new String[responseVec.size()]);
        Sort.sort(responseArr);
        String response = "";
        for(String responseElement : responseArr) {
            response += responseElement;
        }

        //print the respond
        speak(response);
    }



    //special cases output
    public void greet()
    {
        speak(GREETING);
    }
    public void goodbye()
    {
        speak(GOODBYE);
    }
    public void respondNoInput()
    {
        speak(NO_INPUT_RESPONSE);
    }
}