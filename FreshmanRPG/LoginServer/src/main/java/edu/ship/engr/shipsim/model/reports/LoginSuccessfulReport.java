package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.model.Report;

/**
 * @author Merlin
 */
public final class LoginSuccessfulReport implements Report
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
        this.hostname = hostname;
        this.port = port;
        this.pin = d;
        this.playerID = playerID;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        LoginSuccessfulReport other = (LoginSuccessfulReport) obj;
        if (hostname == null)
        {
            if (other.hostname != null)
            {
                return false;
            }
        }
        else if (!hostname.equals(other.hostname))
        {
            return false;
        }
        if (port != other.port)
        {
            return false;
        }
        if (playerID != other.playerID)
        {
            return false;
        }
        return true;
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

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((hostname == null) ? 0 : hostname.hashCode());
        result = prime * result + port;
        result = prime * result + playerID;
        return result;
    }

}
