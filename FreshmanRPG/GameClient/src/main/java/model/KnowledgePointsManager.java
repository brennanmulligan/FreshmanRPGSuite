package model;

import java.util.ArrayList;

import dataDTO.KnowledgePointPrizeDTO;
import model.reports.KnowledgePointPrizeListReport;

/**
 * @author am3243
 * @author Henry Wyatt
 *
 */

public class KnowledgePointsManager
{

	private static KnowledgePointsManager singleton;
	private ArrayList<KnowledgePointPrizeDTO> KnowledgePointsList;

	/**
	 * initialize resource with existing widgets. Act as database.
	 */
	public KnowledgePointsManager()
	{
		this.KnowledgePointsList = new ArrayList<>();
	}

	/**
	 * There should be only one
	 *
	 * @return the only player
	 */
	public static synchronized KnowledgePointsManager getSingleton()
	{
		if (singleton == null)
		{
			singleton = new KnowledgePointsManager();
		}
		return singleton;
	}

	/**
	 * Used only in testing to re-initialize the singleton
	 */
	public static void resetSingleton()
	{
		singleton = null;
	}

	/**
	 * Get the player that is playing on this client
	 *
	 * @return All Knowledge Points
	 */
	public ArrayList<KnowledgePointPrizeDTO> getAllKnowledgePoints()
	{
		return KnowledgePointsList;
	}

	/**
	 * @param prize KnowledgePointPrize being added.
	 */
	public void add(KnowledgePointPrizeDTO prize)
	{
		KnowledgePointsList.add(prize);
	}

	/**
	 * @param prizes KnowledgePointPrize List being added.
	 */
	public void add(ArrayList<KnowledgePointPrizeDTO> prizes)
	{
		KnowledgePointsList.addAll(prizes);
		receivedPrizeList(KnowledgePointsList);
	}

	/**
	 * @param prize KnowledgePointPrize being deleted.
	 */
	public void delete(KnowledgePointPrizeDTO prize)
	{
		KnowledgePointsList.remove(prize);
	}

	/**
	 * @param prizes KnowledgePointPrize List being deleted.
	 */
	public void delete(ArrayList<KnowledgePointPrizeDTO> prizes)
	{
		KnowledgePointsList.removeAll(prizes);
	}

	/**
	 * Method for handling the reception of the Prize List
	 * @param prizeList - List of DTO Prizes
	 */
	public void receivedPrizeList(ArrayList<KnowledgePointPrizeDTO> prizeList)
	{
		if (!KnowledgePointsManager.getSingleton().getAllKnowledgePoints().isEmpty())
		{
			QualifiedObservableConnector.getSingleton().sendReport(new KnowledgePointPrizeListReport(KnowledgePointsList));
		}
	}
}