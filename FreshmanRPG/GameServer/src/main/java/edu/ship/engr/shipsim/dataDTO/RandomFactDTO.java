package edu.ship.engr.shipsim.dataDTO;

import java.time.LocalDate;

/**
 * Contains info on a fact.
 */
public class RandomFactDTO
{
    private int factID;
    private String factText;
    private LocalDate startDate;
    private LocalDate endDate;
    private int npcID;

    /**
     * Create a new instance of a Random Fact.
     *
     * @param id        fact ID
     * @param ncpID     The npc that should spout this fact
     * @param factText  The text of the fact
     * @param startDate The starting valid date for a fact
     * @param endDate   The ending valid date for a fact
     */
    public RandomFactDTO(int id, int ncpID, String factText, LocalDate startDate, LocalDate endDate)
    {
        this.factID = id;
        this.npcID = ncpID;
        this.factText = factText;
        this.startDate = startDate;
        this.endDate = endDate;
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
        RandomFactDTO other = (RandomFactDTO) obj;
        if (endDate == null)
        {
            if (other.endDate != null)
            {
                return false;
            }
        }
        else if (!endDate.equals(other.endDate))
        {
            return false;
        }
        if (factID != other.factID)
        {
            return false;
        }
        if (factText == null)
        {
            if (other.factText != null)
            {
                return false;
            }
        }
        else if (!factText.equals(other.factText))
        {
            return false;
        }
        if (npcID != other.npcID)
        {
            return false;
        }
        if (startDate == null)
        {
            if (other.startDate != null)
            {
                return false;
            }
        }
        else if (!startDate.equals(other.startDate))
        {
            return false;
        }
        return true;
    }

    /**
     * @return endDate The ending valid date for a fact
     */
    public LocalDate getEndDate()
    {
        return this.endDate;
    }


    /**
     * @return this fact's unique ID
     */
    public int getFactID()
    {
        return factID;
    }

    /**
     * @return fact The text of the fact
     */
    public String getFactText()
    {
        return this.factText;
    }

    /**
     * @return factID A unique ID for this fact
     */
    public int getId()
    {
        return this.factID;
    }

    /**
     * @return the ID of the NPC that should spout this fact
     */
    public int getNpcID()
    {
        return npcID;
    }

    /**
     * @return startDate The starting valid date for a fact
     */
    public LocalDate getStartDate()
    {
        return this.startDate;
    }


    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
        result = prime * result + factID;
        result = prime * result + ((factText == null) ? 0 : factText.hashCode());
        result = prime * result + npcID;
        result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
        return result;
    }


    /**
     * @param endDate the last date this fact should be available
     */
    public void setEndDate(LocalDate endDate)
    {
        this.endDate = endDate;
    }

    /**
     * @param fact the text of the fact
     */
    public void setFact(String fact)
    {
        this.factText = fact;
    }

    /**
     * @param startDate the first date this fact should be available
     */
    public void setStartDate(LocalDate startDate)
    {
        this.startDate = startDate;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        String s = "FactInfo(fact: " + this.getFactText() + ", NCP ID: " + this.getNpcID() + ", startDate: "
                + this.getStartDate().toString() + ", endDate: " + this.getEndDate().toString();

        return s;
    }
}
