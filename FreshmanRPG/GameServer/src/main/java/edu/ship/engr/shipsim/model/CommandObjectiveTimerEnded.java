package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datatypes.ObjectiveStateEnum;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * author: Seth
 */
public class CommandObjectiveTimerEnded extends Command
{
    private final int questID;
    private final int objectiveID;
    private final int playerID;
    public CommandObjectiveTimerEnded(int questID, int objectiveID, int playerID)
    {
        this.questID = questID;
        this.objectiveID = objectiveID;
        this.playerID = playerID;
    }

    void execute()
    {
        try
        {
            QuestState questState = QuestManager.getSingleton().getQuestStateByID(playerID, questID);
            ArrayList<ObjectiveState> objStates = questState.getObjectiveList();

            for (Iterator<ObjectiveState> it = objStates.iterator();
                 it.hasNext(); )
            {
                ObjectiveState objState = it.next();

                if (objState.getID() == this.objectiveID)
                {
                    if (objState.getState() == ObjectiveStateEnum.TRIGGERED)
                    {
                        objState.missed();
                    }
                }
            }
        }
        catch (DatabaseException e)
        {
            throw new RuntimeException(e);
        }
        catch (IllegalQuestChangeException e)
        {
            throw new RuntimeException(e);
        }
        catch (IllegalObjectiveChangeException e)
        {
            throw new RuntimeException(e);
        }
    }
}
