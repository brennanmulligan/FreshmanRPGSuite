package datasource;

import java.util.HashMap;

import criteria.AdventureCompletionCriteria;
import dataENUM.AdventureCompletionType;
import model.AdventureRecord;
import datatypes.AdventuresForTest;

/**
 * A mock implementation for the Adventures table in the data source.
 */
public class AdventureRowDataGatewayMock implements AdventureRowDataGateway
{
	private class CompositeKey
	{
		private final int questId;
		private final int adventureId;

		public CompositeKey(int questId, int adventureId)
		{
			this.questId = questId;
			this.adventureId = adventureId;
		}

		@Override
		public boolean equals(Object o)
		{
			if (this == o)
			{
				return true;
			}
			if (!(o instanceof CompositeKey))
			{
				return false;
			}
			CompositeKey key = (CompositeKey) o;
			return questId == key.questId && adventureId == key.adventureId;
		}

		@Override
		public int hashCode()
		{
			int result = questId;
			result = 31 * result + adventureId;
			return result;
		}
	}

	private static HashMap<CompositeKey, AdventureRecord> mockAdventureTable;

	private AdventureRecord currentRecord;

	/**
	 * Construct and initialize a new AdventureRowDataGatewayMock. (Finder)
	 *
	 * @param questId - quest ID adventure belongs to
	 * @param adventureId - ID of this adventure
	 * @throws DatabaseException - if adventure not found
	 */
	public AdventureRowDataGatewayMock(int questId, int adventureId) throws DatabaseException
	{
		if (mockAdventureTable == null)
		{
			resetData();
		}

		final AdventureRecord tempRecord =
				mockAdventureTable.get(new CompositeKey(questId, adventureId));
		if (tempRecord == null)
		{
			throw new DatabaseException("Adventure ID was not found.");
		}
		currentRecord = tempRecord;
	}

	/**
	 * Construct and initialize a new AdventureRowDataGatewayMock with an adventure ID.
	 * (Creator)
	 *
	 * @param adventureId - ID of this adventure
	 * @param adventureDescription - description for adventure
	 * @param questId - quest ID adventure belongs to
	 * @param experiencePointsGained - experience points gained upon completion of adventure
	 * @param adventureCompletionType - adventure completion type
	 * @param adventureCompletionCriteria - adventure completion criteria
	 */
	public AdventureRowDataGatewayMock(int adventureId, String adventureDescription, int questId,
									   int experiencePointsGained, AdventureCompletionType adventureCompletionType,
									   AdventureCompletionCriteria adventureCompletionCriteria)
	{
		if (mockAdventureTable == null)
		{
			resetData();
		}
		final AdventureRecord record = new AdventureRecord(questId, adventureId, adventureDescription,
				experiencePointsGained, adventureCompletionType, adventureCompletionCriteria);
		mockAdventureTable.put(new CompositeKey(record.getQuestID(), record.getAdventureID()), record);
		currentRecord = record;
	}

	/**
	 * Construct and initialize a new AdventureRowDataGatewayMock. (Creator)
	 *
	 * @param adventureDescription - description for adventure
	 * @param questId - quest ID adventure belongs to
	 * @param experiencePointsGained - experience points gained upon completion of adventure
	 * @param adventureCompletionType - adventure completion type
	 * @param adventureCompletionCriteria - adventure completion criteria
	 * @throws DatabaseException - shouldn't
	 */
	public AdventureRowDataGatewayMock(String adventureDescription, int questId,
									   int experiencePointsGained, AdventureCompletionType adventureCompletionType,
									   AdventureCompletionCriteria adventureCompletionCriteria) throws DatabaseException
	{
		if (mockAdventureTable == null)
		{
			resetData();
		}
		final int adventureId = AdventureTableDataGatewayMock.getSingleton().getNextAdventureID(questId);
		final AdventureRecord record = new AdventureRecord(questId, adventureId, adventureDescription,
				experiencePointsGained, adventureCompletionType, adventureCompletionCriteria);
		mockAdventureTable.put(new CompositeKey(record.getQuestID(), record.getAdventureID()), record);
		currentRecord = record;
	}

	/**
	 * @see datasource.AdventureRowDataGateway#resetData()
	 */
	@Override
	public void resetData()
	{
		mockAdventureTable = new HashMap<>();
		for (AdventuresForTest a : AdventuresForTest.values())
		{
			final AdventureRecord record = new AdventureRecord(a.getQuestID(), a.getAdventureID(),
					a.getAdventureDescription(), a.getExperiencePointsGained(), a.getCompletionType(),
					a.getCompletionCriteria());
			mockAdventureTable.put(new CompositeKey(a.getQuestID(), a.getAdventureID()), record);
		}
	}

	/**
	 * @see datasource.AdventureRowDataGateway#getAdventureDescription()
	 */
	@Override
	public String getAdventureDescription()
	{
		return currentRecord.getAdventureDescription();
	}

	/**
	 * @see datasource.AdventureRowDataGateway#getQuestID()
	 */
	@Override
	public int getQuestID()
	{
		return currentRecord.getQuestID();
	}

	/**
	 * @see datasource.AdventureRowDataGateway#getExperiencePointsGained()
	 */
	@Override
	public int getExperiencePointsGained()
	{
		return currentRecord.getExperiencePointsGained();
	}

	/**
	 * @see datasource.AdventureRowDataGateway#getCompletionType()
	 */
	@Override
	public AdventureCompletionType getCompletionType()
	{
		return currentRecord.getCompletionType();
	}

	/**
	 * @see datasource.AdventureRowDataGateway#getCompletionCriteria()
	 */
	@Override
	public AdventureCompletionCriteria getCompletionCriteria()
	{
		return currentRecord.getCompletionCriteria();
	}

	/**
	 * @see datasource.AdventureRowDataGateway#getAdventureId()
	 */
	@Override
	public int getAdventureId()
	{
		return currentRecord.getAdventureID();
	}

	/**
	 * @see datasource.AdventureRowDataGateway#removeAdventure()
	 */
	@Override
	public void removeAdventure()
	{
		final CompositeKey key = new CompositeKey(currentRecord.getQuestID(), currentRecord.getAdventureID());
		mockAdventureTable.remove(key);
	}

	/**
	 * @see datasource.AdventureRowDataGateway#persist()
	 */
	@Override
	public void persist() throws DatabaseException
	{
		mockAdventureTable.put(new CompositeKey(currentRecord.getQuestID(), currentRecord.getAdventureID()), currentRecord);
	}

	/**
	 * Sets the adventure id for currentRecord
	 * @see datasource.AdventureRowDataGateway#setAdventureId(int)
	 */
	@Override
	public void setAdventureId(int id)
	{
		this.currentRecord.setAdventureID(id);
	}

	/**
	 * sets the adventure description for currentRecord
	 * @see datasource.AdventureRowDataGateway#setAdventureDescription(java.lang.String)
	 */
	@Override
	public void setAdventureDescription(String description)
	{
		this.currentRecord.setAdventureDescription(description);

	}

	/**
	 * Sets the questId for currentRecord
	 * @see datasource.AdventureRowDataGateway#setQuestID(int)
	 */
	@Override
	public void setQuestID(int id)
	{
		this.currentRecord.setQuestID(id);
	}

	/**
	 * Sets the experiencePointsGained for currentRecord
	 * @see datasource.AdventureRowDataGateway#setExperiencePointsGained(int)
	 */
	@Override
	public void setExperiencePointsGained(int exp)
	{
		this.currentRecord.setExperiencePointsGained(exp);
	}

	/**
	 * Sets the completionType for currentRecord
	 * @see datasource.AdventureRowDataGateway#setCompletionType(dataENUM.AdventureCompletionType)
	 */
	@Override
	public void setCompletionType(AdventureCompletionType completionType)
	{
		this.currentRecord.setCompletionType(completionType);
	}

	/**
	 * Set the completionCriteria for currentRecord
	 * @see datasource.AdventureRowDataGateway#setCompletionCriteria(criteria.AdventureCompletionCriteria)
	 */
	@Override
	public void setCompletionCriteria(AdventureCompletionCriteria criteria)
	{
		this.currentRecord.setCompletionCriteria(criteria);
	}

}
