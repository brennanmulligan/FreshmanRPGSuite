package edu.ship.engr.shipsim.criteria;

import java.util.ArrayList;

/**
 * Holds the List of Quests
 *
 * @author Nahesha and Courtney
 */
public class QuestListCompletionParameter implements ObjectiveCompletionCriteria, QuestCompletionActionParameter, InteractableItemActionParameter
{
    private static final long serialVersionUID = 1L;

    private ArrayList<Integer> questIDs;

    /**
     * @param questIDs
     */
    public QuestListCompletionParameter(ArrayList<Integer> questIDs)
    {
        this.questIDs = questIDs;
    }

    /**
     * gets the list of Ids
     *
     * @return questIDs
     */
    public ArrayList<Integer> getQuestIDs()
    {
        return questIDs;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((questIDs == null) ? 0 : questIDs.hashCode());
        return result;
    }

    /**
     * (non-Javadoc)
     *
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
        QuestListCompletionParameter other = (QuestListCompletionParameter) obj;
        if (questIDs == null)
        {
            if (other.questIDs != null)
            {
                return false;
            }
        }
        else if (!questIDs.equals(other.questIDs))
        {
            return false;
        }
        return true;
    }

    /**
     * add a questID
     *
     * @param questID
     */
    public void addQuestID(int questID)
    {
        questIDs.add(questID);
    }


}
