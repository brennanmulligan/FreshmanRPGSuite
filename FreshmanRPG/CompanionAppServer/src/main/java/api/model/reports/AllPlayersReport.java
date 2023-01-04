package api.model.reports;

import edu.ship.engr.shipsim.dataDTO.PlayerDTO;
import edu.ship.engr.shipsim.model.Report;

import java.util.ArrayList;

/**
 * A report containing a list of players. Assumes that the sender puts all of
 * the players into the list
 *
 * @author Merlin
 */
public class AllPlayersReport implements Report
{

    private final ArrayList<PlayerDTO> list;

    /**
     * @param list the players
     */
    public AllPlayersReport(ArrayList<PlayerDTO> list)
    {
        this.list = list;
    }

    /**
     * @return the players
     */
    public ArrayList<PlayerDTO> getPlayerInfo()
    {
        return list;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((list == null) ? 0 : list.hashCode());
        return result;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        AllPlayersReport other = (AllPlayersReport) obj;
        if (list == null)
        {
            return other.list == null;
        }
        else
        {
            return list.equals(other.list);
        }
    }
}
