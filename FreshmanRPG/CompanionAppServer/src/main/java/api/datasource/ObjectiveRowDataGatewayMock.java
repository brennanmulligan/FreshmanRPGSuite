package api.datasource;

import java.util.HashMap;

import criteria.ObjectiveCompletionCriteria;
import dataENUM.ObjectiveCompletionType;
import datasource.DatabaseException;
import datasource.ObjectiveTableDataGateway;
import datasource.TableDataGatewayManager;
import model.ObjectiveRecord;
import datatypes.ObjectivesForTest;

/**
 * A mock implementation for the Objectives table in the data source.
 */
public class ObjectiveRowDataGatewayMock implements ObjectiveRowDataGateway
{
    private static class CompositeKey
    {
        private final int questId;
        private final int objectiveID;

        public CompositeKey(int questId, int objectiveID)
        {
            this.questId = questId;
            this.objectiveID = objectiveID;
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
            return questId == key.questId && objectiveID == key.objectiveID;
        }

        @Override
        public int hashCode()
        {
            int result = questId;
            result = 31 * result + objectiveID;
            return result;
        }
    }

    private static HashMap<CompositeKey, ObjectiveRecord> mockObjectiveTable;

    private final ObjectiveRecord currentRecord;

    /**
     * Construct and initialize a new ObjectiveRowDataGatewayMock. (Finder)
     *
     * @param questId - quest ID objective belongs to
     * @param objectiveID - ID of this objective
     * @throws DatabaseException - if objective not found
     */
    public ObjectiveRowDataGatewayMock(int questId, int objectiveID) throws
            DatabaseException
    {
        if (mockObjectiveTable == null)
        {
            resetData();
        }

        final ObjectiveRecord tempRecord =
                mockObjectiveTable.get(new CompositeKey(questId, objectiveID));
        if (tempRecord == null)
        {
            throw new DatabaseException("Objective ID was not found.");
        }
        currentRecord = tempRecord;
    }

    /**
     * Construct and initialize a new ObjectiveRowDataGatewayMock with an objective ID.
     * (Creator)
     *
     * @param objectiveID - ID of this objective
     * @param objectiveDescription - description for objective
     * @param questId - quest ID objective belongs to
     * @param experiencePointsGained - experience points gained upon completion of objective
     * @param objectiveCompletionType - objective completion type
     * @param objectiveCompletionCriteria - objective completion criteria
     */
    public ObjectiveRowDataGatewayMock(int objectiveID, String objectiveDescription, int questId,
                                       int experiencePointsGained, ObjectiveCompletionType objectiveCompletionType,
                                       ObjectiveCompletionCriteria objectiveCompletionCriteria)
    {
        if (mockObjectiveTable == null)
        {
            resetData();
        }
        final ObjectiveRecord record = new ObjectiveRecord(questId, objectiveID, objectiveDescription,
                experiencePointsGained, objectiveCompletionType, objectiveCompletionCriteria);
        mockObjectiveTable.put(new CompositeKey(record.getQuestID(), record.getObjectiveID()), record);
        currentRecord = record;
    }

    /**
     * Construct and initialize a new ObjectiveRowDataGatewayMock. (Creator)
     *
     * @param objectiveDescription - description for objective
     * @param questId - quest ID objective belongs to
     * @param experiencePointsGained - experience points gained upon completion of objective
     * @param objectiveCompletionType - objective completion type
     * @param objectiveCompletionCriteria - objective completion criteria
     * @throws DatabaseException - shouldn't
     */
    public ObjectiveRowDataGatewayMock(String objectiveDescription, int questId,
                                       int experiencePointsGained, ObjectiveCompletionType objectiveCompletionType,
                                       ObjectiveCompletionCriteria objectiveCompletionCriteria) throws DatabaseException
    {
        if (mockObjectiveTable == null)
        {
            resetData();
        }
        ObjectiveTableDataGateway objectiveGateway =
                (ObjectiveTableDataGateway) TableDataGatewayManager.getSingleton().getTableGateway("Objective");
        final int objectiveID = objectiveGateway.getNextObjectiveID(questId);
        final ObjectiveRecord record = new ObjectiveRecord(questId, objectiveID, objectiveDescription,
                experiencePointsGained, objectiveCompletionType, objectiveCompletionCriteria);
        mockObjectiveTable.put(new CompositeKey(record.getQuestID(), record.getObjectiveID()), record);
        currentRecord = record;
    }

    /**
     * @see ObjectiveRowDataGateway#resetData()
     */
    @Override
    public void resetData()
    {
        mockObjectiveTable = new HashMap<>();
        for (ObjectivesForTest objective : ObjectivesForTest.values())
        {
            final ObjectiveRecord record = new ObjectiveRecord(objective.getQuestID(), objective.getObjectiveID(),
                    objective.getObjectiveDescription(), objective.getExperiencePointsGained(), objective.getCompletionType(),
                    objective.getCompletionCriteria());
            mockObjectiveTable.put(new CompositeKey(objective.getQuestID(), objective.getObjectiveID()), record);
        }
    }

    /**
     * @see ObjectiveRowDataGateway#getObjectiveDescription()
     */
    @Override
    public String getObjectiveDescription()
    {
        return currentRecord.getObjectiveDescription();
    }

    /**
     * @see ObjectiveRowDataGateway#getQuestID()
     */
    @Override
    public int getQuestID()
    {
        return currentRecord.getQuestID();
    }

    /**
     * @see ObjectiveRowDataGateway#getExperiencePointsGained()
     */
    @Override
    public int getExperiencePointsGained()
    {
        return currentRecord.getExperiencePointsGained();
    }

    /**
     * @see ObjectiveRowDataGateway#getCompletionType()
     */
    @Override
    public ObjectiveCompletionType getCompletionType()
    {
        return currentRecord.getCompletionType();
    }

    /**
     * @see ObjectiveRowDataGateway#getCompletionCriteria()
     */
    @Override
    public ObjectiveCompletionCriteria getCompletionCriteria()
    {
        return currentRecord.getCompletionCriteria();
    }

    /**
     * @see ObjectiveRowDataGateway#getObjectiveID()
     */
    @Override
    public int getObjectiveID()
    {
        return currentRecord.getObjectiveID();
    }

    /**
     * @see ObjectiveRowDataGateway#removeObjective()
     */
    @Override
    public void removeObjective()
    {
        final CompositeKey key = new CompositeKey(currentRecord.getQuestID(), currentRecord.getObjectiveID());
        mockObjectiveTable.remove(key);
    }

    /**
     * @see ObjectiveRowDataGateway#persist()
     */
    @Override
    public void persist()
    {
        mockObjectiveTable.put(new CompositeKey(currentRecord.getQuestID(), currentRecord.getObjectiveID()), currentRecord);
    }

    /**
     * Sets the objective id for currentRecord
     * @see ObjectiveRowDataGateway#setObjectiveID(int)
     */
    @Override
    public void setObjectiveID(int id)
    {
        this.currentRecord.setObjectiveID(id);
    }

    /**
     * sets the objective description for currentRecord
     * @see ObjectiveRowDataGateway#setObjectiveDescription(java.lang.String)
     */
    @Override
    public void setObjectiveDescription(String description)
    {
        this.currentRecord.setObjectiveDescription(description);

    }

    /**
     * Sets the questId for currentRecord
     * @see ObjectiveRowDataGateway#setQuestID(int)
     */
    @Override
    public void setQuestID(int id)
    {
        this.currentRecord.setQuestID(id);
    }

    /**
     * Sets the experiencePointsGained for currentRecord
     * @see ObjectiveRowDataGateway#setExperiencePointsGained(int)
     */
    @Override
    public void setExperiencePointsGained(int exp)
    {
        this.currentRecord.setExperiencePointsGained(exp);
    }

    /**
     * Sets the completionType for currentRecord
     * @see ObjectiveRowDataGateway#setCompletionType(ObjectiveCompletionType)
     */
    @Override
    public void setCompletionType(ObjectiveCompletionType completionType)
    {
        this.currentRecord.setCompletionType(completionType);
    }

    /**
     * Set the completionCriteria for currentRecord
     * @see ObjectiveRowDataGateway#setCompletionCriteria(ObjectiveCompletionCriteria)
     */
    @Override
    public void setCompletionCriteria(ObjectiveCompletionCriteria criteria)
    {
        this.currentRecord.setCompletionCriteria(criteria);
    }

}
