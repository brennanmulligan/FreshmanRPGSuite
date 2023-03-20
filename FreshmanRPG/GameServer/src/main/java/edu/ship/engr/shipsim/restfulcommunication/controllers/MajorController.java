package edu.ship.engr.shipsim.restfulcommunication.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ship.engr.shipsim.model.CommandGetMajors;
import edu.ship.engr.shipsim.model.ModelFacade;
import edu.ship.engr.shipsim.model.reports.GetAllMajorsReport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Scott Bucher
 */
@RestController
public class MajorController extends Controller
{
    @CrossOrigin // Required for web client support
    @GetMapping("/majors")
    public ResponseEntity<Object> getAllMajors() throws JsonProcessingException
    {
        GetAllMajorsReport report = processAction(() ->
        {
            CommandGetMajors command =
                    new CommandGetMajors();
            ModelFacade.getSingleton().queueCommand(command);
        }, GetAllMajorsReport.class);

        if (report != null)
        {
            //array list of dtos to json object
            ObjectMapper mapper = new ObjectMapper();
            String json = "{\"success\": true, \"majors\": " + mapper.writeValueAsString(report.getMajors()) + "}";
            return new ResponseEntity<>(json, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
