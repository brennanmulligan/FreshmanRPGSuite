package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datasource.ObjectiveStateTableDataGateway;
import edu.ship.engr.shipsim.datasource.ObjectiveTableDataGateway;
import edu.ship.engr.shipsim.datasource.QuestStateTableDataGateway;
import edu.ship.engr.shipsim.datatypes.ObjectiveStateEnum;
import edu.ship.engr.shipsim.datatypes.QuestStateEnum;
import edu.ship.engr.shipsim.datatypes.QuestsForProduction;
import edu.ship.engr.shipsim.model.reports.CreatePlayerResponseReport;

import java.util.ArrayList;

public class CommandCreatePlayer extends Command
{

    private final String password;
    private String playerName;

    public CommandCreatePlayer(String playerName, String password)
    {
        this.playerName = playerName;
        this.password = password;
    }
    private static final QuestsForProduction[] questsToTrigger =
            {QuestsForProduction.ONRAMPING_QUEST,
                    QuestsForProduction.SCAVENGER_HUNT, QuestsForProduction.EVENTS};

    @Override
    void execute()
    {
        ReportObserverConnector.getSingleton().sendReport(new CreatePlayerResponseReport(true));
        return ;
    }  private void triggerInitialQuests(int playerID) throws DatabaseException
{
    QuestStateTableDataGateway questStateTableDataGatewayRDS =
            QuestStateTableDataGateway.getSingleton();
    ObjectiveStateTableDataGateway objectiveStateTableDataGateway =
            ObjectiveStateTableDataGateway.getSingleton();
    ObjectiveTableDataGateway objectiveTableDataGateway =
            ObjectiveTableDataGateway.getSingleton();

    for (QuestsForProduction q : questsToTrigger)
    {
        questStateTableDataGatewayRDS.updateState(playerID, q.getQuestID(),
                QuestStateEnum.TRIGGERED, true);

        //Add relevant Objectives
        ArrayList<ObjectiveRecord> objectiveList =
                objectiveTableDataGateway.getObjectivesForQuest(q.getQuestID());
        for (ObjectiveRecord objective : objectiveList)
        {
            objectiveStateTableDataGateway.updateState(playerID, q.getQuestID(),
                    objective.getObjectiveID(), ObjectiveStateEnum.TRIGGERED,
                    false);
        }
    }
}

}
