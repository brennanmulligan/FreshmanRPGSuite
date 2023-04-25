package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.model.reports.UpsertQuestResponseReport;

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

        try
        {
            boolean found = false;
            QuestMapper mapper;
            try
            {
                mapper = new QuestMapper(questRecord.getQuestID());
                found = true;
            }
            catch (DatabaseException ignored)
            {
                mapper = new QuestMapper(questRecord.getTitle(),
                        questRecord.getDescription(),
                        questRecord.getTriggerMapName(),
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

            if (found)
            {
                // set the new values and then persist
                mapper.questRecord.setTitle(questRecord.getTitle());
                mapper.questRecord.setDescription(questRecord.getDescription());
                mapper.questRecord.setTriggerMapName(questRecord.getTriggerMapName());
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
        }
        catch (DatabaseException e)
        {
            // This should not happen because we create a new quest if it doesn't exist
            ReportObserverConnector.getSingleton().sendReport(
                    new UpsertQuestResponseReport(false,
                            "Database Failed to Upsert Quest."));
        }


        ReportObserverConnector.getSingleton()
                .sendReport(new UpsertQuestResponseReport(true));

    }
}
