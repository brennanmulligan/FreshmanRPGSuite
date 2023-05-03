package edu.ship.engr.shipsim.datasource;

import edu.ship.engr.shipsim.datatypes.LevelsForTest;
import edu.ship.engr.shipsim.model.OptionsManager;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * An abstract class that tests the table data gateways into the Level table
 *
 * @author merlin
 */
@GameTest("GameServer")
public class LevelTableDataGatewayTest
{

    /**
     * @return the gateway we should test
     */
    public LevelTableDataGateway getGateway()
    {
        return LevelTableDataGateway.getSingleton();
    }

    /**
     *
     */
    @Test
    public void isASingleton()
    {
        LevelTableDataGateway x = getGateway();
        LevelTableDataGateway y = getGateway();
        assertSame(x, y);
        assertNotNull(x);
    }

    /**
     * One method retrieves them all
     *
     * @throws DatabaseException shouldn't
     */
    @Test
    public void getsThemAll() throws DatabaseException
    {
        LevelTableDataGateway gateway = getGateway();
        ArrayList<LevelRecord> actual = gateway.getAllLevels();

        for (LevelsForTest l : LevelsForTest.values())
        {
            LevelRecord r = new LevelRecord(l.getDescription(), l.getLevelUpPoints(),
                    l.getLevelUpMonth(),
                    l.getLevelUpDayOfMonth());
            assertTrue(actual.contains(r));
        }
    }

    /**
     * Test that a new row is added when creatRow is called
     *
     * @throws DatabaseException if can't add row
     */
    @Test
    public void testCreateRow() throws DatabaseException
    {
        OptionsManager x = OptionsManager.getSingleton();
        LevelTableDataGateway testGateway = LevelTableDataGateway.getSingleton();
        LevelRecord testLevel = new LevelRecord("GOD", 500, 1, 30);

        testGateway.createRow(testLevel.getDescription(), testLevel.getLevelUpPoints(),
                testLevel.getLevelUpMonth(), testLevel.getLevelUpDayOfMonth());

        ArrayList<LevelRecord> testLevels = testGateway.getAllLevels();

        assertTrue(testLevels.contains(testLevel));
        assertEquals(testLevels.size(), 5);
    }

}
