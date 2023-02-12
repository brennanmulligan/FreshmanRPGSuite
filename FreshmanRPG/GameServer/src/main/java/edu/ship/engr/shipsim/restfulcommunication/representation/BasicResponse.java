package edu.ship.engr.shipsim.restfulcommunication.representation;

import com.google.gson.Gson;

public class BasicResponse
{

    private final String description;
    private final boolean success;
    public BasicResponse(boolean success, String description)
    {
        this.description = description;
        this.success = success;
    }

    public String toString()
    {
        return new Gson().toJson(this);
    }
}
