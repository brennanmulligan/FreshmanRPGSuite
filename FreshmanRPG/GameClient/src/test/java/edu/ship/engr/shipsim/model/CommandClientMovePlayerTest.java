package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.dataDTO.VanityDTO;
import edu.ship.engr.shipsim.datatypes.Crew;
import edu.ship.engr.shipsim.datatypes.Major;
import edu.ship.engr.shipsim.datatypes.Position;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import edu.ship.engr.shipsim.testing.annotations.ResetClientPlayerManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Josh
 */
@GameTest("GameClient")
@ResetClientPlayerManager
public class CommandClientMovePlayerTest
{
    /**
     * Create the passability map to simulate a map being loaded in.
     */
    @BeforeEach
    public void setup()
    {
        boolean[][] passability =
                {
                        {true, true, true},
                        {true, false, true},
                        {true, true, true}};

        MapManager.resetSingleton();
        MapManager.getSingleton().setPassability(passability);
    }

    /**
     * Testing the command to move our player
     *
     * @throws NotBoundException     shouldn't
     * @throws AlreadyBoundException shouldnt
     */
    @Test
    public void testMovePlayer() throws AlreadyBoundException,
            NotBoundException
    {
        Position pos = new Position(1, 2);
        ClientPlayerManager.getSingleton().initiateLogin("1", "1");
        ClientPlayerManager.getSingleton().finishLogin(1);
        ClientPlayerManager.getSingleton().getThisClientsPlayer().setPosition(pos);
        assertEquals(new Position(1, 2), ClientPlayerManager.getSingleton().getThisClientsPlayer().getPosition());

        CommandClientMovePlayer cm = new CommandClientMovePlayer(1, new Position(1, 0));
        cm.execute();
        assertEquals(new Position(1, 0), ClientPlayerManager.getSingleton().getThisClientsPlayer().getPosition());
    }

    /**
     * Test attempting to move into an impassable position
     *
     * @throws NotBoundException     shouldn't
     * @throws AlreadyBoundException shouldn't
     */
    @Test
    public void testIllegalMove() throws AlreadyBoundException,
            NotBoundException
    {
        Position pos = new Position(1, 2);
        VanityDTO vanityDTO = new VanityDTO();
        List<VanityDTO> vanityDTOS = new ArrayList<>();
        vanityDTOS.add(vanityDTO);
        ClientPlayer me = ClientPlayerManager.getSingleton().initializePlayer(1, "1", vanityDTOS,
                pos, Crew.OUT_OF_BOUNDS, Major.SOFTWARE_ENGINEERING, 1);
        ClientPlayerManager.getSingleton().initiateLogin("1", "1");
        ClientPlayerManager.getSingleton().finishLogin(1);
        assertEquals(new Position(1, 2), me.getPosition());

        CommandClientMovePlayer cm = new CommandClientMovePlayer(me.getID(), new Position(
                1, 1));
        cm.execute();
        assertEquals(new Position(1, 2), me.getPosition());
    }
}
