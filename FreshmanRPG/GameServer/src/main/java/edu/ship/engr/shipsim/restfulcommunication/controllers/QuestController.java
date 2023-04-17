package edu.ship.engr.shipsim.restfulcommunication.controllers;

import edu.ship.engr.shipsim.model.CommandUpsertQuest;
import edu.ship.engr.shipsim.model.ModelFacade;
import edu.ship.engr.shipsim.model.QuestRecord;
import edu.ship.engr.shipsim.model.reports.UpsertQuestResponseReport;
import edu.ship.engr.shipsim.restfulcommunication.representation.BasicResponse;
import edu.ship.engr.shipsim.restfulcommunication.representation.UpsertQuestInformation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
            CommandUpsertQuest command =
                    new CommandUpsertQuest(
                            new QuestRecord(info.getId(), info.getTitle(),
                                    info.getDescription(), info.getTriggerMapName(),
                                    info.getPosition(), null,
                                    info.getObjectivesForFulfillment(),
                                    info.getXpGained(),
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
}
