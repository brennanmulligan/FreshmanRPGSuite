package edu.ship.engr.shipsim.communication;

import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.communication.packers.MessagePackerSet;
import edu.ship.engr.shipsim.dataDTO.PlayerMapLocationDTO;
import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datasource.DatabaseManager;
import edu.ship.engr.shipsim.datasource.LoggerManager;
import edu.ship.engr.shipsim.datasource.PlayerConnectionRowDataGateway;
import edu.ship.engr.shipsim.datasource.ServerRowDataGateway;
import edu.ship.engr.shipsim.model.OptionsManager;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Responsible for communication between the server and a single connected
 * player
 *
 * @author Merlin
 */
public class ConnectionOutgoing implements Runnable
{

    private ObjectOutputStream ostream;
    /**
     *
     */
    private final StateAccumulator stateAccumulator;
    private final Logger logger;

    /**
     * @param socket           Socket being used - will be null for JUnit tests
     * @param stateAccumulator the accumulator that is gathering events that
     *                         should be sent to the other side
     * @param messagePackerSet the set of messagepackers the outgoing connection
     *                         should use
     * @throws IOException Exception thrown for invalid input or output
     */
    public ConnectionOutgoing(Socket socket, StateAccumulator stateAccumulator,
                              MessagePackerSet messagePackerSet)
            throws IOException
    {
        if (socket != null)
        {
            this.ostream = new ObjectOutputStream(socket.getOutputStream());
        }
        this.stateAccumulator = stateAccumulator;
        logger = LoggerManager.getSingleton().getLogger();
    }

    /**
     * @see java.lang.Runnable#run()
     */
    public void run()
    {
        try
        {
            boolean done = false;
            while (!Thread.currentThread().isInterrupted() && !done)
            {
                ArrayList<Message> msgs = stateAccumulator.getPendingMsgs();
                if (msgs.size() == 0)
                {
                    Thread.sleep(100);
                    continue;
                }

                if (ostream != null)
                {
                    for (Message msg : msgs)
                    {
                        try
                        {
                            if(!OptionsManager.getSingleton().isQuiet() ||
                                msg.getRelevantPlayerID() == stateAccumulator.getPlayerID() ||
                                !msg.isQuietMessage())
                            {
                                logger.log(Level.FINE,
                                        "Sending " + msg.getClass() + " to " +
                                                stateAccumulator.getPlayerID());

                                this.ostream.writeObject(msg);
                            }
                        }
                        catch (SocketException e)
                        {
                            cleanUpAndExit();
                            done = true;
                        }
                    }
                }

            }
        }
        catch (InterruptedException E)
        {
            cleanUpAndExit();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                DatabaseManager.getSingleton().closeConnection();
            }
            catch (SQLException | DatabaseException e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     *
     */
    private void cleanUpAndExit()
    {

    }

    /**
     * @return the state accumulator associated with this outgoing connecting
     */
    public StateAccumulator getStateAccumulator()
    {
        return stateAccumulator;
    }

    /**
     * @return the output stream we are writing to
     */
    public ObjectOutputStream getStream()
    {
        return this.ostream;
    }
}
