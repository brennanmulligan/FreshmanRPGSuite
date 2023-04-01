package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.datasource.DatabaseException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;

@EqualsAndHashCode(callSuper = true)
@Data
public class CommandCompleteObjective extends Command
{
    private final int playerID;
    private final int questID;
    private final int objectiveID;

    @SneakyThrows({DatabaseException.class, IllegalObjectiveChangeException.class, IllegalQuestChangeException.class})
    @Override
    void execute()
    {
        QuestManager.getSingleton().completeObjective(playerID, questID, objectiveID);
        PlayerManager.getSingleton().getPlayerFromID(playerID).persist();
    }
}
