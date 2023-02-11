package edu.ship.engr.shipsim.restfulcommunication.controllers;

import edu.ship.engr.shipsim.model.CommandCreatePlayer;
import edu.ship.engr.shipsim.model.ModelFacade;
import edu.ship.engr.shipsim.model.reports.CreatePlayerResponseReport;
import edu.ship.engr.shipsim.restfulcommunication.representation.CreatePlayerInformation;
import edu.ship.engr.shipsim.restfulcommunication.representation.GeneralFailureResponse;
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
    @PostMapping("/player")
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
                return new ResponseEntity<>(HttpStatus.OK);
            }
            else
            {
                return new ResponseEntity<>(
                        new GeneralFailureResponse(report.getDescription()),
                        HttpStatus.BAD_REQUEST);
            }
        }

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
