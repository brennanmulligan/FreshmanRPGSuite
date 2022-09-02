package api.controller;

import api.model.ObjectiveFetchResponse;
import api.model.ObjectiveRequest;
import api.model.ObjectiveResponse;
import api.service.ObjectiveService;
import com.fasterxml.jackson.core.JsonProcessingException;
import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.model.IllegalObjectiveChangeException;
import edu.ship.engr.shipsim.model.IllegalQuestChangeException;
import edu.ship.engr.shipsim.model.ObjectiveRecord;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Handles RESTAPI calls for Objective Authentication
 *
 * @author Joel
 */


@RestController
public class ObjectiveController
{

    private final ObjectiveService objectiveService;

    public ObjectiveController(ObjectiveService objectiveService)
    {
        super();
        this.objectiveService = objectiveService;
    }

    /**
     * Handles HTTP Post request for Objective Authentication
     *
     * @param request ObjectiveAuthentication Object
     */
    @PostMapping("/objective-complete")
    public ResponseEntity<Object> completeObjective(@RequestBody ObjectiveRequest request) throws
            JsonProcessingException, IllegalObjectiveChangeException, DatabaseException, IllegalQuestChangeException
    {
        int response = objectiveService.completeObjective(request);
        ObjectiveResponse responseObj = new ObjectiveResponse(response);
        return new ResponseEntity<>(responseObj.toJSON(), HttpStatus.OK);
    }

    @GetMapping("/objective-fetch-all/{playerID}")
    public ResponseEntity<Object> fetchAllObjectives(@PathVariable("playerID") int playerID) throws DatabaseException,
            JsonProcessingException
    {
        List<ObjectiveRecord> response = objectiveService.fetchAllObjectives(playerID);
        ObjectiveFetchResponse responseObj = new ObjectiveFetchResponse(response);
        return new ResponseEntity<>(responseObj.toJSON(), HttpStatus.OK);
    }
}
