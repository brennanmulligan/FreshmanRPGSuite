package DatabaseBuilders;

import datasource.DatabaseException;
import datasource.DoubloonPrizesTableDataGateway;
import datatypes.DoubloonPrizesForTest;
import model.OptionsManager;

import java.sql.SQLException;

/**
 * @author Mina, Christian
 */
public class BuildTestDoubloonPrizes
{

    /**
     * Create a table of levels
     */
    private static void createDoubloonPrizesTable() throws DatabaseException
    {
        DoubloonPrizesTableDataGateway.createTable();
        for (DoubloonPrizesForTest prize : DoubloonPrizesForTest.values())
        {
            DoubloonPrizesTableDataGateway gateway =
                    DoubloonPrizesTableDataGateway.getSingleton();
            gateway.createRow(prize.getName(), prize.getCost(), prize.getDescription());
        }
    }

    public static void main(String[] args) throws DatabaseException, SQLException
    {
        OptionsManager.getSingleton().setUsingTestDB(true);
        createDoubloonPrizesTable();
    }

}
