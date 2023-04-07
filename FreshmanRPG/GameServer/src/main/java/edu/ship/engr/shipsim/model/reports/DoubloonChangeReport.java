package edu.ship.engr.shipsim.model.reports;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * @author Matthew Croft
 */
@EqualsAndHashCode(callSuper = true)
public final class DoubloonChangeReport extends SendMessageReport
{
    @Getter private final int doubloons;
    @Getter private final int playerID;
    @Getter private final int buffPool;

    /**
     * @param playerID  of the current player
     * @param doubloons of the player
     * @param buffPool  the current value of the players remaining bonus points
     */
    public DoubloonChangeReport(int playerID, int doubloons, int buffPool)
    {
        super(playerID, true);
        this.playerID = playerID;
        this.doubloons = doubloons;
        this.buffPool = buffPool;
    }
}
