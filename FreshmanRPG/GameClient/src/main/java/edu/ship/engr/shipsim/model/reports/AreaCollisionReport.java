package edu.ship.engr.shipsim.model.reports;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * @author nhydock
 */
@EqualsAndHashCode(callSuper = true)
public final class AreaCollisionReport extends SendMessageReport
{
    @Getter private final int playerID;
    @Getter private final String areaName;

    /**
     * @param playerID The player who moved
     * @param areaName The name of the area the player has stepped into
     */
    public AreaCollisionReport(int playerID, String areaName)
    {
        super(playerID, true);
        this.playerID = playerID;
        this.areaName = areaName;
    }
}
