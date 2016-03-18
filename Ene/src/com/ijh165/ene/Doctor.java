package com.ijh165.ene;

import java.io.*;
import java.util.*;
import java.util.regex.*;

/**
 * Class name: Doctor.java
 * Description: This is the doctor class used to model a doctor and coordinate the doctor interactions
 * Created by IvanJonathan on 2016-02-21.
 */
public class Doctor
{
    //array index constants
    private static final int PATTERN_TOKEN = 0;
    private static final int RESPONSE_TOKEN = 1;

    //output text constants
    private static final String DOCTOR_STR = "Doctor: ";
    private static final String GREETING = "Hello! ^_^";
    private static final String GOODBYE = "Goodbye! Have a nice day! ^_^";
    private static final String NO_INPUT_RESPONSE = "You didn't enter any input, please type again!";
    private static final String[] DEFAULT_RESPONSES = {"I didn't quite get that.",
                                                    "I'm not sure I understand what you're saying.",
                                                    "Sorry, I didn't get that."};

    //list of patterns
    private List<PatternResponse> patternResponseVec;

    //constructor
    public Doctor()
    {
        super();
        patternResponseVec = new ArrayList<>(128); //create empty list of large enough size initially
    }

    //check for placeholders in pattern, return a hash table which map these placeholders to their occurrence number
    private Hashtable<String,Integer> extractPlaceholders(final String pattern)
    {
        Hashtable<String,Integer> placeholderHT = new Hashtable<>(pattern.length());
        Pattern p = Pattern.compile("%\\p{Alpha}%", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(pattern);
        int value = 0;
        while(m.find()) {
            value++;
            String tmpStr = pattern.substring(m.start(),m.end());
            placeholderHT.put(tmpStr, value);
        }
        return placeholderHT;
    }

    //open the pattern file and load the patterns and corresponding responses to the patternResponseVec (return true on success)
    public void loadPatterns(String patternFilePath) throws IOException
    {
        FileReader fr = new FileReader(patternFilePath);
        BufferedReader br = new BufferedReader(fr);
        String lineBuff = br.readLine();
        while(lineBuff != null) {
            if(lineBuff.length()>0) {
                String[] tokens = lineBuff.split("\\s->\\s");
                patternResponseVec.add(new PatternResponse(tokens[PATTERN_TOKEN], tokens[RESPONSE_TOKEN]));
            }
            lineBuff = br.readLine();
        }
        br.close();
    }

    //find matching patterns and return responses based on matching patterns (default response if no match)
    public String respond(final String inputBuff)
    {
        List<String> responseVec = new ArrayList<>();
        boolean patternFound = false;

        //check for matching patterns for every pattern/response
        for(PatternResponse patternResponseElement : patternResponseVec)
        {
            String pattern = patternResponseElement.getPattern();
            String response = patternResponseElement.getResponse();

            //process placeholders in pattern
            Hashtable<String,Integer> placeholderHT = extractPlaceholders(pattern);
            Set<String> placeholderStrSet = placeholderHT.keySet();
            Iterator<String> itr = placeholderStrSet.iterator();
            while(itr.hasNext()) {
                String placeholderStr = itr.next();
                pattern = pattern.replace(placeholderStr, "(\\w+)");
            }

            //actually find pattern in user input, also take care of placeholders
            Pattern p = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(inputBuff);
            if(m.find()) {
                //well, pattern is found...
                patternFound = true;
                //apply placeholders (if any) into response
                if(placeholderHT.size()>0) {
                    placeholderStrSet = placeholderHT.keySet();
                    itr = placeholderStrSet.iterator();
                    while(itr.hasNext()) {
                        String placeholderStr = itr.next();
                        response = response.replace(placeholderStr, m.group(placeholderHT.get(placeholderStr)));
                    }
                }
                //add to the response vector
                responseVec.add(response);
            }
        }

        //if no pattern is found just print a random default response
        if(!patternFound) {
            Random rand = new Random();
            String defaultResponse = DEFAULT_RESPONSES[rand.nextInt(DEFAULT_RESPONSES.length)];
            return defaultResponse;
        }

        //sort multiple responses (in case multiple patterns detected) and combine them to one response
        String[] responseArr = responseVec.toArray(new String[responseVec.size()]);
        Sort.sortStrings(responseArr);
        String response = "";
        for(String responseElement : responseArr) {
            response += responseElement + " ";
        }

        //print the response
        return response;
    }

    //print the words
    public void speak(final String words)
    {
        System.out.println(DOCTOR_STR + words);
    }

    //greet the user
    public void greet()
    {
        speak(GREETING);
    }

    //say goodbye to the user
    public void goodbye()
    {
        speak(GOODBYE);
    }

    //print no input respond
    public void respondNoInput()
    {
        speak(NO_INPUT_RESPONSE);
    }
}