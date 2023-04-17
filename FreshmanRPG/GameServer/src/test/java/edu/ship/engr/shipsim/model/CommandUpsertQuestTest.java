package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.dataDTO.ObjectiveStateRecordDTO;
import edu.ship.engr.shipsim.dataDTO.QuestStateRecordDTO;
import edu.ship.engr.shipsim.dataENUM.QuestCompletionActionType;
import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datasource.DuplicateNameException;
import edu.ship.engr.shipsim.datasource.ObjectiveStateTableDataGateway;
import edu.ship.engr.shipsim.datasource.ObjectiveTableDataGateway;
import edu.ship.engr.shipsim.datasource.QuestRowDataGateway;
import edu.ship.engr.shipsim.datasource.QuestStateTableDataGateway;
import edu.ship.engr.shipsim.datasource.QuestTableDataGateway;
import edu.ship.engr.shipsim.datatypes.Crew;
import edu.ship.engr.shipsim.datatypes.Major;
import edu.ship.engr.shipsim.datatypes.Position;
import edu.ship.engr.shipsim.model.reports.CreatePlayerResponseReport;
import edu.ship.engr.shipsim.model.reports.UpsertQuestResponseReport;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import edu.ship.engr.shipsim.testing.annotations.ResetPlayerManager;
import edu.ship.engr.shipsim.testing.annotations.ResetReportObserverConnector;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author Scott Bucher
 */
@GameTest("GameServer")
@ResetPlayerManager
@ResetReportObserverConnector
public class CommandUpsertQuestTest
{

    @Test
    public void testCreateAndUpdateQuest()
            throws DatabaseException, DuplicateNameException, ParseException
    {
        ReportObserver obs = mock(ReportObserver.class);
        ReportObserverConnector.getSingleton()
                .registerObserver(obs, UpsertQuestResponseReport.class);

        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");

        CommandUpsertQuest cmd = new CommandUpsertQuest(
                new QuestRecord(-1, "Test Quest", "Test Quest", "map1.tmx",
                        new Position(1, 2), null, 420, 0,
                        QuestCompletionActionType.NO_ACTION,
                        null, formatter.parse("03-01-1996"),
                        formatter.parse("11-09-2001"), true));
        cmd.execute();

        QuestTableDataGateway questTableDataGateway =
                QuestTableDataGateway.getSingleton();
        ArrayList<QuestRecord> quests = questTableDataGateway.getAllQuests();
        QuestRowDataGateway questRowDataGateway = new QuestRowDataGateway(
                quests.get(quests.size() - 1).getQuestID());

        assertEquals("Test Quest", questRowDataGateway.getQuestTitle());
        assertEquals("Test Quest", questRowDataGateway.getQuestDescription());
        assertEquals("map1.tmx", questRowDataGateway.getTriggerMapName());
        assertEquals(1, questRowDataGateway.getTriggerPosition().getRow());
        assertEquals(2, questRowDataGateway.getTriggerPosition().getColumn());
        assertEquals(420, questRowDataGateway.getExperiencePointsGained());
        assertEquals(0, questRowDataGateway.getObjectivesForFulfillment());
        assertEquals(QuestCompletionActionType.NO_ACTION,
                questRowDataGateway.getCompletionActionType());
        assertEquals(formatter.parse("03-01-1996"),
                questRowDataGateway.getStartDate());
        assertEquals(formatter.parse("11-09-2001"),
                questRowDataGateway.getEndDate());
        assertTrue(questRowDataGateway.isEasterEgg());

        // Update the quest we just created with slightly altered values
        cmd = new CommandUpsertQuest(
                new QuestRecord(questRowDataGateway.getQuestID(),
                        "Updated Quest", "Updated Quest", "map2.tmx",
                        new Position(2, 1), null, 100, 0,
                        QuestCompletionActionType.NO_ACTION,
                        null, formatter.parse("11-09-2001"),
                        formatter.parse("03-01-1996"), false));

        cmd.execute();

        questRowDataGateway = new QuestRowDataGateway(
                quests.get(quests.size() - 1).getQuestID());

        assertEquals("Updated Quest", questRowDataGateway.getQuestTitle());
        assertEquals("Updated Quest",
                questRowDataGateway.getQuestDescription());
        assertEquals("map2.tmx", questRowDataGateway.getTriggerMapName());
        assertEquals(2, questRowDataGateway.getTriggerPosition().getRow());
        assertEquals(1, questRowDataGateway.getTriggerPosition().getColumn());
        assertEquals(100, questRowDataGateway.getExperiencePointsGained());
        assertEquals(0, questRowDataGateway.getObjectivesForFulfillment());
        assertEquals(QuestCompletionActionType.NO_ACTION,
                questRowDataGateway.getCompletionActionType());
        assertEquals(formatter.parse("11-09-2001"),
                questRowDataGateway.getStartDate());
        assertEquals(formatter.parse("03-01-1996"),
                questRowDataGateway.getEndDate());
        assertFalse(questRowDataGateway.isEasterEgg());

        verify(obs, times(2)).receiveReport(
                new UpsertQuestResponseReport(true));
    }

}
