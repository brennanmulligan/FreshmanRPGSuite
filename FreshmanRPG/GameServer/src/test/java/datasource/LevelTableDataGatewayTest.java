package datasource;

import datatypes.LevelsForTest;
import model.OptionsManager;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * An abstract class that tests the table data gateways into the Level table
 *
 * @author merlin
 */
public class LevelTableDataGatewayTest extends ServerSideTest
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
    public void retrievesThemAll() throws DatabaseException
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
