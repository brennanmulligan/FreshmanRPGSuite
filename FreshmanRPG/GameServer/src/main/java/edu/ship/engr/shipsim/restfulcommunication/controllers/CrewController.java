package edu.ship.engr.shipsim.restfulcommunication.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ship.engr.shipsim.dataDTO.CrewDTO;
import edu.ship.engr.shipsim.model.CommandGetCrews;
import edu.ship.engr.shipsim.model.ModelFacade;
import edu.ship.engr.shipsim.model.reports.GetAllCrewsReport;
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
public class CrewController extends Controller
{
    @CrossOrigin // Required for web client support
    @GetMapping("/crews")
    public ResponseEntity<Object> getAllCrews() throws JsonProcessingException
    {
        GetAllCrewsReport report = processAction(() ->
        {
            CommandGetCrews command =
                    new CommandGetCrews();
            ModelFacade.getSingleton().queueCommand(command);
        }, GetAllCrewsReport.class);

        if (report != null)
        {
            //array list of dtos to json object
            ObjectMapper mapper = new ObjectMapper();
            String json = "{\"success\": true, \"crews\": " + mapper.writeValueAsString(report.getCrews()) + "}";
            return new ResponseEntity<>(json, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
