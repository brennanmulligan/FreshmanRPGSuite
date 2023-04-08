package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.dataENUM.QuestCompletionActionType;
import edu.ship.engr.shipsim.datatypes.QuestsForTest;
import edu.ship.engr.shipsim.model.reports.QuestCreatedOrUpdatedReport;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class CommandCreateUpdateQuestTest
{
    QuestsForTest quest = QuestsForTest.ONE_BIG_QUEST;
    QuestCompletionActionType actionType = QuestCompletionActionType.TELEPORT;
    @Test
    public void createAndUpdateQuest()
    {
        ReportObserver obs = mock(ReportObserver.class);
        ReportObserverConnector.getSingleton().registerObserver(obs,
                QuestCreatedOrUpdatedReport.class);

        //Test for Updating and Existing Quest 't0'
        CommandCreateUpdateQuest comm = new CommandCreateUpdateQuest("t0",
                quest.getQuestDescription(), quest.getMapName(), quest.getPosition(),
                quest.getExperienceGained(), quest.getObjectiveForFulfillment(),
                actionType, quest.getCompletionActionParameter(),
                quest.getStartDate(), quest.getEndDate(), quest.getIsEasterEgg());

        comm.execute();
        verify(obs, times(1)).receiveReport(new QuestCreatedOrUpdatedReport(true));

        //Test for Creating a Quest 't100' that doesn't already exist
        comm = new CommandCreateUpdateQuest("t100",
                quest.getQuestDescription(), quest.getMapName(), quest.getPosition(),
                quest.getExperienceGained(), quest.getObjectiveForFulfillment(),
                actionType, quest.getCompletionActionParameter(),
                quest.getStartDate(), quest.getEndDate(), quest.getIsEasterEgg());
        comm.execute();
        verify(obs, times(2)).receiveReport(new QuestCreatedOrUpdatedReport(true));
    }
}
