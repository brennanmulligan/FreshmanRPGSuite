package dataENUM;

import criteria.AdventureCompletionCriteria;
import criteria.CriteriaIntegerDTO;
import criteria.CriteriaStringDTO;
import criteria.GameLocationDTO;

/**
 * The list of ways adventures can be completed
 *
 * @author Merlin
 */
public enum AdventureCompletionType
{
	/**
	 *
	 */
	REAL_LIFE(CriteriaStringDTO.class),

	/**
	 *
	 */
	MOVEMENT(GameLocationDTO.class),

	/**
	 *
	 */
	CHAT(CriteriaStringDTO.class),

	/**
	 *
	 */
	KNOWLEDGE_POINTS(CriteriaIntegerDTO.class),

	/**
	 */
	KEYSTROKE(CriteriaStringDTO.class),

	/**
	 *
	 */
	INTERACT(CriteriaIntegerDTO.class),


	/**
	 *
	 */
	TERMINAL(CriteriaStringDTO.class),


	/**
	 *
	 */
	FRIENDS(CriteriaIntegerDTO.class);

	/**
	 * Get the completion type with a given ID
	 *
	 * @param id the ID
	 * @return the appropriate completion type
	 */
	public static AdventureCompletionType findByID(int id)
	{
		return AdventureCompletionType.values()[id];
	}

	private Class<? extends AdventureCompletionCriteria> completionCriteriaType;

	AdventureCompletionType(Class<? extends AdventureCompletionCriteria> completionCriteriaType)
	{
		this.completionCriteriaType = completionCriteriaType;
	}

	/**
	 * @return the class of the adventure completion criteria
	 */
	public Class<? extends AdventureCompletionCriteria> getCompletionCriteriaType()
	{
		return completionCriteriaType;
	}

	/**
	 * @return the unique ID of the completion type
	 */
	public int getID()
	{
		return this.ordinal();
	}
}
