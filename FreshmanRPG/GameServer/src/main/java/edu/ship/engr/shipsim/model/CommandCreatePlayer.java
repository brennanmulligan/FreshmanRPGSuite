package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datasource.ObjectiveStateTableDataGateway;
import edu.ship.engr.shipsim.datasource.ObjectiveTableDataGateway;
import edu.ship.engr.shipsim.datasource.QuestStateTableDataGateway;
import edu.ship.engr.shipsim.datatypes.Crew;
import edu.ship.engr.shipsim.datatypes.Major;
import edu.ship.engr.shipsim.datatypes.ObjectiveStateEnum;
import edu.ship.engr.shipsim.datatypes.Position;
import edu.ship.engr.shipsim.datatypes.QuestStateEnum;
import edu.ship.engr.shipsim.datatypes.QuestsForProduction;
import edu.ship.engr.shipsim.model.reports.CreatePlayerResponseReport;

import java.util.ArrayList;

public class CommandCreatePlayer extends Command
{
    private final String password;
    private final Crew crew;
    private final Major major;
    private final int section;
    private final String name;

    public CommandCreatePlayer(String playerName, String password, int crewNum, int majorNum, int section)
    {
        this.name = playerName;
        this.password = password;
        this.crew = Crew.getCrewForID(crewNum);
        this.major = Major.getMajorForID(majorNum);
        this.section = section;
    }
    private static final QuestsForProduction[] questsToTrigger =
            {QuestsForProduction.ONRAMPING_QUEST,
                    QuestsForProduction.SCAVENGER_HUNT, QuestsForProduction.EVENTS};

    @Override
    void execute()
    {
        if(!CheckPassword.checkPassword(password))
        {
            ReportObserverConnector.getSingleton().sendReport(new CreatePlayerResponseReport(false, "Bad Password"));
            return;
        }

        try
        {
            PlayerMapper mapper = new PlayerMapper(new Position(11, 7), "default_player", 0, 0,
                    crew, major, section, name, password);
            triggerInitialQuests(mapper.getPlayer().getPlayerID());
        }
        catch (DatabaseException e)
        {

            ReportObserverConnector.getSingleton().sendReport(new CreatePlayerResponseReport(false, "Duplicate Name"));
        }

        ReportObserverConnector.getSingleton().sendReport(new CreatePlayerResponseReport(true));

    }

    private void triggerInitialQuests(int playerID) throws DatabaseException
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
