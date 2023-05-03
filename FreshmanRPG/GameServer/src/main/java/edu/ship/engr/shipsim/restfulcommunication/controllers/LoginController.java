package edu.ship.engr.shipsim.restfulcommunication.controllers;

import edu.ship.engr.shipsim.model.CommandRestfulLogin;
import edu.ship.engr.shipsim.model.CommandRestfulLogout;
import edu.ship.engr.shipsim.model.ModelFacade;
import edu.ship.engr.shipsim.model.Player;
import edu.ship.engr.shipsim.model.PlayerManager;
import edu.ship.engr.shipsim.model.Report;
import edu.ship.engr.shipsim.model.reports.RestfulLoginFailedReport;
import edu.ship.engr.shipsim.model.reports.RestfulLoginSuccessReport;
import edu.ship.engr.shipsim.model.reports.RestfulLogoutFailedReport;
import edu.ship.engr.shipsim.model.reports.RestfulLogoutSuccessReport;
import edu.ship.engr.shipsim.restfulcommunication.RestfulPlayerManager;
import edu.ship.engr.shipsim.restfulcommunication.RestfulServer;
import edu.ship.engr.shipsim.restfulcommunication.representation.LoginInformation;
import edu.ship.engr.shipsim.restfulcommunication.representation.LoginResponse;
import edu.ship.engr.shipsim.restfulcommunication.representation.LogoutInformation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Derek
 */
@RestController
public class LoginController extends Controller
{
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginInformation info)
    {
        // Temporary fix for autoclosing the connection
        try (RestfulServer.AutoClosingConnectionManager manager = RestfulServer.createConnectionToLoginServer())
        {
            Report report = processAction(() ->
            {
                CommandRestfulLogin command = new CommandRestfulLogin(info.getUsername(), info.getPassword());

                ModelFacade.getSingleton().queueCommand(command);
            }, RestfulLoginSuccessReport.class, RestfulLoginFailedReport.class);

            if (report != null)
            {
                return handleLoginReport(report);
            }

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Object> logout(@RequestBody LogoutInformation info)
    {
        Report report = processAction(() ->
        {
            CommandRestfulLogout command = new CommandRestfulLogout(info.getAuthKey());

            ModelFacade.getSingleton().queueCommand(command);
        }, RestfulLogoutSuccessReport.class, RestfulLogoutFailedReport.class);

        if (report != null)
        {
            return handleLogoutReport(report);
        }

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<Object> handleLogoutReport(Report report)
    {
        if (report.getClass().equals(RestfulLogoutSuccessReport.class))
        {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else if (report.getClass().equals(RestfulLogoutFailedReport.class))
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<Object> handleLoginReport(Report report)
    {
        if (report.getClass().equals(RestfulLoginSuccessReport.class))
        {
            LoginResponse response = handleLoginSuccess((RestfulLoginSuccessReport) report);

            return new ResponseEntity<>(response.toString(), HttpStatus.OK);
        }
        else if (report.getClass().equals(RestfulLoginFailedReport.class))
        {
            LoginResponse response = new LoginResponse((RestfulLoginFailedReport)report);

            return new ResponseEntity<>(response.toString(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }


    private LoginResponse handleLoginSuccess(RestfulLoginSuccessReport report)
    {
        Player player = PlayerManager.getSingleton().addPlayer(report.getPlayerID()); // Add the player to the player manager

        String authKey = RestfulPlayerManager.getSingleton().initializePlayer(player);

        return new LoginResponse(report.getPlayerID(), authKey);
    }
}
