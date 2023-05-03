package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.dataDTO.ClientPlayerObjectiveStateDTO;
import edu.ship.engr.shipsim.dataDTO.ClientPlayerQuestStateDTO;
import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datatypes.*;
import edu.ship.engr.shipsim.model.Player;
import edu.ship.engr.shipsim.model.PlayerManager;
import edu.ship.engr.shipsim.model.QuestManager;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import edu.ship.engr.shipsim.testing.annotations.ResetQuestManager;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test the CurrentQuestStateReport
 *
 * @author Ryan, LaVonne, Olivia
 */
@GameTest("GameServer")
@ResetQuestManager
public class UpdatePlayerInformationReportTest
{
    /**
     * Tests that we can combine a quest description and state
     *
     * @throws DatabaseException shouldn't
     */
    @Test
    public void testCombineQuestDescriptionAndState() throws DatabaseException
    {
        Player john = PlayerManager.getSingleton().addPlayer(1);
        UpdatePlayerInformationReport report = new UpdatePlayerInformationReport(john);

        assertEquals(1, report.getPlayerID());
        assertEquals(QuestManager.getSingleton().getQuestList(john.getPlayerID()).size(),
                report.getClientPlayerQuestList().size());

        int i = 1;
        for (ClientPlayerQuestStateDTO q : report.getClientPlayerQuestList())
        {
            if (i == 1)
            {
                assertEquals(QuestsForTest.ONE_BIG_QUEST.getQuestDescription(), q.getQuestDescription());
                assertEquals(QuestStatesForTest.PLAYER1_QUEST1.getState(), q.getQuestState());
            }
            if (i == 2)
            {
                assertEquals(QuestsForTest.THE_OTHER_QUEST.getQuestDescription(), q.getQuestDescription());
                assertEquals(QuestStatesForTest.PLAYER1_QUEST2.getState(), q.getQuestState());
            }
            i++;
        }
    }

    /**
     * Tests that we can combine a quest description and state
     *
     * @throws DatabaseException shouldn't
     */
    @Test
    public void testCombineObjectiveDescriptionAndState() throws DatabaseException
    {
        Player john = PlayerManager.getSingleton().addPlayer(1);
        UpdatePlayerInformationReport report = new UpdatePlayerInformationReport(john);

        int i = 1;
        for (ClientPlayerQuestStateDTO q : report.getClientPlayerQuestList())
        {
            List<ClientPlayerObjectiveStateDTO> objectiveList = q.getObjectiveList();

            if (i == 1)
            {
                int j = 1;
                for (ClientPlayerObjectiveStateDTO a : objectiveList)
                {
                    if (j == 1)
                    {
                        assertEquals(ObjectivesForTest.QUEST1_OBJECTIVE_1.getObjectiveDescription(),
                                a.getObjectiveDescription());
                        assertEquals(ObjectiveStatesForTest.PLAYER1_QUEST1_ADV1.getState(), a.getObjectiveState());
                    }
                    if (j == 2)
                    {
                        assertEquals(ObjectivesForTest.QUEST1_OBJECTIVE2.getObjectiveDescription(),
                                a.getObjectiveDescription());
                        assertEquals(ObjectiveStatesForTest.PLAYER1_QUEST1_ADV2.getState(), a.getObjectiveState());
                    }
                    j++;
                }

            }
            if (i == 2)
            {
                int j = 1;
                for (ClientPlayerObjectiveStateDTO a : objectiveList)
                {
                    if (j == 1)
                    {
                        assertEquals(ObjectivesForTest.QUEST2_OBJECTIVE1.getObjectiveDescription(),
                                a.getObjectiveDescription());
                        assertEquals(ObjectiveStatesForTest.PLAYER1_QUEST2_ADV1.getState(), a.getObjectiveState());
                    }
                    if (j == 2)
                    {
                        assertEquals(ObjectivesForTest.QUEST2_OBJECTIVE2.getObjectiveDescription(),
                                a.getObjectiveDescription());
                        assertEquals(ObjectiveStatesForTest.PLAYER1_QUEST2_ADV2.getState(), a.getObjectiveState());
                    }
                    j++;
                }
            }
            i++;
        }
    }

    /**
     * Tests that we can report a players experience points and their levels
     * description and the points level requires
     *
     * @throws DatabaseException shouldn't
     */
    @Test
    public void testExperienceAndLevelPoints() throws DatabaseException
    {
        Player john = PlayerManager.getSingleton().addPlayer(1);

        UpdatePlayerInformationReport report = new UpdatePlayerInformationReport(john);
        assertEquals(PlayersForTest.JOHN.getExperiencePoints(), report.getExperiencePoints());
        assertEquals(LevelsForTest.TWO.getDescription(), report.getLevel().getDescription());
        assertEquals(LevelsForTest.TWO.getLevelUpPoints(), report.getLevel().getLevelUpPoints());

    }
}
