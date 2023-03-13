package edu.ship.engr.shipsim.communication.packers;

import edu.ship.engr.shipsim.communication.StateAccumulator;
import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.model.reports.SendMessageReport;

import java.util.ArrayList;

/**
 * These classes translates events in the system (as reported by Observables)
 * into Messages
 *
 * @author merlin
 */
public abstract class MessagePacker
{

    private StateAccumulator accumulator;

    /**
     * Build a message describing an event
     *
     * @param object the object pushed by the observable in its notification
     * @return the appropriate message
     */
    public abstract Message pack(SendMessageReport object);

    /**
     * Tell which type of report we pack
     *
     * @return the type of report we pack
     */
    public abstract ArrayList<Class<? extends SendMessageReport>> getReportTypesWePack();

    /**
     * @return the StateAccumulator attached to this packer
     */
    public StateAccumulator getAccumulator()
    {
        return accumulator;
    }

    /**
     * @param obs the StateAccumulator we should be associated with
     */
    public void setAccumulator(StateAccumulator obs)
    {
        this.accumulator = obs;
    }
}
