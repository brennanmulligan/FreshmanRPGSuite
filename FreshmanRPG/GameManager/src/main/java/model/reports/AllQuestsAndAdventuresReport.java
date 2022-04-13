package model.reports;

import java.util.ArrayList;

import model.QualifiedObservableReport;
import model.QuestRecord;

/**
 * A report containing a list of quests and adventures. Assumes that the sender puts all of
 * the quests and adventures into the list
 *
 * @author Darnell Martin & Darin Alleman
 *
 */
public class AllQuestsAndAdventuresReport implements QualifiedObservableReport
{

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
		AllQuestsAndAdventuresReport other = (AllQuestsAndAdventuresReport) obj;
		if (list == null)
		{
			if (other.list != null)
			{
				return false;
			}
		}
		else if (!list.equals(other.list))
		{
			return false;
		}
		return true;
	}

	private ArrayList<QuestRecord> list;

	/**
	 * @param list the quests
	 */
	public AllQuestsAndAdventuresReport(ArrayList<QuestRecord> list)
	{
		this.list = list;
	}

	/**
	 * @return the quests
	 */
	public ArrayList<QuestRecord> getQuestInfo()
	{
		return list;
	}

}
