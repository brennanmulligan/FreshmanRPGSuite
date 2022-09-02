package edu.ship.engr.shipsim.communication;

import edu.ship.engr.shipsim.communication.packers.MessagePackerSet;
import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datasource.DatabaseManager;

import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;

class MockConnectionOutgoing extends ConnectionOutgoing
{

    public Connection manager;

    public MockConnectionOutgoing(Socket socket, StateAccumulator stateAccumulator, MessagePackerSet messagePackerSet) throws IOException
    {
        super(socket, stateAccumulator, messagePackerSet);
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
