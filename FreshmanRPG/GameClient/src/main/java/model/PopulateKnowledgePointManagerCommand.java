package model;

import java.util.ArrayList;

import communication.messages.KnowledgePointPrizeMessage;
import dataDTO.KnowledgePointPrizeDTO;

/**
 *
 * @author Christian C, Andrew M
 *
 *         This command gets all the prizes and will populate the knowledge
 *         point manager
 *
 */
public class PopulateKnowledgePointManagerCommand extends Command
{

	ArrayList<KnowledgePointPrizeDTO> dtos;

	/**
	 * Constructor for the Populate Knowledge Point Manager Command
	 *
	 * @param dtos a list of the current knowledge point prizes
	 */
	public PopulateKnowledgePointManagerCommand(ArrayList<KnowledgePointPrizeDTO> dtos)
	{
		this.dtos = dtos;

	}

	public PopulateKnowledgePointManagerCommand(KnowledgePointPrizeMessage message)
	{
		this.dtos = message.getDtos();

	}

	/**
	 * getter for name
	 *
	 * @param i index
	 * @return name
	 */
	public String getName(int i)
	{
		return dtos.get(i).getName();
	}

	/**
	 * getter for price
	 *
	 * @param i index
	 * @return price
	 */
	public int getPrice(int i)
	{
		return dtos.get(i).getCost();
	}

	/**
	 * getter for the description
	 *
	 * @param i index
	 * @return description
	 */
	public String getDescription(int i)
	{
		return dtos.get(i).getDescription();
	}

	/**
	 * getter for the list of DTOS
	 *
	 * @return list of DTOs
	 */
	public ArrayList<KnowledgePointPrizeDTO> getDtos()
	{
		return dtos;
	}

	/**
	 * Executes the command
	 *
	 * @return true
	 */
	@Override
	boolean execute()
	{

		KnowledgePointsManager.getSingleton().add(dtos);
		return true;
	}

}