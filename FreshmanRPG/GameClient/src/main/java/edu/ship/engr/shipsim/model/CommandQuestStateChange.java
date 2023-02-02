package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.communication.messages.QuestStateChangeMessage;
import edu.ship.engr.shipsim.datatypes.QuestStateEnum;

/**
 * @author Ryan
 */
public class CommandQuestStateChange extends Command
{

    private final int questID;
    private final String questTitle;
    private final String questDescription;
    private final QuestStateEnum questState;

    /**
     * @param message the QuestStateChangeMesage
     */
    public CommandQuestStateChange(QuestStateChangeMessage message)
    {
        this.questID = message.getQuestID();
        this.questTitle = message.getQuestTitle();
        this.questDescription = message.getQuestDescription();
        this.questState = message.getNewState();
    }

    @Override
    void execute()
    {
        ClientPlayerManager.getSingleton().getThisClientsPlayer()
                .sendQuestStateChangeReport(questID, questDescription, questState);
    }

    /**
     * The description of the quest
     *
     * @return questDescription
     */
    public String getQuestDescription()
    {
        return questDescription;
    }

    /**
     * The ID of the quest
     *
     * @return questID
     */
    public int getQuestID()
    {
        return questID;
    }

    /**
     * The State of the quest
     *
     * @return questState
     */
    public QuestStateEnum getQuestState()
    {
        return questState;
    }

    /**
     * @return the quest's title
     */
    public String getQuestTitle()
    {
        return questTitle;
    }

}
