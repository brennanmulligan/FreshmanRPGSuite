package edu.ship.engr.shipsim.model;

import com.badlogic.gdx.utils.Timer.Task;

import java.io.IOException;

/**
 * @author Merlin
 */
public class CommandProcessor extends Task
{

    private InformationQueue queue;

    /**
     * @param queue the queue through which we will receive commands
     */
    public CommandProcessor(InformationQueue queue)
    {
        this.queue = queue;
    }

    /**
     * @see com.badlogic.gdx.utils.Timer.Task#run()
     */
    @Override
    public void run()
    {
        while (queue.getQueueSize() > 0)
        {
            Command cmd;
            try
            {
                cmd = (Command) queue.getInfoPacket();
                cmd.execute();
            }
            catch (InterruptedException | IOException e)
            {
                e.printStackTrace();
            }
        }
    }

}
