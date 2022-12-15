package api.controller;

import api.model.LoginRequest;
import api.model.LoginResponse;
import api.model.PlayerTokenManager;
import api.service.LoginService;
import com.fasterxml.jackson.core.JsonProcessingException;
import edu.ship.engr.shipsim.datasource.DatabaseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Login Controller
 *
 * @author Jun
 */
@RestController
public class LoginController
{
    private final LoginService loginService;

    /**
     * @param loginService - the login service
     */
    public LoginController(LoginService loginService)
    {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> loginWithCredentials(@RequestBody LoginRequest request) throws JsonProcessingException
    {
        try
        {
            int playerID = loginService.loginWithCredentials(request.getUsername(), request.getPassword());
            return new ResponseEntity<>(new LoginResponse(playerID).toJSON(), HttpStatus.OK);
        }
        catch (DatabaseException e)
        {
            return new ResponseEntity<>(new LoginResponse(-1).toJSON(), HttpStatus.OK);
        }
    }

    @GetMapping("/logout/{playerID}")
    public ResponseEntity<Object> logoutPlayer(@PathVariable("playerID") int playerID)
    {
        PlayerTokenManager ptm = PlayerTokenManager.getInstance();
        if (ptm.removePlayer(playerID))
        {
            return new ResponseEntity<>("Player " + playerID + " logged out.", HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>("Player " + playerID + " failed to log out.", HttpStatus.OK);
        }
    }

}
