package edu.ship.engr.shipsim.communication;

import edu.ship.engr.shipsim.communication.handlers.MessageHandlerSet;
import edu.ship.engr.shipsim.communication.packers.MessagePackerSet;
import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.model.OptionsManager;
import edu.ship.engr.shipsim.model.ReportObserverConnector;
import edu.ship.engr.shipsim.model.Report;
import edu.ship.engr.shipsim.model.ReportObserver;
import edu.ship.engr.shipsim.model.reports.LogoutReport;
import edu.ship.engr.shipsim.model.reports.PlayerDisconnectedReport;

import java.io.IOException;
import java.net.Socket;

/**
 * All of the pieces necessary to manage the connection between the client and
 * the game server. Each side has one of these. It contains two threads (one for
 * reading the socket and one for writing to it). The incoming messages are
 * processed by a MessageProcessor (to which appropriate MessageHandlers should
 * be registered). A StateAccumulator holds a queue of the messages that should
 * go out on the outgoing connection.
 *
 * @author merlin
 */
public class ConnectionManager implements ReportObserver
{

    private ConnectionIncoming incoming;
    private ConnectionOutgoing outgoing;
    private Thread outgoingThread;
    private Thread incomingThread;

    private Socket socket;
    private MessagePackerSet messagePackerSet;
    private MessageHandlerSet handlerSet;
    /**
     *
     */
    StateAccumulator stateAccumulator;
    private int playerID;

    /**
     * Create everything necessary for building messages to send to the other
     * side and handling messages that come from the other side.
     *
     * @param sock                  the socket connection we are managing
     * @param stateAccumulator      the accumulator connecting us to the rest of the
     *                              system
     * @param messageHandlerSet     the set of MessageHandlers hat will process the
     *                              incoming messages on this connection
     * @param messagePackerSet      the set of MessagePackers that will pack
     *                              notifications from the model into outgoing messages
     * @param watchForSocketClosure true if we should shut things down when the
     *                              socket gets closed by the other side
     * @throws IOException caused by socket issues
     */
    public ConnectionManager(Socket sock, StateAccumulator stateAccumulator,
                             MessageHandlerSet messageHandlerSet,
                             MessagePackerSet messagePackerSet,
                             boolean watchForSocketClosure) throws IOException
    {
        this.socket = sock;
        this.messagePackerSet = messagePackerSet;
        this.handlerSet = messageHandlerSet;

        outgoing = new ConnectionOutgoing(sock, stateAccumulator, messagePackerSet);
        outgoingThread = new Thread(outgoing);
        outgoingThread.start();
        this.stateAccumulator = stateAccumulator;
        messageHandlerSet.setConnectionManager(this);

        incoming = new ConnectionIncoming(sock, messageHandlerSet);
        incomingThread = new Thread(incoming);
        incomingThread.start();

        if (watchForSocketClosure)
        {
            ConnectionListener cl = new ConnectionListener(outgoing.getStream(), 5000);
            cl.setDisconnectionAction(() ->
            {
                disconnect();
                ConnectionManagerList.getSingleton().remove(this);
//                try
//                {
                    ReportObserverConnector.getSingleton()
                            .sendReport(new PlayerDisconnectedReport(
                                    stateAccumulator.getPlayerID()));
//                }
//                catch (DatabaseException e)
//                {
//                    throw new RuntimeException(e);
//                }
                messagePackerSet.destroyAllObservables(stateAccumulator);
            });
            Thread watcherThread = new Thread(cl);
            watcherThread.start();
        }
        ReportObserverConnector.getSingleton()
                .registerObserver(this, LogoutReport.class);
    }

    /**
     * For testing purposes only
     */
    public ConnectionManager()
    {
    }

    public void disconnectFromPackers()
    {
        this.messagePackerSet.destroyAllObservables(this.stateAccumulator);
    }

    /**
     * Used by the client change which server we are connected to
     *
     * @param sock the new socket
     * @throws IOException shouldn't
     */
    public void moveToNewSocket(Socket sock) throws IOException
    {
        disconnect();
        this.socket = sock;

        outgoing = new ConnectionOutgoing(sock, this.stateAccumulator, messagePackerSet);
        outgoingThread = new Thread(outgoing);

        outgoingThread.start();

        incoming = new ConnectionIncoming(sock, handlerSet);
        incomingThread = new Thread(incoming);
        incomingThread.start();
    }

    /**
     * @return the state accumulator that is queuing messages for the outgoing
     * part of this connection
     */
    public StateAccumulator getStateAccumulator()
    {
        return outgoing.getStateAccumulator();
    }

    /**
     * Kill the threads and let go of the socket
     */
    public void disconnect()
    {
        try
        {
            socket.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        incomingThread.interrupt();
        outgoingThread.interrupt();
    }

    /**
     * @return the player ID for the player connected through this connection
     */
    public int getPlayerID()
    {
        return playerID;
    }

    /**
     * set the playerID for the player connected through this connection
     *
     * @param playerID the playerID
     */
    public void setPlayerID(int playerID)
    {
        this.playerID = playerID;
        if (stateAccumulator != null)
        {
            stateAccumulator.setPlayerId(playerID);
        }
    }

    /**
     * Receives a logout report and connects to the login server
     */
    @Override
    public void receiveReport(Report report)
    {
        if (report.getClass() == LogoutReport.class)
        {
            try
            {

                moveToNewSocket(
                        new Socket(OptionsManager.getSingleton().getLoginHost(), 1871));

            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

    }
}
