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

        if(!checkPassword()) {
            ReportObserverConnector.getSingleton().sendReport(new CreatePlayerResponseReport(false, "Bad Password");
        }
    }
    private static final QuestsForProduction[] questsToTrigger =
            {QuestsForProduction.ONRAMPING_QUEST,
                    QuestsForProduction.SCAVENGER_HUNT, QuestsForProduction.EVENTS};

    @Override
    void execute()
    {
        try
        {

            PlayerMapper mapper = new PlayerMapper(new Position(11, 7), "default_player", 0, 0,
                    crew, major, section, name, password);
            triggerInitialQuests(mapper.getPlayer().getPlayerID());
        }
        catch (DatabaseException e)
        {
            throw new RuntimeException(e);
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

    private boolean checkPassword() {
        /**
         * Password Requirements
         * 8-16 characters in length
         * 1+ capital letters
         * 1+ lowercase letters
         * 1+ special characters
         */

        // 8 - 16 char length
        if(password.length() < 8 || password.length() > 16)
                return false;

        // Has capital
        char current;
        boolean hasCapital = false, hasLowercase = false, hasSpecial = false;
        for(int i = 0; i < password.length(); i++) {
            current = password.charAt(i);
            if(Character.isUpperCase(current))
                hasCapital = true;
            else if(Character.isLowerCase(current))
                hasLowercase = true;
            else
                hasSpecial = true;
        }

        return (hasCapital && hasLowercase && hasSpecial);
    }

}
