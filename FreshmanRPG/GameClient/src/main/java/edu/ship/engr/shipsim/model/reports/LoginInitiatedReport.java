package edu.ship.engr.shipsim.model.reports;

import java.util.Objects;

/**
 * This report is sent when the player initiates his login to the system
 *
 * @author Merlin
 */
public final class LoginInitiatedReport extends SendMessageReport
{

    private final String name;
    private final String password;

    /**
     * @param name     the player's name
     * @param password the player's password
     */
    public LoginInitiatedReport(String name, String password)
    {
        // Happens on client, thus it will always be loud
        super(0, false);
        this.name = name;
        this.password = password;
    }

    /**
     * @return the password
     */
    public String getPassword()
    {
        return password;
    }

    /**
     * @return the name
     */
    public String getPlayerName()
    {
        return name;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof LoginInitiatedReport that))
        {
            return false;
        }
        return Objects.equals(name, that.name) &&
                Objects.equals(getPassword(), that.getPassword())&&
                this.getRelevantPlayerID() == that.getRelevantPlayerID() &&
                this.isQuiet() == that.isQuiet();
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(name, getPassword(), getRelevantPlayerID(), isQuiet());
    }
}
