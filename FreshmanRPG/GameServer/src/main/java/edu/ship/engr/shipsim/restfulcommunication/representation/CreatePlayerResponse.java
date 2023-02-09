package edu.ship.engr.shipsim.restfulcommunication.representation;

import com.google.gson.Gson;

/**
 * @author Derek
 */
public final class CreatePlayerResponse
{
    private final boolean success;

    public CreatePlayerResponse(boolean success)
    {
        this.success = success;
    }

    @Override
    public String toString()
    {
        return new Gson().toJson(this);
    }
}
