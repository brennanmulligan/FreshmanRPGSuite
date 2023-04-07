package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.datatypes.Position;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * This is the ChangeMapReport contains information for connecting to a server.
 *
 * @author Steve
 */
@EqualsAndHashCode(callSuper = true)
public final class ChangeMapReport extends SendMessageReport
{
    @Getter private final int playerID;
    @Getter private final Position position;
    @Getter private final String mapName;


    /**
     * @param playerID the ID of the player teleporting
     * @param position The position to connect to
     * @param mapName  is the Map the player is going to
     */
    public ChangeMapReport(int playerID, Position position, String mapName)
    {
        // Happens on client, thus it will always be loud
        super(0, false);

        this.playerID = playerID;
        this.position = position;
        this.mapName = mapName;
    }
}
