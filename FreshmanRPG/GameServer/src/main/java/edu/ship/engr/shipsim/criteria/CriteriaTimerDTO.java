package edu.ship.engr.shipsim.criteria;

import edu.ship.engr.shipsim.model.Command;

/**
 * @author Calvin Niewind,
 */

public class CriteriaTimerDTO implements ObjectiveCompletionCriteria
{
    private long time;

    private final long millisecondsPerMinute = 60000;

    /**
     *
     * @param time - time in minutes, translated to miliseconds
     */
    public CriteriaTimerDTO(long time)
    {
        setTime(time * millisecondsPerMinute);
    }

    private void setTime(long setTime)
    {
        time = setTime;
    }

    public long getTime()
    {
        return time;
    }

    @Override
    public String toString()
    {
        return ("Criteria Timer: " + (time / millisecondsPerMinute));
    }
}
