package edu.ship.engr.shipsim.restfulcommunication.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datasource.PlayerTableDataGateway;
import edu.ship.engr.shipsim.model.CommandFetchPlayerObjectives;
import edu.ship.engr.shipsim.model.ModelFacade;
import edu.ship.engr.shipsim.model.Player;
import edu.ship.engr.shipsim.model.PlayerManager;
import edu.ship.engr.shipsim.model.reports.PlayerQuestReport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

/**
 * @author Derek
 */
@RestController
public class PlayerController extends Controller
{
    @GetMapping("/player/{playerID}")
    public ResponseEntity<Object> getPlayer(@PathVariable("playerID") int playerID) throws DatabaseException
    {
        String response = PlayerTableDataGateway.getSingleton().retrieveAllPlayers().stream().map(player -> "  {\n    id:" + player.getPlayerID() + ",\n    name:" + player.getPlayerName() + "\n  }").collect(Collectors.joining(",\n"));

        return new ResponseEntity<>("{\n" + response + "\n}", HttpStatus.OK);
    }

    @GetMapping("/objective-fetch-all/{playerID}")
    public ResponseEntity<Object> fetchAllObjectives(@PathVariable("playerID") int playerID) throws JsonProcessingException
    {
        Player player = PlayerManager.getSingleton().getPlayerFromID(playerID);

        PlayerQuestReport report = processAction(() ->
        {
            CommandFetchPlayerObjectives command = new CommandFetchPlayerObjectives(player);

            ModelFacade.getSingleton().queueCommand(command);
        }, PlayerQuestReport.class);

        HttpStatus status = HttpStatus.GATEWAY_TIMEOUT;
        String recordJson = "";
        if (report != null)
        {
            status = HttpStatus.OK;
            recordJson = report.toJson();
        }

        return new ResponseEntity<>(recordJson, status); // STOPSHIP: 11/2/22 Doesn't work as intended
    }
}
