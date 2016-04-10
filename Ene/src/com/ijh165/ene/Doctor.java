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
    private static final String NO_INPUT_RESPONSE = "...? Say something!";
    private static final String[] DEFAULT_RESPONSES = {"Sorry, I can't understand what you're saying!",
                                                       "I don't know what to say... My creator didn't expect you to type that!",
                                                       "I didn't quite get that. Tell me something else!"};

    //attributes
    private List<PatternResponse> patternResponseVec;
    private String prevInputStr;
    private List<String> prevResponseList;
    private boolean isSimulation;

    //constructor
    public Doctor()
    {
        super();
        patternResponseVec = new ArrayList<>();
        prevInputStr = null;
        prevResponseList = null;
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
    public void respond(String input)
    {
        if(input.equals(prevInputStr)) {
            if(prevResponseList!=null) {
                for (String responseElement : prevResponseList) {
                    speak(responseElement);
                }
            } else {
                speak( DEFAULT_RESPONSES[new Random().nextInt(DEFAULT_RESPONSES.length)] );
            }
            return;
        }

        //store to previous input
        prevInputStr = input;

        //variables
        List<String> responseList = new ArrayList<>();
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
            Matcher m = p.matcher(input);
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
                responseList.add(tmpResponse);
            }
        }

        //if no pattern is found just print a random default response
        if(!patternFound) {
            prevResponseList = null;
            speak( DEFAULT_RESPONSES[new Random().nextInt(DEFAULT_RESPONSES.length)] );
            return;
        }

        //sort (if needed) and display responses
        String[] responseArr = responseList.toArray(new String[responseList.size()]);
        if(!SortChecker.checkStringArr(responseArr)) {
            Sort.sortStringArr(responseArr);
        }
        for(String responseElement : responseArr) {
            speak(responseElement);
        }

        //store to previous response
        prevResponseList = Arrays.asList(responseArr);
    }

    //speak
    private void speak(String words)
    {
        if(!isSimulation) {
            System.out.println(DOCTOR_STR + words);
        }
    }

    //greet the user (never a simulation)
    public void greet()
    {
        simulationOff();
        speak(GREETING);
    }

    //say goodbye to the user (never a simulation)
    public void goodbye()
    {
        simulationOff();
        speak(GOODBYE);
    }

    //display no input respond (never a simulation)
    public void respondNoInput()
    {
        simulationOff();
        speak(NO_INPUT_RESPONSE);
    }

    //turn on isSimulation boolean
    public void simulationOn()
    {
        isSimulation = true;
    }

    //turn off isSimulation boolean
    public void simulationOff()
    {
        isSimulation = false;
    }
}