package edu.ship.engr.shipsim.criteria;

import edu.ship.engr.shipsim.model.Command;

/**
 * @author Calvin Niewind, Merlin
 */

public class CriteriaTimerDTO implements ObjectiveCompletionCriteria
{
    private static final long millisecondsPerMinute = 60000;
    private long time;
    private GameLocationDTO gameLocationDTO;

    public CriteriaTimerDTO(long time, GameLocationDTO gameLocationDTO)
    {
        setTime(time * millisecondsPerMinute);
        this.gameLocationDTO = gameLocationDTO;
    }

    public GameLocationDTO getGameLocationDTO()
    {
        return gameLocationDTO;
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