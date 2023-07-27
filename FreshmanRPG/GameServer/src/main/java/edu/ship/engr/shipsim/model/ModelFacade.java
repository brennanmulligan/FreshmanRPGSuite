package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datasource.DatabaseManager;

import java.util.Timer;

/**
 * A facade that allows things outside the model to change or retrieve things
 * from inside the model
 *
 * @author Merlin
 */
public class ModelFacade
{

    private static ModelFacade singleton;
    private int dbTouchCounter = 0;

    /**
     * @return the only one of these there is
     */
    public synchronized static ModelFacade getSingleton()
    {
        if (singleton == null)
        {
            singleton = new ModelFacade();
        }
        return singleton;
    }

    /**
     * Used for testing to reset the state of the model
     */
    public synchronized static void resetSingleton()
    {
        OptionsManager.getSingleton().assertTestMode();
        if (singleton != null)
        {
            try
            {
                singleton.commandQueue.emptyQueue();
            }
            catch (InterruptedException e)
            {
                throw new RuntimeException(e);
            }
            singleton.commandsPending = false;
        }
        singleton = null;
    }

    private final InformationQueue commandQueue;
    private boolean commandsPending;

    /**
     * Checks if commands are pending
     *
     * @return if commands are pending
     */
    public boolean hasCommandsPending()
    {
        return commandsPending;
    }

    /**
     * Make the default constructor private
     */
    private ModelFacade()
    {
        commandQueue = new InformationQueue();

        Timer timer = new Timer();
        ProcessCommandQueueTask task = new ProcessCommandQueueTask();
        timer.schedule(task, 0, 250);
    }

    private class ProcessCommandQueueTask extends java.util.TimerTask
    {
        /**
         * @see java.util.TimerTask#run()
         */
        @Override
        public void run()
        {
            synchronized (commandQueue)
            {

                while (commandQueue.getQueueSize() > 0)
                {
                    Command cmd;
                    try
                    {
                        cmd = (Command) commandQueue.getInfoPacket();
                        cmd.execute();
                        if (commandQueue.getQueueSize() == 0)
                        {
                            commandsPending = false;
                        }
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                }
            }
        }

    }

    /**
     * Queue a command for the model to process
     *
     * @param cmd the command to be processed
     */
    public void queueCommand(Command cmd)
    {
        synchronized (commandQueue)
        {
            commandsPending = true;
            commandQueue.queueInfoPacket(cmd);
        }
    }

    /**
     * @return The number of commands in queue
     */
    public int queueSize()
    {
        return commandQueue.getQueueSize();
    }
}
