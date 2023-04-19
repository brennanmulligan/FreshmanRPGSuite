package edu.ship.engr.shipsim.restfulcommunication.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import edu.ship.engr.shipsim.dataDTO.ActionTypeDTO;
import edu.ship.engr.shipsim.dataDTO.QuestEditingInfoDTO;
import edu.ship.engr.shipsim.dataDTO.QuestRecordDTO;
import edu.ship.engr.shipsim.dataENUM.QuestCompletionActionType;
import edu.ship.engr.shipsim.datatypes.Position;
import edu.ship.engr.shipsim.model.reports.GetQuestInformationReport;
import edu.ship.engr.shipsim.model.reports.UpsertQuestResponseReport;
import edu.ship.engr.shipsim.restfulcommunication.representation.UpsertQuestInformation;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import edu.ship.engr.shipsim.testing.annotations.ResetModelFacade;
import edu.ship.engr.shipsim.testing.annotations.ResetPlayerManager;
import edu.ship.engr.shipsim.testing.annotations.ResetQuestManager;
import edu.ship.engr.shipsim.testing.annotations.ResetReportObserverConnector;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@GameTest("GameServer")
@ResetModelFacade
@ResetQuestManager
@ResetPlayerManager
@ResetReportObserverConnector
public class TestQuestController
{

    @Test
    public void goodResponseUpsertQuest()
    {
        QuestController mock = mock(QuestController.class);
        when(mock.processAction(any(Runnable.class), eq(
                UpsertQuestResponseReport.class))).thenReturn(
                new UpsertQuestResponseReport(true));
        when(mock.upsertQuest(
                any(UpsertQuestInformation.class))).thenCallRealMethod();

        ResponseEntity<Object> response = mock.upsertQuest(
                new UpsertQuestInformation(-1, "Test Quest", "Test Quest", 420,
                        "map1.tmx",
                        1, 2, 0,
                        1,
                        "01-03-1996",

                        "07-15-2014", true));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(
                "{\"description\":\"Successfully created the quest.\"," +
                        "\"success\":true}",
                Objects.requireNonNull(response.getBody()).toString());

    }

    @Test
    public void badResponseUpsertQuest()
    {
        QuestController mock = mock(QuestController.class);
        when(mock.processAction(any(Runnable.class), eq(
                UpsertQuestResponseReport.class))).thenReturn(
                new UpsertQuestResponseReport(false));
        when(mock.upsertQuest(
                any(UpsertQuestInformation.class))).thenCallRealMethod();

        ResponseEntity<Object> response = mock.upsertQuest(
                new UpsertQuestInformation(100000, "Test Quest", "Test Quest",
                        420, "map1.tmx",
                        1, 2, 0,
                        1,
                        "01-03-1996",
                        "07-15-2014", true));

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("{\"description\":\"\",\"success\":false}",
                Objects.requireNonNull(response.getBody()).toString());
    }

    @Test
    public void goodResponseGetQuestInfo()
            throws ParseException, JsonProcessingException, JSONException
    {
        QuestController mock = mock(QuestController.class);
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");

        QuestEditingInfoDTO quest = new QuestEditingInfoDTO();
        ArrayList<QuestRecordDTO> records = new ArrayList<QuestRecordDTO>();
        records.add(new QuestRecordDTO("Test Quest", "Test Quest", null, 1,
                "map1.tmx",
                new Position(1, 2), 420, 0,
                new ActionTypeDTO(
                        String.valueOf(QuestCompletionActionType.NO_ACTION)),
                formatter.parse("01-03-1996"),
                formatter.parse("07-15-2014"), true));
        ArrayList<String> maps = new ArrayList<>()
        {{
            add("map1.tmx");
            add("map2.tmx");
        }};
        ArrayList<ActionTypeDTO> actions = new ArrayList<>()
        {{
            add(new ActionTypeDTO(QuestCompletionActionType.NO_ACTION.getID(),
                    QuestCompletionActionType.NO_ACTION.toString()));
            add(new ActionTypeDTO(QuestCompletionActionType.TELEPORT.getID(),
                    QuestCompletionActionType.TELEPORT.toString()));
        }};

        quest.setQuestRecords(records);
        quest.setMapNames(maps);
        quest.setCompletionActionTypes(actions);

        when(mock.processAction(any(Runnable.class), eq(
                GetQuestInformationReport.class))).thenReturn(
                new GetQuestInformationReport(quest));

        when(mock.getQuestInfo()).thenCallRealMethod();


        ResponseEntity<Object> response = mock.getQuestInfo();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        JSONObject last = new JSONObject((String) response.getBody());

        JSONArray records2 = (JSONArray) last.get("quests");
        assertEquals(records2.getJSONObject(0).getInt("questID"), 1);

        JSONArray maps2 = (JSONArray) last.get("mapNames");
        assertEquals(maps2.getString(0), "map1.tmx");
        assertEquals(maps2.getString(1), "map2.tmx");

        JSONArray actions2 = (JSONArray) last.get("completionActionTypes");
        assertEquals(actions2.getJSONObject(0).getInt("actionID"),
                QuestCompletionActionType.NO_ACTION.getID());
        assertEquals(actions2.getJSONObject(1).getInt("actionID"),
                QuestCompletionActionType.TELEPORT.getID());
    }
}
