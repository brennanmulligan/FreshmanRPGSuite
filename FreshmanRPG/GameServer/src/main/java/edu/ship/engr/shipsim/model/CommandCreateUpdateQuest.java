package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.criteria.QuestCompletionActionParameter;
import edu.ship.engr.shipsim.dataENUM.QuestCompletionActionType;
import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datasource.QuestRowDataGateway;
import edu.ship.engr.shipsim.datatypes.Position;
import edu.ship.engr.shipsim.model.reports.ChangePlayerReport;
import edu.ship.engr.shipsim.model.reports.QuestCreatedOrUpdatedReport;

import java.sql.Connection;
import java.util.Date;

public class CommandCreateUpdateQuest extends Command
{
    private final String questTitle;
    private final String questDescription;
    private final String triggerMapName;
    private final Position triggerPosition;
    private final int experiencePointsGained;
    private final int objectivesForFulfillment;
    private final QuestCompletionActionParameter completionActionParameter;
    private final QuestCompletionActionType completionActionType;
    private final Date startDate;
    private final Date endDate;

    private final Boolean isEasterEgg;

    public CommandCreateUpdateQuest(String questTitle, String questDescription,
                                    String triggerMapName,
                                    Position triggerPosition,
                                    int experiencePointsGained,
                                    int objectivesForFulfillment,
                                    QuestCompletionActionType completionActionType,
                                    QuestCompletionActionParameter completionActionParameter,
                                    Date startDate, Date endDate, Boolean isEasterEgg)
    {
        this.questTitle = questTitle;
        this.questDescription = questDescription;
        this.triggerMapName = triggerMapName;
        this.triggerPosition = triggerPosition;
        this.experiencePointsGained = experiencePointsGained;
        this.objectivesForFulfillment = objectivesForFulfillment;
        this.completionActionParameter = completionActionParameter;
        this.completionActionType = completionActionType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isEasterEgg = isEasterEgg;
    }

    // Creates a Quest. If Quest with that name already exists,
    // it updates the existing quest.
    @Override
    void execute()
    {
        try
        {
            QuestRowDataGateway gw = new QuestRowDataGateway(questTitle,
                    questDescription, triggerMapName, triggerPosition,
                    experiencePointsGained, objectivesForFulfillment,
                    completionActionType, completionActionParameter, startDate,
                    endDate, isEasterEgg);
        }
        catch(DatabaseException e)
        {
            if(e.getRootCause().getMessage().split(" ")[0].equals("Duplicate"))
            {
                try
                {
                    QuestRowDataGateway gw = new QuestRowDataGateway(QuestRowDataGateway.findIDFromTitle(questTitle));
                    gw.setQuestDescription(questDescription);
                    gw.setTriggerMapName(triggerMapName);
                    gw.setTriggerPosition(triggerPosition);
                    gw.setExperiencePointsGained(experiencePointsGained);
                    gw.setObjectivesForFulfillment(objectivesForFulfillment);
                    gw.setCompletionActionType(completionActionType);
                    gw.setCompletionActionParameter(completionActionParameter);
                    gw.setStartDate(startDate);
                    gw.setEndDate(endDate);
                    gw.setEasterEgg(isEasterEgg);
                    gw.persist();
                }
                catch (DatabaseException ex)
                {
                    throw new RuntimeException(ex);
                }
                ReportObserverConnector.getSingleton().sendReport(new QuestCreatedOrUpdatedReport(true));
            }
        }
    }
}
