package api.controller;


import api.model.CreatePlayerResponce;
import api.model.Player;
import api.service.PlayerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlayerController {

    private final PlayerService playerService;

    /**
     *
     * @param playerService - player service
     */
    public PlayerController(PlayerService playerService) {
        super();
        this.playerService = playerService;
    }


    /**
     * Adds a player
     * @param player Basic player information in GameManagerPlayerDTO
     * @return Returns result int
     */
    @CrossOrigin
    @PostMapping("/player")
    public ResponseEntity<Object> addPlayer(@RequestBody Player player) throws JsonProcessingException {
        System.out.println("fdf");
        int result = playerService.addPlayer(player);
        CreatePlayerResponce responseObj = new CreatePlayerResponce(result);
        System.out.println("dsdf");
        return new ResponseEntity<>(responseObj.toJSON(),HttpStatus.OK);
    }
}
