package api.service;

import api.model.PlayerTokenManager;
import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datasource.LoggerManager;
import edu.ship.engr.shipsim.datasource.PlayerLoginRowDataGateway;
import edu.ship.engr.shipsim.datasource.PlayerRowDataGateway;
import edu.ship.engr.shipsim.model.Player;
import edu.ship.engr.shipsim.model.PlayerManager;
import org.springframework.stereotype.Service;

/**
 * Implementation of Login Service
 *
 * @author Jun
 */
@Service
public class LoginServiceImpl implements LoginService
{
//    private final PlayerLoginRowDataGateway playerLoginRowDataGateway;
//    private final PlayerRowDataGateway playerRowDataGateway;
//
//    public LoginServiceImpl(PlayerLoginRowDataGateway playerLoginRowDataGateway, PlayerRowDataGateway playerRowDataGateway) {
//        this.playerLoginRowDataGateway = playerLoginRowDataGateway;
//        this.playerRowDataGateway = playerRowDataGateway;
//    }
//
//    public LoginServiceImpl() {
//
//    }

    /**
     * @throws DatabaseException if username of password is incorrect, this will be thrown
     */
    @Override
    public int loginWithCredentials(String username, String password) throws DatabaseException
    {
        LoggerManager.getSingleton().getLogger().info("Trying to log in " + username);
        PlayerLoginRowDataGateway loginGateway;
        PlayerRowDataGateway playerGateway;
        try
        {
            loginGateway = new PlayerLoginRowDataGateway(username);
            playerGateway = new PlayerRowDataGateway(loginGateway.getPlayerID());
        }
        catch (DatabaseException e)
        {
            throw new DatabaseException("No login information for " + username + ".");

        }

        if (!loginGateway.checkPassword(password))
        {
            throw new DatabaseException("Incorrect password.");
        }
        Player player = PlayerManager.getSingleton().addPlayer(playerGateway.getPlayerID());
        PlayerTokenManager ptm = PlayerTokenManager.getInstance();

        LoggerManager.getSingleton().getLogger().info("Login was successful for " + username);
        return ptm.addPlayer(player);
    }
}
