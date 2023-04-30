package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datatypes.Position;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import edu.ship.engr.shipsim.testing.annotations.ResetChatManager;
import edu.ship.engr.shipsim.testing.annotations.ResetPlayerManager;
import edu.ship.engr.shipsim.testing.annotations.ResetReportObserverConnector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Stack;

import static org.junit.jupiter.api.Assertions.assertEquals;

@GameTest("GameServer")
@ResetPlayerManager
@ResetChatManager
@ResetReportObserverConnector
public class SmartPathTest
{
    boolean t = true;
    boolean f = false;

    private final boolean[][] testCollisionMap =
            {
                    {f, f, f, t, t, f, f, f}
                    , {f, t, f, f, f, t, f, f}
                    , {f, t, t, t, f, t, f, f}
                    , {f, t, f, f, f, t, f, f}
                    , {f, t, f, t, t, f, f, f}
                    , {f, t, f, f, f, f, f, f}
                    , {f, f, f, f, f, f, f, f}
            };

    @BeforeEach
    public void localSetUp() throws DatabaseException
    {
        NPC npc = new NPC(33);
        npc.setMapName("quad.tmx");
        PlayerManager.getSingleton().addNpc(npc);

        OptionsManager.getSingleton().setMapFileTitle("quad.tmx");
    }

    @Test
    public void testConstructor()
    {
        SmartPath sp = new SmartPath();
    }

    @Test
    public void testAStar()
    {
        SmartPath sp = new SmartPath();
        sp.setCollisionMap(testCollisionMap);
        Stack<Position> steps = sp.aStar(new Position(0, 0), new Position(6, 7));
        Position temp = steps.pop();
        assertEquals(new Position(0, 0), temp);
        temp = steps.pop();
        assertEquals(new Position(1, 0), temp);
        temp = steps.pop();
        assertEquals(new Position(2, 0), temp);

        temp = steps.pop();
        assertEquals(new Position(3, 0), temp);
        temp = steps.pop();
        assertEquals(new Position(4, 0), temp);
        temp = steps.pop();
        assertEquals(new Position(5, 0), temp);
        temp = steps.pop();
        assertEquals(new Position(6, 0), temp);
        temp = steps.pop();
        assertEquals(new Position(6, 1), temp);
        temp = steps.pop();
        assertEquals(new Position(6, 2), temp);
        temp = steps.pop();
        assertEquals(new Position(6, 3), temp);
        temp = steps.pop();
        assertEquals(new Position(6, 4), temp);
        temp = steps.pop();
        assertEquals(new Position(6, 5), temp);
        temp = steps.pop();
        assertEquals(new Position(6, 6), temp);
        temp = steps.pop();
        assertEquals(new Position(6, 7), temp);
    }
}
