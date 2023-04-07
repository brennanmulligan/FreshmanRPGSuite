package edu.ship.engr.shipsim.model.reports;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Merlin
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true, exclude = "pin")
public final class LoginSuccessfulReport extends SendMessageReport
{

    private final String hostname;
    private final int port;
    private final double pin;
    private final int playerID;

    /**
     * @param playerID the playerID who was successful
     * @param hostname the hostname of the area server the client should connect
     *                 to
     * @param port     the port number of the area server the client should connect
     *                 to
     * @param d        the pin the client should use in its connection
     */
    public LoginSuccessfulReport(int playerID, String hostname, int port, double d)
    {
        super(playerID, true);
        this.hostname = hostname;
        this.port = port;
        this.pin = d;
        this.playerID = playerID;
    }

    /**
     * @return the hostname
     */
    public String getHostname()
    {
        return hostname;
    }

    /**
     * @return the pin
     */
    public double getPin()
    {
        return pin;
    }

    /**
     * @return the port
     */
    public int getPort()
    {
        return port;
    }

    /**
     * @return the player ID in this report
     */
    public int getPlayerID()
    {
        return playerID;
    }
}
