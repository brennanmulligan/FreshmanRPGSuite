package api.datasource;

import datasource.DatabaseException;
import datasource.ObjectiveTableDataGateway;
import datasource.TableDataGatewayManager;
import datatypes.QuestsForTest;
import model.QuestRecord;

import java.util.ArrayList;

/**
 * Encapsulates access to the mock Quest table.
 */
public class QuestTableDataGatewayMock implements QuestTableDataGateway
{

    private static QuestTableDataGatewayMock instance;
    private ArrayList<QuestRecord> quests;

    /**
     * create a new object with the data from the appropriate enum
     */
    private QuestTableDataGatewayMock()
    {
        resetData();
    }

    /**
     * @return The gateway instance.
     */
    public static QuestTableDataGatewayMock getInstance()
    {
        if (instance == null)
        {
            instance = new QuestTableDataGatewayMock();
        }

        return instance;
    }

    /**
     * @see api.datasource.QuestTableDataGateway#getAllQuests()
     */
    @Override
    public ArrayList<QuestRecord> getAllQuests() throws DatabaseException
    {
        refreshObjectives();
        return quests;
    }

    /**
     * @see QuestTableDataGateway#resetData()
     */
    @Override
    public void resetData()
    {
        quests = new ArrayList<>();
        for (QuestsForTest testQuest : QuestsForTest.values())
        {
            try
            {
                quests.add(new QuestRecord(testQuest.getQuestID(),
                        testQuest.getQuestTitle(),
                        testQuest.getQuestDescription(),
                        testQuest.getMapName(),
                        testQuest.getPosition(),
                        ((ObjectiveTableDataGateway) TableDataGatewayManager.getSingleton()
                                .getTableGateway("Objective")).getObjectivesForQuest(
                                testQuest.getQuestID()),
                        testQuest.getExperienceGained(),
                        testQuest.getObjectiveForFulfillment(),
                        testQuest.getCompletionActionType(),
                        testQuest.getCompletionActionParameter(),
                        testQuest.getStartDate(),
                        testQuest.getEndDate()));
            }
            catch (DatabaseException e)
            {
                /* Should never throw a DatabaseException, but
                 * ObjectiveTableDataGatewayMock#getObjectivesForQuest needs to conform
                 * to the ObjectiveTableDataGateway interface!
                 */
            }
        }
    }

    private void refreshObjectives() throws DatabaseException
    {
        for (QuestRecord q : quests)
        {
            q.setObjectives(((ObjectiveTableDataGateway) TableDataGatewayManager.getSingleton().getTableGateway("Objective"))
                    .getObjectivesForQuest(q.getQuestID()));
        }
    }

}
