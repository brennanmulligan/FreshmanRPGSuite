package edu.ship.engr.shipsim.model.reports;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * This report is sent when the player initiates his login to the system
 *
 * @author Merlin
 */
@EqualsAndHashCode(callSuper = true)
public final class LoginInitiatedReport extends SendMessageReport
{
    @Getter private final String playerName;
    @Getter private final String password;

    /**
     * @param name     the player's name
     * @param password the player's password
     */
    public LoginInitiatedReport(String name, String password)
    {
        // Happens on client, thus it will always be loud
        super(0, false);
        this.playerName = name;
        this.password = password;
    }
}
