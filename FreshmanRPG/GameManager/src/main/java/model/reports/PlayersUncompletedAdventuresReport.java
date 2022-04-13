package model.reports;

import java.util.ArrayList;

import model.AdventureRecord;
import model.QualifiedObservableReport;

/**
 * @author Scott Bowling
 *
 */
public class PlayersUncompletedAdventuresReport implements QualifiedObservableReport
{

	private ArrayList<AdventureRecord> list;


	/**
	 *
	 * @param list of Adventures
	 */
	public PlayersUncompletedAdventuresReport(ArrayList<AdventureRecord> list)
	{
		this.list = list;
	}

	/**
	 *
	 * @return the list of adventures
	 */
	public ArrayList<AdventureRecord> getAllUncompletedAdventures()
	{
		return list;
	}

}
