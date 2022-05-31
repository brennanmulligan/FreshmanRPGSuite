package model;

import datatypes.Position;
import datatypes.ServersForTest;
import org.junit.Test;
import org.junit.experimental.theories.suppliers.TestedOn;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ServerMapManagerTest
{
@Test
    public void getsDefaultPosition()
    {
        Position p = ServerMapManager.getSingleton().getDefaultPositionForMap("Quad");
        assertEquals(new Position(ServersForTest.QUAD.getTeleportPositionX(),
                        ServersForTest.QUAD.getTeleportPositionY()), p);
    }
    @Test
    public void canGetCollisionData()
    {
        boolean[][] actual = ServerMapManager.getSingleton().getCollisionMap("quad.tmx");
        assertEquals(124,actual.length);
        assertEquals(114, actual[0].length);
    }
}
