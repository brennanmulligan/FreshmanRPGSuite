package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.datasource.ServerSideTest;
import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.model.reports.LoginSuccessfulReport;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Merlin
 */
public class PlayerManagerTest extends ServerSideTest
{

    /**
     * reset the necessary singletons
     */
    @Before
    public void localSetUp()
    {
        LoginPlayerManager.resetSingleton();
        QualifiedObservableConnector.resetSingleton();
    }

    /**
     * Make sure PlayerManager is a resetable singleton
     */
    @Test
    public void isSingleton()
    {
        LoginPlayerManager pm1 = LoginPlayerManager.getSingleton();
        LoginPlayerManager pm2 = LoginPlayerManager.getSingleton();
        assertSame(pm1, pm2);
        LoginPlayerManager.resetSingleton();
        assertNotSame(pm1, LoginPlayerManager.getSingleton());
    }

    /**
     * When a login is successful, the PlayerManager should send a
     * LoginSuccessfulReport
     *
     * @throws LoginFailedException shouldn't
     */
    @Test
    public void respondsOnSuccessfulLogin() throws LoginFailedException
    {
        LoginPlayerManager pm = LoginPlayerManager.getSingleton();
        LoginSuccessfulReport expected = new LoginSuccessfulReport(PlayersForTest.MERLIN_OFFLINE.getPlayerID(), "localhost",
                1883, 0);

        LoginSuccessfulReport actual = pm.login(PlayersForTest.MERLIN_OFFLINE.getPlayerName(),
                PlayersForTest.MERLIN_OFFLINE.getPlayerPassword());
        assertEquals(expected, actual);
    }

    /**
     * When a login fails, the PlayerManager should throw an exception
     *
     * @throws LoginFailedException should
     */
    @Test(expected = LoginFailedException.class)
    public void notifiesOnFailedLogin() throws LoginFailedException
    {
        LoginPlayerManager pm = LoginPlayerManager.getSingleton();
        pm.login(PlayersForTest.MERLIN.getPlayerName(), PlayersForTest.MERLIN.getPlayerPassword() + "Z");
    }

}
