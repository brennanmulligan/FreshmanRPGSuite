package edu.ship.engr.shipsim.DatabaseBuilders;

import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datasource.InteractableItemRowDataGateway;
import edu.ship.engr.shipsim.datatypes.InteractableItemsForTest;
import edu.ship.engr.shipsim.model.OptionsManager;

/**
 * Builds InteractableItemsForTest
 *
 * @author Jake Moore, Elisabeth Ostrow
 */
public class BuildProductionInteractableItems
{
    /**
     * Main
     *
     * @param args unused
     * @throws DatabaseException shouldnt
     */
    public static void main(String[] args) throws DatabaseException
    {
        OptionsManager.getSingleton().setUsingTestDB(false);
        createItemTable();
    }

    /**
     * Create table of levels
     *
     * @throws DatabaseException shouldnt
     */
    private static void createItemTable() throws DatabaseException
    {
        InteractableItemRowDataGateway.createTable();
        for (InteractableItemsForTest item : InteractableItemsForTest.values())
        {
            new InteractableItemRowDataGateway(item.getName(), item.getPosition(), item.getActionType(), item.getActionParam(), item.getMapName());
        }
    }

}
