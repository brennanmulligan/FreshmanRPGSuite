package edu.ship.engr.shipsim.communication;

import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datasource.DatabaseManager;

import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.SQLException;

class MockConnectionListener extends ConnectionListener
{

    public Connection manager;

    public MockConnectionListener(ObjectOutputStream stream, int frequency)
    {
        super(stream, frequency);
    }

    @Override
    public void run()
    {
        try
        {
            this.manager = DatabaseManager.getSingleton().getConnection();
        }
        catch (DatabaseException e)
        {
        }
        finally
        {
            try
            {
                DatabaseManager.getSingleton().closeConnection();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            catch (DatabaseException e)
            {
                e.printStackTrace();
            }

        }
    }

}
