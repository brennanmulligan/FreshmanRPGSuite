package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.model.reports.UpsertQuestResponseReport;

import java.util.ArrayList;

public class CommandUpsertQuest extends Command
{
    private final QuestRecord questRecord;

    public CommandUpsertQuest(QuestRecord questRecord)
    {
        this.questRecord = questRecord;
    }

    @Override
    void execute()
    {

        // Doesn't exist, clarify with team about less than 0 logic
        if (questRecord.getQuestID() < 0)
        {
            try
            {
                new QuestMapper(questRecord.getTitle(),
                        questRecord.getDescription(),
                        questRecord.getMapName(),
                        questRecord.getPos(),
                        questRecord.getObjectives(),
                        questRecord.getObjectivesForFulfillment(),
                        questRecord.getExperiencePointsGained(),
                        questRecord.getCompletionActionType(),
                        questRecord.getCompletionActionParameter(),
                        questRecord.getStartDate(),
                        questRecord.getEndDate(),
                        questRecord.isEasterEgg());
            }
            catch (DatabaseException e)
            {
                ReportObserverConnector.getSingleton().sendReport(
                        new UpsertQuestResponseReport(false,
                                "Could not create quest. Please try again."));
                return;
            }
        }
        else
        {
            try
            {
                QuestMapper mapper = new QuestMapper(questRecord.getQuestID());

                // set the new values and then persist
                mapper.questRecord.setTitle(questRecord.getTitle());
                mapper.questRecord.setDescription(questRecord.getDescription());
                mapper.questRecord.setMapName(questRecord.getMapName());
                mapper.questRecord.setPos(questRecord.getPos());
                mapper.questRecord.setObjectives(questRecord.getObjectives());
                mapper.questRecord.setObjectivesForFulfillment(
                        questRecord.getObjectivesForFulfillment());
                mapper.questRecord.setExperiencePointsGained(
                        questRecord.getExperiencePointsGained());
                mapper.questRecord.setCompletionActionType(
                        questRecord.getCompletionActionType());
                mapper.questRecord.setCompletionActionParameter(
                        questRecord.getCompletionActionParameter());
                mapper.questRecord.setStartDate(questRecord.getStartDate());
                mapper.questRecord.setEndDate(questRecord.getEndDate());
                mapper.questRecord.setEasterEgg(questRecord.isEasterEgg());

                mapper.persist();

            }
            catch (DatabaseException e)
            {
                ReportObserverConnector.getSingleton().sendReport(
                        new UpsertQuestResponseReport(false,
                                "Could find the specified quest, or failed to" +
                                        " update it. Please try again."));
                return;
            }
        }


        ReportObserverConnector.getSingleton()
                .sendReport(new UpsertQuestResponseReport(true));

    }

}
