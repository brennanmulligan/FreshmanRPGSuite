package edu.ship.engr.shipsim.communication;

import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Watches to make sure once a socket is created, we listen for how long it
 * remains to be alive
 *
 * @author nhydock
 */
public class ConnectionListener implements Runnable
{

    private ObjectOutputStream stream;
    private int rate;
    private Runnable disconnectAction;

    /**
     * Creates a new listener
     *
     * @param stream    The socket to listen to
     * @param frequency The rate at which we should poll the socket to check the
     *                  availability of the connection
     */
    public ConnectionListener(ObjectOutputStream stream, int frequency)
    {
        this.rate = frequency;
        this.stream = stream;
        // default disconnect action should be nothing
        disconnectAction = () ->
        {
            // Do nothing
        };
    }

    /**
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run()
    {
        try
        {
            // try writing to the connection at a regular interval
            // if it takes too long, force close the connection
            // as we can assume the connection has been lost
            while (!Thread.currentThread().isInterrupted())
            {
                if (stream != null)
                {
                    // write a single byte to poll if the socket is still
                    // available
                    stream.writeObject((byte) 0);
                }
                // poll every few seconds
                try
                {
                    Thread.sleep(rate);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }
        catch (IOException e)
        {
            disconnectAction.run();
        }
        finally
        {


        }
    }

    /**
     * Performs an action when a connection no longer exists
     *
     * @param action the thing we should do when this connection disconnects
     */
    public void setDisconnectionAction(Runnable action)
    {
        this.disconnectAction = action;
    }
}
