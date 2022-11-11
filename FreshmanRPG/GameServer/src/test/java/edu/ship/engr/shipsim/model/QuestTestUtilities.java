package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.dataDTO.ClientPlayerObjectiveStateDTO;
import edu.ship.engr.shipsim.dataDTO.ClientPlayerQuestStateDTO;
import edu.ship.engr.shipsim.datatypes.ObjectiveStateEnum;
import edu.ship.engr.shipsim.datatypes.QuestStateEnum;
import org.apache.commons.compress.utils.Lists;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * @author Derek
 */
public final class QuestTestUtilities
{
    private static final int NUM_QUESTS_FOR_TEST = 3;
    private static final int NUM_OBJECTIVES_FOR_TEST = 5;

    public static List<ClientPlayerQuestStateDTO> getQuestsForTest()
    {
        List<ClientPlayerQuestStateDTO> quests = Lists.newArrayList();

        for (int i = 0; i < NUM_QUESTS_FOR_TEST; i++)
        {
            int questID = i + 1;
            String questName = "Quest " + questID;
            String questDescription = "This is the description for " + questName;
            Date questExpireDate = new GregorianCalendar(9999, Calendar.JANUARY, 1).getTime();
            QuestStateEnum questState = QuestStateEnum.AVAILABLE;

            List<ClientPlayerObjectiveStateDTO> objectives = getObjectivesForTest(questID, questState);
            int questExperience = objectives.stream().map(ClientPlayerObjectiveStateDTO::getObjectiveXP).reduce(0, Integer::sum);

            ClientPlayerQuestStateDTO clientPlayerQuestStateDTO = new ClientPlayerQuestStateDTO(
                    questID, questName,
                    questDescription, questState,
                    questExperience, objectives.size(),
                    true, questExpireDate
            );

            clientPlayerQuestStateDTO.setObjectives(objectives);

            quests.add(clientPlayerQuestStateDTO);
        }

        return quests;
    }

    public static List<ClientPlayerObjectiveStateDTO> getObjectivesForTest(int questID, QuestStateEnum questState)
    {
        List<ClientPlayerObjectiveStateDTO> objectives = Lists.newArrayList();

        for (int i = 0; i < NUM_OBJECTIVES_FOR_TEST; i++)
        {
            int objectiveID = i + 1;
            String objectiveName = "Objective " + objectiveID;
            String objectiveDescription = String.format("This is the description for %s of Quest %d", objectiveName, questID);
            int objectiveExperience = objectiveID * 100;

            ClientPlayerObjectiveStateDTO objective = new ClientPlayerObjectiveStateDTO(
                    objectiveID, objectiveDescription,
                    objectiveExperience, ObjectiveStateEnum.TRIGGERED,
                    true, false,
                    "", questState
            );

            objectives.add(objective);
        }

        return objectives;
    }
}
