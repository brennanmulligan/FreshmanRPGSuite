package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.criteria.ObjectiveCompletionCriteria;
import edu.ship.engr.shipsim.dataDTO.ObjectiveStateRecordDTO;
import edu.ship.engr.shipsim.dataDTO.QuestStateRecordDTO;
import edu.ship.engr.shipsim.dataENUM.ObjectiveCompletionType;
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
            throws DatabaseException, ParseException
    {
        ReportObserver obs = mock(ReportObserver.class);
        ReportObserverConnector.getSingleton()
                .registerObserver(obs, UpsertQuestResponseReport.class);

        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");

        ArrayList<ObjectiveRecord> objectives = new ArrayList<>();

        objectives.add(new ObjectiveRecord(-1, -1, "Test Objective",
                1, ObjectiveCompletionType.CHAT, null));
        objectives.add(new ObjectiveRecord(-1, -1, "Test Objective 2",
                2, ObjectiveCompletionType.MOVEMENT,
                null));

        CommandUpsertQuest cmd = new CommandUpsertQuest(
                new QuestRecord(-1, "Test Quest", "Test Quest", "map1.tmx",
                        new Position(1, 2), objectives, 420, 0,
                        QuestCompletionActionType.NO_ACTION,
                        null, formatter.parse("03-01-1996"),
                        formatter.parse("07-15-2014"), true));
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
        assertEquals(formatter.parse("07-15-2014"),
                questRowDataGateway.getEndDate());
        assertTrue(questRowDataGateway.isEasterEgg());

        ObjectiveTableDataGateway objectiveTableDataGateway =
                ObjectiveTableDataGateway.getSingleton();

        ArrayList<ObjectiveRecord> objectiveRecords =
                objectiveTableDataGateway.getObjectivesForQuest(quests.get(quests.size() - 1).getQuestID());

        assertEquals(2, objectiveRecords.size());

        ObjectiveRecord objectiveRecord = objectiveRecords.get(0);
        ObjectiveRecord objectiveRecord2 = objectiveRecords.get(1);

        assertEquals("Test Objective", objectiveRecord.getObjectiveDescription());
        assertEquals("Test Objective 2", objectiveRecord2.getObjectiveDescription());

        objectiveRecord.setObjectiveDescription("Updated Objective");
        objectiveRecord2.setObjectiveDescription("Updated Objective 2");

        objectives = new ArrayList<>();
        objectives.add(objectiveRecord);
        objectives.add(objectiveRecord2);

        // Update the quest we just created with slightly altered values
        cmd = new CommandUpsertQuest(
                new QuestRecord(questRowDataGateway.getQuestID(),
                        "Updated Quest", "Updated Quest", "map2.tmx",
                        new Position(2, 1), objectives, 100, 0,
                        QuestCompletionActionType.NO_ACTION,
                        null, formatter.parse("07-15-2014"),
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
        assertEquals(formatter.parse("07-15-2014"),
                questRowDataGateway.getStartDate());
        assertEquals(formatter.parse("03-01-1996"),
                questRowDataGateway.getEndDate());
        assertFalse(questRowDataGateway.isEasterEgg());

        objectiveRecords = objectiveTableDataGateway.getObjectivesForQuest(quests.get(quests.size() - 1).getQuestID());

        assertEquals(2, objectiveRecords.size());

        objectiveRecord = objectiveRecords.get(0);
        objectiveRecord2 = objectiveRecords.get(1);
        assertEquals("Updated Objective", objectiveRecord.getObjectiveDescription());
        assertEquals("Updated Objective 2", objectiveRecord2.getObjectiveDescription());

        verify(obs, times(2)).receiveReport(
                new UpsertQuestResponseReport(true));
    }

}
