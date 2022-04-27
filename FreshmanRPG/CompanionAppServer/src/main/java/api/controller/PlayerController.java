package api.controller;


import api.model.Player;
import api.service.PlayerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @PostMapping("/player")
    public ResponseEntity<Object> addPlayer(@RequestBody Player player){
        int result = playerService.addPlayer(player);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
}
