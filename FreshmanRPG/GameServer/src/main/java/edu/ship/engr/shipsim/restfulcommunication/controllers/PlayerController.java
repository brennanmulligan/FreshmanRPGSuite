package edu.ship.engr.shipsim.restfulcommunication.controllers;

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
                    new CommandCreatePlayer(info.getUsername(),
                            info.getPassword(),
                            info.getCrewNum(), info.getMajorNum(),
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
                System.out.println("FAIL");
                return new ResponseEntity<>(
                        new BasicResponse(false, report.getDescription()).toString(),
                        HttpStatus.BAD_REQUEST);
            }
        }

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @CrossOrigin // Required for web client support
    @PostMapping("/getAllPlayers")
    public ResponseEntity<Object> getAllPlayers()
    {
        GetAllPlayersReport report = processAction(() ->
        {
            CommandGetAllPlayers command =
                    new CommandGetAllPlayers();
            ModelFacade.getSingleton().queueCommand(command);
        }, GetAllPlayersReport.class);

        if (report != null)
        {
            return new ResponseEntity<>(report.getPlayers(), HttpStatus.OK);
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
                    new CommandChangePlayer(info.getUsername(),
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