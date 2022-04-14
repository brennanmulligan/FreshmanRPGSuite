package api.service;

import api.model.PlayerTokenManager;
import datasource.*;
import model.Player;
import model.PlayerManager;
import org.springframework.stereotype.Service;

/**
 * Implementation of Login Service
 *
 * @author Jun
 */
@Service
public class LoginServiceImpl implements LoginService{
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
     *
     * @param username
     * @param password
     * @return
     * @throws DatabaseException if username of password is incorrect, this will be thrown
     */
    @Override
    public int loginWithCredentials(String username, String password) throws DatabaseException {
        PlayerLoginRowDataGateway loginGateway;
        PlayerRowDataGateway playerGateway;
        try {
            loginGateway = new PlayerLoginRowDataGatewayRDS(username);
            playerGateway = new PlayerRowDataGatewayRDS(loginGateway.getPlayerID());
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

        return ptm.addPlayer(player);
    }
}
