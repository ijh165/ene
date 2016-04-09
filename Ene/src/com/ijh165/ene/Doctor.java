package com.ijh165.ene;

import java.io.*;
import java.util.*;
import java.util.regex.*;

/**
 * Class name: Doctor.java
 * Description: This is the doctor class used to model a doctor and coordinate the doctor interactions.
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

    //attributes
    private List<PatternResponse> patternResponseVec;
    private String prevInput;
    private String prevResponse;
    private boolean isSimulation;

    //constructor
    public Doctor()
    {
        super();
        patternResponseVec = new ArrayList<>();
        prevInput = null;
        prevResponse = null;
        isSimulation = false;
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

    //check for placeholders in pattern, return a hash table which map these placeholders to their occurrence number
    private Map<String,Integer> extractPlaceholders(String pattern)
    {
        Map<String,Integer> placeholderMap = new HashMap<>();
        Pattern p = Pattern.compile("%\\p{Alpha}%", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(pattern);
        int value = 0;
        while(m.find()) {
            value++;
            String tmpStr = pattern.substring(m.start(),m.end());
            placeholderMap.put(tmpStr, value);
        }
        return placeholderMap;
    }

    //find matching patterns and return responses based on matching patterns (default response if no match)
    public void respond(String userInput)
    {
        if(userInput.equals(prevInput) && prevResponse!=null) {
            speak(prevResponse);
            return;
        }
        else if(prevInput!=null && prevResponse==null) {
            speak( DEFAULT_RESPONSES[new Random().nextInt(DEFAULT_RESPONSES.length)] );
            return;
        }

        //store to previous input
        prevInput = userInput;

        List<String> responseVec = new ArrayList<>();
        boolean patternFound = false;

        //check for matching patterns for every pattern/response
        for(PatternResponse patternResponseElement : patternResponseVec) {
            //tmp pattern and response
            String tmpPattern = patternResponseElement.getPattern();
            String tmpResponse = patternResponseElement.getResponse();

            //process placeholders in pattern
            Map<String, Integer> placeholderMap = extractPlaceholders(tmpPattern);
            Set<Map.Entry<String, Integer>> placeholderEntrySet = placeholderMap.entrySet();
            for(Map.Entry<String, Integer> entry : placeholderEntrySet) {
                tmpPattern = tmpPattern.replace(entry.getKey(), "(\\w+)");
            }

            //actually find pattern in user input, also take care of placeholders
            Pattern p = Pattern.compile(tmpPattern, Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(userInput);
            if(m.find()) {
                //well, pattern is found...
                patternFound = true;
                //apply placeholders (if any) into response
                if(placeholderMap.size() > 0) {
                    for (Map.Entry<String, Integer> entry : placeholderEntrySet) {
                        tmpResponse = tmpResponse.replace(entry.getKey(), m.group(entry.getValue()));
                    }
                }
                //add to the response vector
                responseVec.add(tmpResponse);
            }
        }

        //if no pattern is found just print a random default response
        if(!patternFound) {
            prevResponse = null;
            speak( DEFAULT_RESPONSES[new Random().nextInt(DEFAULT_RESPONSES.length)] );
            return;
        }

        //sort multiple responses (in case multiple patterns detected) and combine them to one response
        String[] responseArr = responseVec.toArray(new String[responseVec.size()]);
        if(!SortChecker.checkStringArr(responseArr)) {
            Sort.sortStringArr(responseArr);
        }
        String fullResponse = "";
        for(String responseElement : responseArr) {
            fullResponse += responseElement + " ";
        }

        //store to previous response
        prevResponse = fullResponse;

        //print the response
        speak(fullResponse);
    }

    //speak
    private void speak(String words)
    {
        if(!isSimulation) {
            System.out.println(DOCTOR_STR + words);
        }
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

    public void simulationOn()
    {
        isSimulation = true;
    }

    public void simulationOff()
    {
        isSimulation = false;
    }
}