package edu.ship.engr.shipsim.communication.messages;

import edu.ship.engr.shipsim.dataDTO.ClientPlayerObjectiveStateDTO;
import edu.ship.engr.shipsim.dataDTO.ClientPlayerQuestStateDTO;
import edu.ship.engr.shipsim.dataDTO.FriendDTO;
import edu.ship.engr.shipsim.datasource.LevelRecord;
import edu.ship.engr.shipsim.datatypes.FriendStatusEnum;
import edu.ship.engr.shipsim.datatypes.ObjectiveStateEnum;
import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.datatypes.QuestStateEnum;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests the InitializeThisClientsPlayerMessage class
 *
 * @author Olivia, LaVonne
 */
@GameTest("GameShared")
public class InitializeThisClientsPlayerMessageTest
{
    /**
     * Tests that we can create InitializeThisClientsPlayerMessage and get the
     * values of its fields
     */
    @Test
    public void testInitialize()
    {
        ClientPlayerObjectiveStateDTO objectiveOne = new ClientPlayerObjectiveStateDTO(1, "Test Objective 1", 3,
                ObjectiveStateEnum.HIDDEN, false, true, "Dean", QuestStateEnum.AVAILABLE);
        ClientPlayerObjectiveStateDTO objectiveTwo = new ClientPlayerObjectiveStateDTO(2, "Test Objective 2", 3,
                ObjectiveStateEnum.HIDDEN, false, false, null, QuestStateEnum.AVAILABLE);
        ClientPlayerQuestStateDTO q = new ClientPlayerQuestStateDTO(1, "title", "Test Quest 1", QuestStateEnum.AVAILABLE, 42, 13, true,
                false, null);
        q.addObjective(objectiveOne);
        q.addObjective(objectiveTwo);
        ArrayList<ClientPlayerQuestStateDTO> list = new ArrayList<>();
        list.add(q);
        LevelRecord level = new LevelRecord("One", 15, 10, 7);

        ArrayList<FriendDTO> mockFriends = new ArrayList<>();
        FriendDTO friend1 = new FriendDTO(PlayersForTest.MERLIN.getPlayerID(), PlayersForTest.JOHN.getPlayerID(), FriendStatusEnum.ACCEPTED, PlayersForTest.MERLIN.getPlayerName(), PlayersForTest.JOHN.getPlayerName());
        FriendDTO friend2 = new FriendDTO(PlayersForTest.MERLIN.getPlayerID(), PlayersForTest.NICK.getPlayerID(), FriendStatusEnum.ACCEPTED, PlayersForTest.MERLIN.getPlayerName(), PlayersForTest.NICK.getPlayerName());
        mockFriends.add(friend1);
        mockFriends.add(friend2);

        InitializeThisClientsPlayerMessage message = new InitializeThisClientsPlayerMessage(
                PlayersForTest.MERLIN.getPlayerID(), false, list, mockFriends, 20, 50, level);

        assertEquals(20, message.getExperiencePts());
        assertEquals(50, message.getDoubloons());
        assertEquals("One", message.getLevel().getDescription());
        assertEquals(1, message.getClientPlayerQuestList().size());
        ClientPlayerQuestStateDTO firstQuest = message.getClientPlayerQuestList().get(0);
        assertEquals(42, firstQuest.getExperiencePointsGained());
        assertEquals(13, firstQuest.getObjectivesToFulfillment());
        assertTrue(firstQuest.isNeedingNotification());
        ClientPlayerObjectiveStateDTO firstObjective = firstQuest.getObjectiveList().get(0);
        assertTrue(firstObjective.isRealLifeObjective());
        assertEquals("Dean", firstObjective.getWitnessTitle());
        ArrayList<FriendDTO> friends = message.getFriends();
        assertEquals(2, friends.get(0).getPlayerID());
        assertEquals(3, friends.get(1).getFriendID());
        assertEquals(FriendStatusEnum.ACCEPTED, friends.get(0).getStatus());

    }
}
