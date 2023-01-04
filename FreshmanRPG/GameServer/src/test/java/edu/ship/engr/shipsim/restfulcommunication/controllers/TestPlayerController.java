package edu.ship.engr.shipsim.restfulcommunication.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ship.engr.shipsim.dataDTO.ClientPlayerQuestStateDTO;
import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.model.Player;
import edu.ship.engr.shipsim.model.PlayerManager;
import edu.ship.engr.shipsim.model.QuestTestUtilities;
import edu.ship.engr.shipsim.model.reports.PlayerQuestReport;
import edu.ship.engr.shipsim.testing.annotations.*;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * @author Derek
 */
@GameTest("GameServer")
@ResetModelFacade
@ResetQuestManager
@ResetPlayerManager
@ResetReportObserverConnector
public class TestPlayerController
{
    @SuppressWarnings("unchecked")
    @Test
    public void testFetchAllObjectives() throws JsonProcessingException
    {
        Player player = PlayerManager.getSingleton().addPlayerSilently(PlayersForTest.MERLIN.getPlayerID());

        PlayerController controller = mock(PlayerController.class);
        List<ClientPlayerQuestStateDTO> questsForTest = QuestTestUtilities.getQuestsForTest();

        PlayerQuestReport report = new PlayerQuestReport(player, questsForTest);

        when(controller.processAction(any(), any())).thenReturn(report);
        when(controller.fetchAllObjectives(anyInt())).thenCallRealMethod();

        ResponseEntity<Object> objectResponseEntity = controller.fetchAllObjectives(player.getPlayerID());

        // region Verify JSON Data
        Map<String, List<Map<String, Object>>> list = new ObjectMapper().readValue((String) objectResponseEntity.getBody(), Map.class);

        // Verify that we got back the correct number of quests
        List<Map<String, Object>> quests = list.get("quests");
        assertEquals(questsForTest.size(), quests.size());

        // Get quest 2 data
        Map<String, Object> questMap = quests.get(1);
        assertEquals(questMap.get("questID"), 2);

        // Get objective data for quest 2
        List<Map<String, Object>> objectives = (List<Map<String, Object>>) questMap.get("objectives");
        assertEquals(objectives.size(), 5);
        
        // Get objective 2
        Map<String, Object> objectiveData = objectives.get(2);
        assertEquals(objectiveData.get("objectiveID"), 3);
        assertEquals(objectiveData.get("objectiveDescription"), "This is the description for Objective 3 of Quest 2");
        // endregion
    }
}

