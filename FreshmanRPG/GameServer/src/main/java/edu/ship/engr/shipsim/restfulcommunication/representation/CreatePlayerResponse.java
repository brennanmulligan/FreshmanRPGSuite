package edu.ship.engr.shipsim.restfulcommunication.representation;

import com.google.gson.Gson;
import edu.ship.engr.shipsim.model.CommandCreatePlayer;

/**
 * @author Derek
 */
public final class CreatePlayerResponse
{

    private final CommandCreatePlayer.CreatePlayerResponseType responseType;
    private final int responseTypeID;

    public CreatePlayerResponse(CommandCreatePlayer.CreatePlayerResponseType responseType)
    {
        this.responseType = responseType;
        this.responseTypeID = responseType.ordinal();
    }

    @Override
    public String toString()
    {
        return new Gson().toJson(this);
    }
}
