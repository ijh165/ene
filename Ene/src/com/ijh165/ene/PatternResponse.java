package com.ijh165.ene;

/**
 * Class name: PatternResponse
 * Description: This is the pattern class used to store a pattern and the response. Note that I implement
 *              this class as "PatternResponse" as Java already has a built in class named "Pattern".
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