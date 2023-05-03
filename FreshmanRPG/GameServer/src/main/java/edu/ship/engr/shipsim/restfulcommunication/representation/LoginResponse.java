package edu.ship.engr.shipsim.restfulcommunication.representation;

import com.google.gson.Gson;
import edu.ship.engr.shipsim.model.reports.RestfulLoginFailedReport;

/**
 * @author Derek
 */
public final class LoginResponse extends BasicResponse
{
    private final int playerID;
    private final String authKey;

    public LoginResponse(int playerID, String authKey)
    {
        super(true, "Login successful");
        this.playerID = playerID;
        this.authKey = authKey;
    }

    public LoginResponse(RestfulLoginFailedReport report)
    {
        super(false, report.getMessage());
        playerID = 0;
        authKey = "";
    }

    @Override
    public String toString()
    {
        return new Gson().toJson(this);
    }
}
