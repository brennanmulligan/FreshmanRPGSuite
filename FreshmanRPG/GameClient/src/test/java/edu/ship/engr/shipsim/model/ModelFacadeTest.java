package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.testing.annotations.GameTest;
import edu.ship.engr.shipsim.testing.annotations.ResetClientModelFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Merlin
 */
@GameTest("GameClient")
@ResetClientModelFacade
public class ModelFacadeTest
{

    /**
     * reset the necessary singletons
     */
    @BeforeEach
    public void setup()
    {
        MapManager.resetSingleton();
    }

    /**
     * make sure it is a singleton
     */
    @Test
    public void isResetableSingleton()
    {
        ClientModelFacade facade1 = ClientModelFacade.getSingleton(true, true);
        ClientModelFacade facade2 = ClientModelFacade.getSingleton(true, true);
        assertSame(facade1, facade2);
        ClientModelFacade.resetSingleton();
        assertNotSame(facade1, ClientModelFacade.getSingleton(true, true));
    }

    /**
     * Make sure that we get an exception if we ask for a facade in a different
     * mode than the current one without resetting it first
     */
    @Test
    public void cantChangeModes()
    {
        boolean sawException = false;
        ClientModelFacade.getSingleton(true, true);
        try
        {
            ClientModelFacade.getSingleton(true, false);
        }
        catch (IllegalArgumentException e)
        {
            sawException = true;
        }
        assertTrue(sawException);

        ClientModelFacade.resetSingleton();
        ClientModelFacade.getSingleton(true, true);

        try
        {
            ClientModelFacade.getSingleton(false, true);
        }
        catch (IllegalArgumentException e)
        {
            sawException = true;
        }
        assertTrue(sawException);
    }

}
