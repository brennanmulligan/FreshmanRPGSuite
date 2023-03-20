package edu.ship.engr.shipsim.restfulcommunication.controllers;

import edu.ship.engr.shipsim.model.CommandGetCrews;
import edu.ship.engr.shipsim.model.ModelFacade;
import edu.ship.engr.shipsim.model.reports.GetAllCrewsReport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Scott Bucher
 */
@RestController
public class CrewController extends Controller
{
    @CrossOrigin // Required for web client support
    @PostMapping("/crews")
    public ResponseEntity<Object> getAllCrews()
    {
        GetAllCrewsReport report = processAction(() ->
        {
            CommandGetCrews command =
                    new CommandGetCrews();
            ModelFacade.getSingleton().queueCommand(command);
        }, GetAllCrewsReport.class);

        if (report != null)
        {
            return new ResponseEntity<>(report.getCrews(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
