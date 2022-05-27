package DatabaseBuilders;

import datasource.*;
import datatypes.VanityAwardsForTest;
import model.OptionsManager;

import java.sql.SQLException;

public class BuildTestVanityAwards
{
    /**
     * @param args unused
     * @throws DatabaseException shouldnt
     * @throws SQLException shouldnt
     */
    public static void main(String[] args) throws DatabaseException, SQLException
    {
        OptionsManager.getSingleton().setUsingTestDB(true);
        createVanityAwardsTable();
    }

    /**
     * Create a table of vanity inventory
     *
     * @throws DatabaseException shouldnt
     */
    private static void createVanityAwardsTable() throws DatabaseException
    {
        VanityAwardsTableDataGateway.createTable();
        VanityAwardsTableDataGateway gateway =
                VanityAwardsTableDataGateway.getSingleton();
        for (VanityAwardsForTest v : VanityAwardsForTest.values())
        {
            gateway.addVanityAward(v.getQuestID(), v.getVanityID());
        }
    }
}
