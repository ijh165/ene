package com.ijh165.ene;

/**
 * Created by IvanJonathan on 2016-02-21.
 */
public class PatternResponse
{
    //data
    private String pattern;
    private String response;

    //constructors
    public PatternResponse(String pattern, String response)
    {
        super();
        this.pattern = pattern;
        this.response = response;
    }

    //getters
    public String getPattern()
    {
        return pattern;
    }
    public String getResponse()
    {
        return response;
    }
}