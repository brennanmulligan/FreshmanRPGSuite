package edu.ship.engr.shipsim.dataDTO;

import java.io.Serializable;

/**
 * @author Andrew M, Christrian C.
 * <p>
 * This class is the DTO that holds a doubloon prize details
 */

public class DoubloonPrizeDTO implements Serializable
{

    private static final long serialVersionUID = 1L;
    private String name;
    private int cost;
    private String description;

    /**
     * the constructor for the DTO
     *
     * @param name        the name of the prize
     * @param cost        the cost of the prize
     * @param description the description of the prize
     */
    public DoubloonPrizeDTO(String name, int cost, String description)
    {
        this.name = name;
        this.cost = cost;
        this.description = description;
    }


    /**
     * get the name for the prize
     *
     * @return the DTO prize name
     */
    public String getName()
    {
        return name;
    }


    /**
     * gets the cost
     *
     * @return cost
     */
    public int getCost()
    {
        return cost;
    }


    /**
     * gets the prize description
     *
     * @return the description
     */
    public String getDescription()
    {
        return description;
    }

    @Override
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("Name: " + this.name + ", ");
        sb.append("Desc: " + this.description + ", ");
        sb.append("Cost: " + this.cost);

        return sb.toString();
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + cost;
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }


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
        DoubloonPrizeDTO other = (DoubloonPrizeDTO) obj;
        if (cost != other.cost)
        {
            return false;
        }
        if (description == null)
        {
            if (other.description != null)
            {
                return false;
            }
        }
        else if (!description.equals(other.description))
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


}
