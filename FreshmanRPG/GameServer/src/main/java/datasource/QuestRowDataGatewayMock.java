package datasource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

import criteria.QuestCompletionActionParameter;
import dataENUM.QuestCompletionActionType;
import datatypes.Position;
import datatypes.QuestsForTest;

/**
 * A Mock implementation of the data source layer for the quest table
 *
 * @author merlin
 *
 */
public class QuestRowDataGatewayMock implements QuestRowDataGateway
{

	private class QuestData
	{
		private String questTitle;
		private String questDescription;
		private String mapName;
		private Position position;
		private int questID;
		private int experiencePointsGained;
		private int adventuresForFulfillment;
		private Date startDate;
		private Date endDate;
		private QuestCompletionActionType completionActionType;
		private QuestCompletionActionParameter completionActionParameter;

		public QuestData(int questID, String questTitle, String questDescription, String mapName, Position position,
						 int experiencePointsGained, int adventuresForFulfillment,
						 QuestCompletionActionType completionActionType, QuestCompletionActionParameter completionActionParam,
						 Date startDate, Date endDate)
		{
			this.questTitle = questTitle;
			this.questDescription = questDescription;
			this.mapName = mapName;
			this.position = position;
			this.questID = questID;
			this.experiencePointsGained = experiencePointsGained;
			this.adventuresForFulfillment = adventuresForFulfillment;
			this.completionActionType = completionActionType;
			this.completionActionParameter = completionActionParam;
			this.startDate = startDate;
			this.endDate = endDate;
		}

		public int getAdventuresForFulfillment()
		{
			return adventuresForFulfillment;
		}

		public QuestCompletionActionParameter getCompletionActionParameter()
		{
			return completionActionParameter;
		}

		public QuestCompletionActionType getCompletionActionType()
		{
			return completionActionType;
		}

		public int getExperiencePointsGained()
		{
			return experiencePointsGained;
		}

		public String getMapName()
		{
			return mapName;
		}

		public Position getPosition()
		{
			return position;
		}

		public String getQuestDescription()
		{
			return questDescription;
		}

		public int getQuestID()
		{
			return questID;
		}

		public String getQuestTitle()
		{
			return questTitle;
		}

		public Date getStartDate()
		{
			return startDate;
		}

		public Date getEndDate()
		{
			return endDate;
		}

	}

	/**
	 * Map quest ID to questDescription
	 */
	private static HashMap<Integer, QuestData> questInfo;

	/**
	 * Get the IDs of the quests that are supposed to trigger at a specified map
	 * location
	 *
	 * @param mapName the name of the map
	 * @param position the position on the map
	 * @return the quest IDs
	 * @throws DatabaseException shouldn't
	 */
	public static ArrayList<Integer> findQuestsForMapLocation(String mapName, Position position)
			throws DatabaseException
	{
		if (questInfo == null)
		{
			new QuestRowDataGatewayMock(1).resetData();
		}
		ArrayList<Integer> results = new ArrayList<>();
		for (QuestData q : questInfo.values())
		{
			if ((q.getMapName().equals(mapName)) && (q.getPosition().equals(position)))
			{
				results.add(q.getQuestID());
			}
		}
		return results;
	}

	private int questID;
	private String questDescription;

	private String triggerMapName;

	private Position triggerPosition;
	private int experiencePointsGained;
	private int adventuresForFulfillment;
	private QuestCompletionActionType completionActionType;
	private QuestCompletionActionParameter completionActionParameter;
	private String questTitle;
	private Date startDate;
	private Date endDate;

	/**
	 * Get the row data gateway object for an existing quest
	 *
	 * @param questID the quest's unique ID
	 * @throws DatabaseException shouldn't
	 */
	public QuestRowDataGatewayMock(int questID) throws DatabaseException
	{
		if (questInfo == null)
		{
			resetData();
		}
		if (questInfo.containsKey(questID))
		{
			QuestData questData = questInfo.get(questID);
			this.questTitle = questData.getQuestTitle();
			this.questDescription = questData.getQuestDescription();
			this.triggerMapName = questData.getMapName();
			this.triggerPosition = questData.getPosition();
			this.questID = questID;
			this.experiencePointsGained = questData.getExperiencePointsGained();
			this.adventuresForFulfillment = questData.getAdventuresForFulfillment();
			this.completionActionType = questData.getCompletionActionType();
			this.completionActionParameter = questData.getCompletionActionParameter();
			this.startDate = questData.getStartDate();
			this.endDate = questData.getEndDate();
		}
		else
		{
			throw new DatabaseException("Couldn't find quest with ID " + questID);
		}
	}

	/**
	 * @param title - Title of the quest
	 * @param description - Description of the quest
	 * @param mapName - The map name
	 * @param position - The Position
	 * @param experienceGained - The exp gained
	 * @param adventuresForFullfillment - Adventures to fulfill the quest
	 * @param completionActionType - How you complete the quest
	 * @param completionActionParameter - What you need to complete the quest
	 * @param startDate - Start date
	 * @param endDate - End date
	 */
	public QuestRowDataGatewayMock(
			String title,
			String description,
			String mapName,
			Position position,
			int experienceGained,
			int adventuresForFullfillment,
			QuestCompletionActionType completionActionType,
			QuestCompletionActionParameter completionActionParameter,
			Date startDate,
			Date endDate
	)
	{
		if (questInfo == null)
		{
			resetData();
		}


		this.questID = Collections.max(questInfo.keySet()) + 1;
		this.questTitle = title;
		this.questDescription = description;
		this.triggerMapName = mapName;
		this.triggerPosition = position;
		this.experiencePointsGained = experienceGained;
		this.adventuresForFulfillment = adventuresForFullfillment;
		this.completionActionType = completionActionType;
		this.completionActionParameter = completionActionParameter;
		this.startDate = startDate;
		this.endDate = endDate;

		QuestData questData = new QuestData(this.questID, title,
				description, mapName, position, experienceGained,
				adventuresForFullfillment, completionActionType,
				completionActionParameter, startDate, endDate);

		questInfo.put(this.questID, questData);
	}

	/**
	 * @see datasource.QuestRowDataGateway#getAdventuresForFulfillment()
	 */
	@Override
	public int getAdventuresForFulfillment()
	{
		return adventuresForFulfillment;
	}

	/**
	 * @see datasource.QuestRowDataGateway#getCompletionActionParameter()
	 */
	@Override
	public QuestCompletionActionParameter getCompletionActionParameter()
	{
		return completionActionParameter;
	}

	/**
	 * @see datasource.QuestRowDataGateway#getCompletionActionType()
	 */
	@Override
	public QuestCompletionActionType getCompletionActionType()
	{
		return completionActionType;
	}

	/**
	 * @see datasource.QuestRowDataGateway#getExperiencePointsGained()
	 */
	@Override
	public int getExperiencePointsGained()
	{
		return experiencePointsGained;
	}

	/**
	 * @see datasource.QuestRowDataGateway#getQuestDescription()
	 */
	@Override
	public String getQuestDescription()
	{
		return questDescription;
	}

	/**
	 * @see datasource.QuestRowDataGateway#getQuestID()
	 */
	@Override
	public int getQuestID()
	{
		return questID;
	}

	/**
	 * @see datasource.QuestRowDataGateway#getQuestTitle()
	 */
	@Override
	public String getQuestTitle()
	{
		return questTitle;
	}

	/**
	 * @see datasource.QuestRowDataGateway#getTriggerMapName()
	 */
	@Override
	public String getTriggerMapName()
	{
		return triggerMapName;
	}

	/**
	 * @see datasource.QuestRowDataGateway#getTriggerPosition()
	 */
	@Override
	public Position getTriggerPosition()
	{
		return triggerPosition;
	}

	/**
	 * @see datasource.QuestRowDataGateway#resetData()
	 */
	@Override
	public void resetData()
	{
		questInfo = new HashMap<>();
		for (QuestsForTest p : QuestsForTest.values())
		{
			questInfo.put(p.getQuestID(), new QuestData(p.getQuestID(), p.getQuestTitle(), p.getQuestDescription(),
					p.getMapName(), p.getPosition(), p.getExperienceGained(), p.getAdventuresForFulfillment(),
					p.getCompletionActionType(), p.getCompletionActionParameter(), p.getStartDate(), p.getEndDate()));
		}
	}

	/**
	 * @return the first day the quest is available
	 */
	@Override
	public Date getStartDate()
	{
		return startDate;
	}

	/**
	 * @return the last day the quest is available
	 */
	@Override
	public Date getEndDate()
	{
		return endDate;
	}

	/**
	 * @see datasource.QuestRowDataGateway#persist()
	 */
	@Override
	public void persist() throws DatabaseException
	{
		QuestData questData = new QuestData(this.questID, this.questTitle,
				this.questDescription, this.triggerMapName, this.triggerPosition,
				this.experiencePointsGained,
				this.adventuresForFulfillment, this.completionActionType,
				this.completionActionParameter, this.startDate, this.endDate);

		questInfo.put(this.questID, questData);
	}

	/**
	 * @see datasource.QuestRowDataGateway#setQuestTitle(java.lang.String)
	 */
	public void setQuestTitle(String questTitle)
	{
		this.questTitle = questTitle;
	}

	/**
	 * @see datasource.QuestRowDataGateway#setQuestDescription(java.lang.String)
	 */
	public void setQuestDescription(String questDescription)
	{
		this.questDescription = questDescription;
	}

	/**
	 * @see datasource.QuestRowDataGateway#setTriggerMapName(java.lang.String)
	 */
	public void setTriggerMapName(String triggerMapName)
	{
		this.triggerMapName = triggerMapName;
	}

	/**
	 * @see datasource.QuestRowDataGateway#setTriggerPosition(datatypes.Position)
	 */
	public void setTriggerPosition(Position triggerPosition)
	{
		this.triggerPosition = triggerPosition;
	}

	/**
	 * @see datasource.QuestRowDataGateway#setExperiencePointsGained(int)
	 */
	public void setExperiencePointsGained(int experiencePointsGained)
	{
		this.experiencePointsGained = experiencePointsGained;
	}

	/**
	 * @see datasource.QuestRowDataGateway#setAdventuresForFulfillment(int)
	 */
	public void setAdventuresForFulfillment(int adventuresForFulfillment)
	{
		this.adventuresForFulfillment = adventuresForFulfillment;
	}

	/**
	 * @see datasource.QuestRowDataGateway#setCompletionActionParameter(criteria.QuestCompletionActionParameter)
	 */
	public void setCompletionActionParameter(QuestCompletionActionParameter completionActionParameter)
	{
		this.completionActionParameter = completionActionParameter;
	}

	/**
	 * @see datasource.QuestRowDataGateway#setCompletionActionType(dataENUM.QuestCompletionActionType)
	 */
	public void setCompletionActionType(QuestCompletionActionType completionActionType)
	{
		this.completionActionType = completionActionType;
	}

	/**
	 * @see datasource.QuestRowDataGateway#setStartDate(java.util.Date)
	 */
	public void setStartDate(Date startDate)
	{
		this.startDate = startDate;
	}

	/**
	 * @see datasource.QuestRowDataGateway#setEndDate(java.util.Date)
	 */
	public void setEndDate(Date endDate)
	{
		this.endDate = endDate;
	}

	/**
	 * Tests removing a quest based on ID
	 * @throws DatabaseException will if the quest is not found
	 */
	@Override
	public void remove() throws DatabaseException
	{
		QuestData data = questInfo.get(questID);
		if (data == null)
		{
			throw new DatabaseException(String.format("Could not find quest with Id of %d", questID));
		}
		else
		{
			questInfo.remove(questID);
		}
	}

}
