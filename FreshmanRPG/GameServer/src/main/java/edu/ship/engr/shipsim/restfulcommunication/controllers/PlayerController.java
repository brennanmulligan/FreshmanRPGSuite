package edu.ship.engr.shipsim.restfulcommunication.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ship.engr.shipsim.model.CommandChangePlayer;
import edu.ship.engr.shipsim.model.CommandCreatePlayer;
import edu.ship.engr.shipsim.model.CommandGetAllPlayers;
import edu.ship.engr.shipsim.model.ModelFacade;
import edu.ship.engr.shipsim.model.reports.ChangePlayerReport;
import edu.ship.engr.shipsim.model.reports.CreatePlayerResponseReport;
import edu.ship.engr.shipsim.model.reports.GetAllPlayersReport;
import edu.ship.engr.shipsim.restfulcommunication.representation.ChangePlayerInformation;
import edu.ship.engr.shipsim.restfulcommunication.representation.CreatePlayerInformation;
import edu.ship.engr.shipsim.restfulcommunication.representation.BasicResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Derek
 */
@RestController
public class PlayerController extends Controller
{
    @CrossOrigin // Required for web client support
    @PostMapping("/player/create")
    public ResponseEntity<Object> createPlayer(
            @RequestBody CreatePlayerInformation info)
    {
        CreatePlayerResponseReport report = processAction(() ->
        {
            CommandCreatePlayer command =
                    new CommandCreatePlayer(info.getPlayerName(),
                            info.getPassword(),
                            info.getCrew(), info.getMajor(),
                            info.getSection());
            ModelFacade.getSingleton().queueCommand(command);
        }, CreatePlayerResponseReport.class);

        if (report != null)
        {
            if (report.isSuccessful())
            {
                System.out.println("Success");
                return new ResponseEntity<>(new BasicResponse(true,"Created").toString(),
                        HttpStatus.OK);
            }
            else
            {
                System.out.println("FAIL; " + report.getDescription());
                return new ResponseEntity<>(
                        new BasicResponse(false, report.getDescription()).toString(),
                        HttpStatus.BAD_REQUEST);
            }
        }

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @CrossOrigin // Required for web client support
    @GetMapping("/getAllPlayers")
    public ResponseEntity<Object> getAllPlayers() throws JsonProcessingException
    {
        GetAllPlayersReport report = processAction(() ->
        {
            CommandGetAllPlayers command =
                    new CommandGetAllPlayers();
            ModelFacade.getSingleton().queueCommand(command);
        }, GetAllPlayersReport.class);

        if (report != null)
        {
            ObjectMapper mapper = new ObjectMapper();
            String json = "{\"success\": true, \"players\": " + mapper.writeValueAsString(report.getPlayers()) + "}";
            return new ResponseEntity<>(json, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @CrossOrigin // Required for web client support
    @PostMapping("/player/update/")
    public ResponseEntity<Object> changePlayer(
            @RequestBody ChangePlayerInformation info)
    {
        ChangePlayerReport report = processAction(() ->
        {
            CommandChangePlayer command =
                    new CommandChangePlayer(info.getPlayerName(),
                            info.getPassword());
            ModelFacade.getSingleton().queueCommand(command);
        }, ChangePlayerReport.class);

        if (report != null)
        {
            if (report.isSuccessful())
            {
                System.out.println("Success");
                return new ResponseEntity<>(new BasicResponse(true,"Password Changed").toString(),
                        HttpStatus.OK);
            }
            else
            {
                System.out.println("FAIL");
                return new ResponseEntity<>(
                        new BasicResponse(false, report.getDescription()).toString(),
                        HttpStatus.BAD_REQUEST);
            }
        }

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}