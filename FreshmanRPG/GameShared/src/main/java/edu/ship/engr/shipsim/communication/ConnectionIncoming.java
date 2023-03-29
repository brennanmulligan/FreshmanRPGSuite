package edu.ship.engr.shipsim.communication;

import edu.ship.engr.shipsim.communication.handlers.MessageHandlerSet;
import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datasource.DatabaseManager;
import edu.ship.engr.shipsim.datasource.LoggerManager;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.net.Socket;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Responsible for communication between the server and a single connected
 * player
 *
 * @author Merlin
 */
public class ConnectionIncoming implements Runnable
{

    private ObjectInputStream istream;
    private Socket socket;
    private MessageHandlerSet messageHandlers;
    private Logger logger;

    /**
     * @param socket    Socket being used. Will be null for JUnit testing
     * @param processor the message processor which should handle messages that
     *                  come in via this connection
     * @throws IOException Exception thrown for invalid input or output
     */
    public ConnectionIncoming(Socket socket, MessageHandlerSet processor) throws IOException
    {

        this.socket = socket;
        this.messageHandlers = processor;
        logger = LoggerManager.getSingleton().getLogger();
    }


    /**
     * @see java.lang.Runnable#run()
     */
    public void run()
    {

        try
        {
            if (socket != null)
            {
                this.istream = new ObjectInputStream(socket.getInputStream());
            }
            while (!Thread.currentThread().isInterrupted())
            {
                if (socket != null)
                {
                    if (socket.getInputStream().available() == 0)
                    {
                        Thread.sleep(100);
                        continue;
                    }
                }
                else
                {
                    Thread.sleep(100);
                    continue;
                }
                if (socket != null)
                {
                    Object inputObject = this.istream.readObject();
                    // catch the normal connection polling
                    if (Message.class.isAssignableFrom(inputObject.getClass()))
                    {
                        logger.log(Level.FINE, "Received " + inputObject.getClass());
                        processRequest((Message) inputObject);
                    }
                    else
                    {
                    }
                }

            }
        }
        catch (InterruptedException E)
        {
            // ok - we just need to quit
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
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

    /**
     * process a given request
     *
     * @param request the request
     */
    protected void processRequest(Message request)
    {
        try
        {
            messageHandlers.process(request);
        }
        catch (CommunicationException e)
        {
            System.out.println("No handler for " + request.getClass() + ": " + request + " which was received from "
                    + socket.getInetAddress().getHostName() + " at " + socket.getInetAddress().getHostAddress());
        }
    }

}
