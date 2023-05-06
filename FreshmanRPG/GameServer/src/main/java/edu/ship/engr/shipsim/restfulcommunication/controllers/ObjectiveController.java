package edu.ship.engr.shipsim.restfulcommunication.controllers;

import edu.ship.engr.shipsim.dataDTO.ClientPlayerObjectiveStateDTO;
import edu.ship.engr.shipsim.dataDTO.ClientPlayerQuestStateDTO;
import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datatypes.ObjectiveStateEnum;
import edu.ship.engr.shipsim.model.CommandCompleteObjective;
import edu.ship.engr.shipsim.model.CommandDeleteObjective;
import edu.ship.engr.shipsim.model.CommandFetchPlayerObjectives;
import edu.ship.engr.shipsim.model.ModelFacade;
import edu.ship.engr.shipsim.model.Player;
import edu.ship.engr.shipsim.model.PlayerManager;
import edu.ship.engr.shipsim.model.Report;
import edu.ship.engr.shipsim.model.reports.ObjectiveDeletedReport;
import edu.ship.engr.shipsim.model.reports.ObjectiveStateChangeReport;
import edu.ship.engr.shipsim.model.reports.PlayerQuestReport;
import edu.ship.engr.shipsim.restfulcommunication.representation.BasicResponse;
import edu.ship.engr.shipsim.restfulcommunication.representation.CompleteObjectiveBody;
import edu.ship.engr.shipsim.restfulcommunication.representation.DeleteObjectiveBody;
import edu.ship.engr.shipsim.restfulcommunication.representation.FetchObjectivesBody;
import edu.ship.engr.shipsim.restfulcommunication.representation.FetchObjectivesResponse;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Derek
 */
@RestController
public class ObjectiveController extends Controller
{

    @CrossOrigin
    @GetMapping("/objectives")
    @SneakyThrows(DatabaseException.class)
    public ResponseEntity<Object> fetchObjectives(@RequestBody FetchObjectivesBody body)
    {
        Player player = PlayerManager.getSingleton().addPlayer(body.getPlayerID());

        Report report = processAction(() ->
        {
            CommandFetchPlayerObjectives command = new CommandFetchPlayerObjectives(player);

            ModelFacade.getSingleton().queueCommand(command);
        }, PlayerQuestReport.class);

        if (report != null)
        {
            System.out.println("Got a report: " + report.getClass().getSimpleName());
            System.out.println(((PlayerQuestReport) report).getQuests().size() + " quests");
            FetchObjectivesResponse response = handleQuestReport((PlayerQuestReport) report);

            PlayerManager.getSingleton().removePlayer(body.getPlayerID());

            return new ResponseEntity<>(response.toString(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private FetchObjectivesResponse handleQuestReport(PlayerQuestReport report)
    {
        FetchObjectivesResponse response = new FetchObjectivesResponse();

        for (ClientPlayerQuestStateDTO quest : report.getQuests())
        {
            for (ClientPlayerObjectiveStateDTO objective : quest.getObjectiveList())
            {
                if (objective.isRealLifeObjective() && (objective.getObjectiveState() == ObjectiveStateEnum.TRIGGERED))
                {
                    response.addObjective(quest.getQuestID(), objective.getObjectiveID(), objective.getObjectiveDescription());
                }
            }
        }

        return response;
    }

    @CrossOrigin
    @PostMapping({"/complete-objective", "/objectives/complete"})
    @SneakyThrows(DatabaseException.class)
    public ResponseEntity<BasicResponse> completeObjective(@RequestBody CompleteObjectiveBody body)
    {
        PlayerManager.getSingleton().addPlayer(body.getPlayerID());

        Report report = processAction(() ->
        {
            CommandCompleteObjective command = new CommandCompleteObjective(body.getPlayerID(), body.getQuestID(), body.getObjectiveID());

            ModelFacade.getSingleton().queueCommand(command);
        }, ObjectiveStateChangeReport.class);

        PlayerManager.getSingleton().removePlayer(body.getPlayerID());

        if (report != null)
        {
            ObjectiveStateChangeReport castReport = (ObjectiveStateChangeReport) report;

            if (castReport.getNewState() == ObjectiveStateEnum.COMPLETED)
            {
                return new ResponseEntity<>(new BasicResponse(true, "Objective complete"), HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(new BasicResponse(false, "Objective not completed"), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @CrossOrigin
    @DeleteMapping( "/objectives/delete")
    public ResponseEntity<BasicResponse> deleteObjective(@RequestBody DeleteObjectiveBody body)
    {
        /*
            TODO: This doesn't work. When a user attempts to delete an objective, the following
              exception gets thrown:
              Couldn't find objective with that ID:java.sql.SQLException: Illegal operation on empty result set.
	            at edu.ship.engr.shipsim.datasource.ObjectiveRowDataGateway.<init>(ObjectiveRowDataGateway.java:56)
	            at edu.ship.engr.shipsim.model.CommandDeleteObjective.execute(CommandDeleteObjective.java:25)
	            at edu.ship.engr.shipsim.model.ModelFacade$ProcessCommandQueueTask.run(ModelFacade.java:109)
	            at java.base/java.util.TimerThread.mainLoop(Timer.java:566)
	            at java.base/java.util.TimerThread.run(Timer.java:516)
              However, this exception only appears to get thrown about 50% of the time. The other 50% will
              just show this instead:
              2023-05-06 19:13:51.151  WARN 2011017 --- [nio-8080-exec-6] .w.s.m.s.DefaultHandlerExceptionResolver :
              Resolved [org.springframework.web.HttpMediaTypeNotAcceptableException: Could not find acceptable representation]
         */
        Report report = processAction(() ->
        {
            CommandDeleteObjective command = new CommandDeleteObjective(body.getObjectiveID(), body.getQuestID());

            ModelFacade.getSingleton().queueCommand(command);
        }, ObjectiveDeletedReport.class);


        if (report != null)
        {
            ObjectiveDeletedReport castReport = (ObjectiveDeletedReport) report;

            if (castReport.getSuccessful())
            {
                return new ResponseEntity<>(new BasicResponse(true, "Objective was successfully deleted"), HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(new BasicResponse(false, "Objective could not be deleted"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
