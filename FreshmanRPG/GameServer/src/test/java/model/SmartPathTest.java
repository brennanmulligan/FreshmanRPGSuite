package model;

import datasource.ServerSideTest;
import datatypes.Position;
import org.junit.Before;
import org.junit.Test;

import java.util.Stack;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class SmartPathTest extends ServerSideTest
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

    @Before
    public void localSetUp()
    {
        NPC npc = new NPC(33);
        npc.setMapName("quad.tmx");
        PlayerManager.getSingleton().addNpc(npc);

        OptionsManager.getSingleton().setMapName("quad.tmx");
        QualifiedObservableConnector.resetSingleton();
        ChatManager.resetSingleton();
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
       Stack<Position> steps = sp.aStar(new Position(0,0), new Position(6,7));
       Position temp = steps.pop();
       assertEquals(new Position(0,0), temp);
       temp = steps.pop();
       assertEquals(new Position(1,0), temp);
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
