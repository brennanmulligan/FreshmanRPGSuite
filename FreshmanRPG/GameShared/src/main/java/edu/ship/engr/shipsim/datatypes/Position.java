package edu.ship.engr.shipsim.datatypes;

import java.io.Serial;
import java.io.Serializable;

/**
 * Just holds a row/column position
 *
 * @author merlin
 */
public class Position implements Serializable
{

    /**
     *
     */
    @Serial
    private static final long serialVersionUID = 1L;
    private final int row;
    private final int column;

    /**
     * @param r the row
     * @param c the column
     */
    public Position(int r, int c)
    {
        row = r;
        column = c;
    }

    /**
     * @return the row
     */
    public int getRow()
    {
        return row;
    }

    /**
     * @return the column
     */
    public int getColumn()
    {
        return column;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public final int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + column;
        result = prime * result + row;
        return result;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public final boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (!(obj instanceof Position))
        {
            return false;
        }
        Position other = (Position) obj;
        if (column != other.column)
        {
            return false;
        }
        if (row != other.row)
        {
            return false;
        }
        return true;
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        return "(" + row + ", " + column + ")";
    }

}
