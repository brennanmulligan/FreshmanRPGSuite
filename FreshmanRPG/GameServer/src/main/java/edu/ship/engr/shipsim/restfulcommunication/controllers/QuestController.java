package edu.ship.engr.shipsim.restfulcommunication.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ship.engr.shipsim.dataDTO.ObjectiveRecordDTO;
import edu.ship.engr.shipsim.dataDTO.QuestEditingInfoDTO;
import edu.ship.engr.shipsim.dataENUM.ObjectiveCompletionType;
import edu.ship.engr.shipsim.model.CommandGetQuestInformation;
import edu.ship.engr.shipsim.model.CommandUpsertQuest;
import edu.ship.engr.shipsim.model.ModelFacade;
import edu.ship.engr.shipsim.model.ObjectiveRecord;
import edu.ship.engr.shipsim.model.QuestRecord;
import edu.ship.engr.shipsim.model.reports.GetQuestInformationReport;
import edu.ship.engr.shipsim.model.reports.UpsertQuestResponseReport;
import edu.ship.engr.shipsim.restfulcommunication.representation.BasicResponse;
import edu.ship.engr.shipsim.restfulcommunication.representation.UpsertQuestInformation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class QuestController extends Controller
{

    @CrossOrigin // Required for web client support
    @PostMapping("/quest/upsert")
    public ResponseEntity<Object> upsertQuest(
            @RequestBody UpsertQuestInformation info)
    {
        UpsertQuestResponseReport report = processAction(() ->
        {
            // Convert ObjectiveRecordDTOs to ObjectiveRecords
            ArrayList<ObjectiveRecord> objectives = new ArrayList<>();

            for (ObjectiveRecordDTO dto : info.getObjectives())
            {
                objectives.add(new ObjectiveRecord(dto.getQuestID(), dto.getID(),
                        dto.getDescription(), dto.getExperiencePointsGained(),
                        ObjectiveCompletionType.findByID(dto.getCompletionType()), null));
            }

            CommandUpsertQuest command =
                    new CommandUpsertQuest(
                            new QuestRecord(info.getId(), info.getTitle(),
                                    info.getDescription(),
                                    info.getTriggerMapName(),
                                    info.getPosition(),
                                    objectives,
                                    info.getObjectivesForFulfillment(),
                                    info.getexperiencePointsGained(),
                                    info.getCompletionActionType(),
                                    null,
                                    info.getStartDate(), info.getEndDate(),
                                    info.isEasterEgg()));
            ModelFacade.getSingleton().queueCommand(command);
        }, UpsertQuestResponseReport.class);


        if (report != null)
        {
            if (report.isSuccessful())
            {
                System.out.println("Success");
                return new ResponseEntity<>(new BasicResponse(true,
                        "Successfully " +
                                (info.getId() < 0 ? "create" : "update") +
                                "d the quest.").toString(),
                        HttpStatus.OK);
            }
            else
            {
                System.out.println("FAIL; " + report.getDescription());
                return new ResponseEntity<>(
                        new BasicResponse(false,
                                report.getDescription()).toString(),
                        HttpStatus.BAD_REQUEST);
            }
        }

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @CrossOrigin
    @PostMapping("/quest/getQuestEditingInfo")
    public ResponseEntity<Object> getQuestInfo()
            throws JsonProcessingException
    {
        GetQuestInformationReport report = processAction(() ->
        {
            CommandGetQuestInformation command =
                    new CommandGetQuestInformation();
            ModelFacade.getSingleton().queueCommand(command);
        }, GetQuestInformationReport.class);


        if (report != null)
        {
            ObjectMapper mapper = new ObjectMapper();
            QuestEditingInfoDTO qInfo = report.getQuestEditingInfoDTO();
            String json = "{\"success\": true, " +
                    "\"quests\": " +
                    mapper.writeValueAsString(qInfo.getQuestRecords()) +
                    ", \"mapNames\": " +
                    mapper.writeValueAsString(qInfo.getMapNames()) +
                    ", \"completionActionTypes\": " + mapper.writeValueAsString(
                    qInfo.getCompletionActionTypes()) +
                    "}";

            return new ResponseEntity<>(json, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
