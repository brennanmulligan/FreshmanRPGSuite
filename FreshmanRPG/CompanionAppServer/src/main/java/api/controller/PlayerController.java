package api.controller;


import api.model.CreatePlayerResponce;
import api.model.GameManagerPlayer;
import api.service.PlayerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlayerController
{

    private final PlayerService playerService;

    /**
     * @param playerService - player service
     */
    public PlayerController(PlayerService playerService)
    {
        super();
        this.playerService = playerService;
    }


    /**
     * Adds a player
     *
     * @param player Basic player information in GameManagerPlayerDTO
     * @return Returns result int
     */
    @CrossOrigin // Required for web client support
    @PostMapping("/player/create")
    public ResponseEntity<Object> addPlayer(@RequestBody GameManagerPlayer player) throws JsonProcessingException
    {
        int result = playerService.addPlayer(player);
        CreatePlayerResponce responseObj = new CreatePlayerResponce(result);
        return new ResponseEntity<>(responseObj.toJSON(), HttpStatus.OK);
    }
}
