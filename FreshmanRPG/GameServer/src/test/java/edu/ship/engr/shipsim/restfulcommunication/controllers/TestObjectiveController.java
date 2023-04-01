package edu.ship.engr.shipsim.restfulcommunication.controllers;

import edu.ship.engr.shipsim.dataDTO.ClientPlayerObjectiveStateDTO;
import edu.ship.engr.shipsim.dataDTO.ClientPlayerQuestStateDTO;
import edu.ship.engr.shipsim.datatypes.ObjectiveStateEnum;
import edu.ship.engr.shipsim.datatypes.QuestStateEnum;
import edu.ship.engr.shipsim.model.reports.ObjectiveStateChangeReport;
import edu.ship.engr.shipsim.model.reports.PlayerQuestReport;
import edu.ship.engr.shipsim.restfulcommunication.representation.BasicResponse;
import edu.ship.engr.shipsim.restfulcommunication.representation.CompleteObjectiveBody;
import edu.ship.engr.shipsim.restfulcommunication.representation.FetchObjectivesBody;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import edu.ship.engr.shipsim.testing.annotations.ResetModelFacade;
import edu.ship.engr.shipsim.testing.annotations.ResetPlayerManager;
import edu.ship.engr.shipsim.testing.annotations.ResetQuestManager;
import edu.ship.engr.shipsim.testing.annotations.ResetReportObserverConnector;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@GameTest("GameServer")
@ResetModelFacade
@ResetQuestManager
@ResetPlayerManager
@ResetReportObserverConnector
public class TestObjectiveController
{
    @ParameterizedTest
    @MethodSource("fetchObjectivesProvider")
    public void testFetchObjectives(HttpStatus expectedStatus, String expectedJson, List<ClientPlayerQuestStateDTO> quests, boolean realReport)
    {
        PlayerQuestReport questReport = new PlayerQuestReport(null, quests);

        ObjectiveController controller = mock(ObjectiveController.class);
        when(controller.processAction(any(Runnable.class), eq(PlayerQuestReport.class))).thenReturn(
                realReport ? questReport : null
        );
        when(controller.fetchObjectives(any(FetchObjectivesBody.class))).thenCallRealMethod();

        ResponseEntity<Object> objectResponseEntity =
                controller.fetchObjectives(new FetchObjectivesBody(1));

        System.out.println(objectResponseEntity.getBody());

        assertEquals(expectedStatus, objectResponseEntity.getStatusCode());
        assertEquals(expectedJson, objectResponseEntity.getBody());
    }

    @ParameterizedTest
    @MethodSource("completeObjectiveProvider")
    public void testCompleteObjective(HttpStatus expectedStatus, String expectedJson, boolean passingReport)
    {
        ObjectiveStateChangeReport report = new ObjectiveStateChangeReport(1, 1, 1, "quest1objective1", ObjectiveStateEnum.COMPLETED, true, "");

        ObjectiveController controller = mock(ObjectiveController.class);
        when(controller.processAction(any(Runnable.class), eq(ObjectiveStateChangeReport.class))).thenReturn(passingReport ? report : null);
        when(controller.completeObjective(any(CompleteObjectiveBody.class))).thenCallRealMethod();

        ResponseEntity<BasicResponse> objectResponseEntity =
                controller.completeObjective(new CompleteObjectiveBody(1, 1, 1));

        assertEquals(expectedStatus, objectResponseEntity.getStatusCode());
        assertEquals(expectedJson, objectResponseEntity.getBody().toString());
    }

    private static List<ClientPlayerQuestStateDTO> generateQuests(int quests, int objectivesPer)
    {
        List<ClientPlayerQuestStateDTO> questList = Lists.newArrayList();

        for (int i = 1; i <= quests; i++)
        {
            ClientPlayerQuestStateDTO questDTO = new ClientPlayerQuestStateDTO(i, "quest" + i, "quest" + i, QuestStateEnum.TRIGGERED, 0, objectivesPer, false, null, false);

            for (int j = 1; j <= objectivesPer; j++)
            {
                ClientPlayerObjectiveStateDTO objectiveDTO = new ClientPlayerObjectiveStateDTO(j, String.format("quest%dobjective%d", i, j), 0, ObjectiveStateEnum.TRIGGERED, false, true, "", QuestStateEnum.TRIGGERED);
                questDTO.addObjective(objectiveDTO);
            }

            questList.add(questDTO);
        }

        return questList;
    }

    private static Stream<Arguments> fetchObjectivesProvider()
    {
        List<Arguments> arguments = Lists.newArrayList();

        arguments.add(Arguments.of(HttpStatus.OK, "{\"objectives\":[{\"questID\":1,\"objectiveID\":1,\"description\":\"quest1objective1\"}]}", generateQuests(1, 1), true));
        arguments.add(Arguments.of(HttpStatus.OK, "{\"objectives\":[{\"questID\":1,\"objectiveID\":1,\"description\":\"quest1objective1\"},{\"questID\":1,\"objectiveID\":2,\"description\":\"quest1objective2\"}]}", generateQuests(1, 2), true));
        arguments.add(Arguments.of(HttpStatus.OK, "{\"objectives\":[{\"questID\":1,\"objectiveID\":1,\"description\":\"quest1objective1\"},{\"questID\":2,\"objectiveID\":1,\"description\":\"quest2objective1\"}]}", generateQuests(2, 1), true));
        arguments.add(Arguments.of(HttpStatus.INTERNAL_SERVER_ERROR, null, Lists.newArrayList(), false));

        return arguments.stream();
    }

    private static Stream<Arguments> completeObjectiveProvider()
    {
        List<Arguments> arguments = Lists.newArrayList();

        arguments.add(Arguments.of(HttpStatus.OK, "{\"description\":\"Objective complete\",\"success\":true}", true));
        arguments.add(Arguments.of(HttpStatus.INTERNAL_SERVER_ERROR, "{\"description\":\"Objective not completed\",\"success\":false}", false));

        return arguments.stream();
    }
}