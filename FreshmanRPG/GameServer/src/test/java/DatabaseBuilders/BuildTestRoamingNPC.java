package DatabaseBuilders;

import datasource.DatabaseException;
import datasource.InfoNPCTableDataGatewayRDS;
import datasource.RoamingNPCTableDataGatewayRDS;
import datatypes.RoamingNPCPathsForTest;
import model.OptionsManager;

import java.sql.SQLException;

public class BuildTestRoamingNPC
{
    /**
     * @param args unused
     * @throws DatabaseException shouldn't
     * @throws SQLException      shouldn't
     */
    public static void main(String[] args) throws DatabaseException, SQLException
    {
        OptionsManager.getSingleton().setUsingMocKDataSource(false);
        OptionsManager.getSingleton().setUsingTestDB(true);
        createRoamNPCTable();
    }

    /**
     * Create a table of test questions
     *
     * @throws DatabaseException if we can't build the table
     */
    public static void createRoamNPCTable() throws DatabaseException
    {
        RoamingNPCTableDataGatewayRDS.createTable();

        for (RoamingNPCPathsForTest path : RoamingNPCPathsForTest.values())
        {
            RoamingNPCTableDataGatewayRDS.createRow(path.getPath(), path.getNpcID());
        }
    }

}
