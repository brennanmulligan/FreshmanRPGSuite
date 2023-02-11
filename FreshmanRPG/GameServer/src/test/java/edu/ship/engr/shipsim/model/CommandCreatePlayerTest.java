package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.dataDTO.ObjectiveStateRecordDTO;
import edu.ship.engr.shipsim.dataDTO.QuestStateRecordDTO;
import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datasource.ObjectiveStateTableDataGateway;
import edu.ship.engr.shipsim.datasource.ObjectiveTableDataGateway;
import edu.ship.engr.shipsim.datasource.PlayerLoginRowDataGateway;
import edu.ship.engr.shipsim.datasource.QuestStateTableDataGateway;
import edu.ship.engr.shipsim.datatypes.Crew;
import edu.ship.engr.shipsim.datatypes.Major;
import edu.ship.engr.shipsim.model.reports.CreatePlayerResponseReport;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import edu.ship.engr.shipsim.testing.annotations.ResetPlayerManager;
import edu.ship.engr.shipsim.testing.annotations.ResetReportObserverConnector;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author Merlin
 */
@GameTest("GameServer")
@ResetPlayerManager
@ResetReportObserverConnector
public class CommandCreatePlayerTest
{
    /**
     * Works when request is valid
     */
    @Test
    public void testValidPlayer() throws DatabaseException
    {
        ReportObserver obs = mock( ReportObserver.class);
        ReportObserverConnector.getSingleton().registerObserver(obs, CreatePlayerResponseReport.class);

        CommandCreatePlayer cmd = new CommandCreatePlayer("name", "pw", Crew.OUT_OF_BOUNDS.getID(),
                Major.BIOLOGY.getID(), 42);
        cmd.execute();
        PlayerLoginRowDataGateway loginRowDataGateway = new PlayerLoginRowDataGateway("name");
        assertTrue( loginRowDataGateway.checkPassword("pw"));
        int playerID = loginRowDataGateway.getPlayerID();
        Player player = new PlayerMapper(playerID).getPlayer();
        assertEquals(Crew.OUT_OF_BOUNDS,player.getCrew());
        assertEquals(Major.BIOLOGY, player.getMajor());
        assertEquals("sortingRoom.tmx", player.getMapName());

        ArrayList<QuestStateRecordDTO> questStates = QuestStateTableDataGateway.getSingleton().getQuestStates(playerID);
        assertEquals(3, questStates.size());
        for(QuestStateRecordDTO questState:questStates)
        {
            ArrayList<ObjectiveStateRecordDTO> objectiveStates =
                    ObjectiveStateTableDataGateway.getSingleton().getObjectiveStates(playerID,questState.getQuestID());
            ArrayList<ObjectiveRecord> shouldHaveObjectives = ObjectiveTableDataGateway.getSingleton().getObjectivesForQuest(questState.getQuestID());
            assertEquals(shouldHaveObjectives.size(), objectiveStates.size());
        }

        verify(obs, times(1)).receiveReport(new CreatePlayerResponseReport(true));
    }
}
