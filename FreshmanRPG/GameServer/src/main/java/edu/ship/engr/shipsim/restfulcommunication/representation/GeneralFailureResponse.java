package edu.ship.engr.shipsim.restfulcommunication.representation;

import com.google.gson.Gson;

public class GeneralFailureResponse
{

    public GeneralFailureResponse(String description)
    {
        this.description = description;
    }

    private final String description;

    public String toString()
    {
        return new Gson().toJson(this);
    }
}
