package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.datasource.DatabaseException;

import java.util.Timer;
import java.util.TimerTask;

/**
 * NPC extends Player to be compatible with the player manager and simplify many
 * messages that can go to the client. Every NPC can have a different behavior.
 * The behavior is changed using the strategy pattern. However, instead of
 * passing in the behavior object, we pass in the name of the class defining
 * that behavior and we use reflection to create the behavior object.
 *
 * @author Steve
 */
public class NPC extends Player
{
    /**
     * Passes the number of ticks since it started to a behavior This is a private
     * class because it won't be used outside of NPC and the behavior is tested
     * through NPC's behavior
     *
     * @author Steve
     */
    private class NpcTimerTask extends TimerTask
    {
        NPCBehavior behavior;

        public NpcTimerTask(NPCBehavior behavior)
        {
            this.behavior = behavior;
        }

        @Override
        public void run()
        {
            this.behavior.doTimedEvent();
        }
    }

    private String behaviorClass;

    private NPCBehavior behavior;

    private Timer timer;

    /**
     * How often the npc behavior is queued
     */
    public static int TIMER_SECONDS = 1;

    /**
     * Instantiate the NPC
     */
    protected NPC(int playerID) throws DatabaseException
    {
        super(playerID);
        timer = new Timer();
    }

    /**
     * @return the class defining the behavior for this NPC
     */
    public String getBehaviorClass()
    {
        return behaviorClass;
    }

    /**
     * Used only for testing - sets the behavior object directly
     *
     * @param mb the behavior object this NPC should use
     */
    protected void setBehavior(NPCBehavior mb)
    {
        this.behavior = mb;
    }

    /**
     * Tell this NPC which behavior class it should use
     *
     * @param behaviorClass the name of the class specifying the behavior of this
     *                      NPC
     */
    protected void setBehaviorClass(String behaviorClass)
    {
        this.behaviorClass = behaviorClass;
        if (behaviorClass != null)
        {
            try
            {
                behavior =
                        (NPCBehavior) Class.forName(behaviorClass).getConstructor(Integer.TYPE).newInstance(getPlayerID());
            }
            catch (ReflectiveOperationException e)
            {
                behavior = null;
                e.printStackTrace();
            }
        }
    }

    /**
     * Begin timing for the npc's behavior
     */
    protected void start()
    {
        if (behavior != null && behavior.getPollingInterval() > 0)
        {
            // The event is going to occur every seconds*1000 ms (second param),
            // but will start after seconds*1000ms (first)
            // has passed. This is so behavior doesn't occur as soon as start
            // happens
            NpcTimerTask timedEvent;
            timedEvent = new NpcTimerTask(behavior);
            timer.scheduleAtFixedRate(timedEvent, behavior.getPollingInterval(), behavior.getPollingInterval());
        }
    }

    /**
     * Stop timing for the behavior
     */
    protected void stop()
    {
        timer.cancel();
        timer.purge();
    }

    /**
     * @return the behavior this NPC is using
     */
    public NPCBehavior getBehavior()
    {
        return behavior;
    }
}
