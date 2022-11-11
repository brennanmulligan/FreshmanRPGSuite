package edu.ship.engr.shipsim.restfulcommunication.representation;

import com.google.gson.Gson;

/**
 * @author Derek
 */
public final class LoginResponse
{
    private final int playerID;
    private final String authKey;

    public LoginResponse(int playerID, String authKey)
    {
        this.playerID = playerID;
        this.authKey = authKey;
    }

    @Override
    public String toString()
    {
        return new Gson().toJson(this);
    }
}
