package edu.ship.engr.shipsim.dataDTO;

/**
 * @author Chris Roadcap
 * @author Ben Lehman
 */
public class PlayerMapLocationDTO
{
    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((map == null) ? 0 : map.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
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
        PlayerMapLocationDTO other = (PlayerMapLocationDTO) obj;
        if (map == null)
        {
            if (other.map != null)
            {
                return false;
            }
        }
        else if (!map.equals(other.map))
        {
            return false;
        }
        if (name == null)
        {
            if (other.name != null)
            {
                return false;
            }
        }
        else if (!name.equals(other.name))
        {
            return false;
        }
        return true;
    }

    private String name;
    private String map;
    private int x;
    private int y;

    /**
     * @param name from PlayerDTO
     * @param map  from PlayerDTO
     */
    public PlayerMapLocationDTO(String name, String map)
    {
        this.name = name;
        this.map = map;
    }

    /**
     * @param x map position x
     * @param y map position y
     */
    public PlayerMapLocationDTO(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    /**
     * @return String name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @return String map
     */
    public String getMap()
    {
        return map;
    }

    /**
     * get x position
     *
     * @return x map position x
     */
    public int getX()
    {
        return x;
    }

    /**
     * get y position
     *
     * @return y map position y
     */
    public int getY()
    {
        return y;
    }
}