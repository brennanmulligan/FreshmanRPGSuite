package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.datasource.LevelRecord;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * The ExperienceChangedReport class
 *
 * @author Olivia
 * @author LaVonne
 */
@EqualsAndHashCode(callSuper = true)
public final class ExperienceChangedReport extends SendMessageReport
{
    @Getter private final int experiencePoints;
    @Getter private final LevelRecord record;
    @Getter private final int playerID;

    /**
     * @param playerID         the player's id
     * @param experiencePoints experience points of the player
     * @param record           level record of the player
     */
    public ExperienceChangedReport(int playerID, int experiencePoints, LevelRecord record)
    {
        super(playerID, true);
        this.experiencePoints = experiencePoints;
        this.record = record;
        this.playerID = playerID;
    }
}
