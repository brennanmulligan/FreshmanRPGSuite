package DatabaseBuilders;

import datasource.DatabaseException;
import datasource.InfoNPCTableDataGatewayRDS;
import datatypes.InfoNPCResponsesForTest;
import model.OptionsManager;

import java.sql.SQLException;

public class BuildTestInfoNPC
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
        createInfoNPCTable();
    }

    /**
     * Create a table of test questions
     *
     * @throws DatabaseException if we can't build the table
     */
    public static void createInfoNPCTable() throws DatabaseException
    {
        InfoNPCTableDataGatewayRDS.createTable();

        for (InfoNPCResponsesForTest response : InfoNPCResponsesForTest.values())
        {
            InfoNPCTableDataGatewayRDS.createRow(response.getResponse(), response.getInformation(), response.getNpcID());
        }
    }
}
