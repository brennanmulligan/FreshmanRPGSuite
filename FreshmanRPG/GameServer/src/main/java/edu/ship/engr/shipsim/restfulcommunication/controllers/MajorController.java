package edu.ship.engr.shipsim.restfulcommunication.controllers;

import edu.ship.engr.shipsim.model.CommandGetMajors;
import edu.ship.engr.shipsim.model.ModelFacade;
import edu.ship.engr.shipsim.model.reports.GetAllMajorsReport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Scott Bucher
 */
@RestController
public class MajorController extends Controller
{
    @CrossOrigin // Required for web client support
    @PostMapping("/majors")
    public ResponseEntity<Object> getAllMajors()
    {
        GetAllMajorsReport report = processAction(() ->
        {
            CommandGetMajors command =
                    new CommandGetMajors();
            ModelFacade.getSingleton().queueCommand(command);
        }, GetAllMajorsReport.class);

        if (report != null)
        {
            return new ResponseEntity<>(report.getMajors(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
