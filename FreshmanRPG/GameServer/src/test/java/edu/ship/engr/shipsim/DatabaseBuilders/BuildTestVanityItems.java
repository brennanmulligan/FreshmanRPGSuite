package edu.ship.engr.shipsim.DatabaseBuilders;

import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datasource.VanityItemsTableDataGateway;
import edu.ship.engr.shipsim.datatypes.VanityItemsForTest;
import edu.ship.engr.shipsim.datatypes.VanityType;
import edu.ship.engr.shipsim.model.OptionsManager;

import java.sql.SQLException;

/**
 * Builds the Vanity item portion of the database
 */
public class BuildTestVanityItems
{
    /**
     * @param args unused
     * @throws DatabaseException shouldnt
     * @throws SQLException      shouldnt
     */
    public static void main(String[] args) throws DatabaseException, SQLException
    {
        OptionsManager.getSingleton().setUsingTestDB(true);
        createVanityItemTable();
    }

    /**
     * Create a table of vanity items
     *
     * @throws DatabaseException shouldn't
     */
    public static void createVanityItemTable() throws DatabaseException
    {
        System.out.println("Building the VanityItem Table");
        VanityItemsTableDataGateway.createTable();
        VanityItemsTableDataGateway gateway = VanityItemsTableDataGateway.getSingleton();

        ProgressBar bar = new ProgressBar(VanityItemsForTest.values().length);
        for (VanityItemsForTest v : VanityItemsForTest.values())
        {
            gateway.addVanityItem(v.getName(), v.getDescription(), v.getTextureName(), v.getVanityType(),
                    v.getPrice(), v.isDeletable(), v.isInShop(), v.isDefault(), v.isDefaultEquipped());

            bar.update();
        }
    }
}
