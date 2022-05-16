package sequencetests;

import communication.messages.DisplayTextMessage;
import communication.messages.ExperienceChangedMessage;
import communication.messages.KeyInputMessage;
import communication.messages.ObjectiveStateChangeMessage;
import dataDTO.LevelManagerDTO;
import datasource.DatabaseException;
import datatypes.InteractableItemsForTest;
import datatypes.ObjectiveStateEnum;
import datatypes.ObjectivesForTest;
import datatypes.PlayersForTest;
import model.*;

/**
 * An objective is completed by interacting with an item
 *
 * @author Truc Chau & Elisabeth Ostrow
 */
public class ObjectiveCompletionItemInteractSequenceTest extends SequenceTest
{


    /**
     * Test that Objectives can be completed by interaction
     */
    public ObjectiveCompletionItemInteractSequenceTest()
    {
        serverList.add(ServerType.THIS_PLAYER_CLIENT);
        serverList.add(ServerType.AREA_SERVER);

        buildInteraction();
    }

    /**
     * @see model.SequenceTest#resetNecessarySingletons()
     */
    @Override
    public void resetNecessarySingletons()
    {
        PlayerManager.resetSingleton();
        QuestManager.resetSingleton();
        InteractObjectManager.resetSingleton();

    }

    /**
     * puts the player close to the interactable item
     * so that the quest can be completed
     */
    @Override
    public void setUpMachines()
    {
        MapManager.getSingleton()
                .changeToNewFile(InteractableItemsForTest.CHEST.getMapName());
        ClientModelTestUtilities.setUpThisClientsPlayerForTest(PlayersForTest.MERLIN);

        PlayerManager playerManager = PlayerManager.getSingleton();
        playerManager.addPlayer(PlayersForTest.MERLIN.getPlayerID());

        //set up player position that is near an interactable item
        Player p = playerManager.getPlayerFromID(PlayersForTest.MERLIN.getPlayerID());
        p.setPlayerPosition(InteractableItemsForTest.CHEST.getPosition());

        //get an objective of completion type interact with item, and give its quest to the player
        int questId = ObjectivesForTest.QUEST13_OBJECTIVE_1.getQuestID();

        try
        {
            QuestManager.getSingleton().triggerQuest(p.getPlayerID(), questId);

        } catch (IllegalObjectiveChangeException | IllegalQuestChangeException | DatabaseException e)
        {

            e.printStackTrace();
        }

        InteractObjectManager.getSingleton();
    }

    private void buildInteraction()
    {

        int newExperiencePoints = PlayersForTest.MERLIN.getExperiencePoints() +
                ObjectivesForTest.QUEST13_OBJECTIVE_1.getExperiencePointsGained();
        MessageFlow[] sequence =
                new MessageFlow[]{
                        new MessageFlow(ServerType.THIS_PLAYER_CLIENT,
                                ServerType.AREA_SERVER,
                                new KeyInputMessage("e"), true),
                        new MessageFlow(ServerType.AREA_SERVER,
                                ServerType.THIS_PLAYER_CLIENT,
                                //last two arguments should be false and null (bc this isn't a real life objective)
                                new ObjectiveStateChangeMessage(
                                        PlayersForTest.MERLIN.getPlayerID(),
                                        ObjectivesForTest.QUEST13_OBJECTIVE_1.getQuestID(),
                                        ObjectivesForTest.QUEST13_OBJECTIVE_1.getObjectiveID(),
                                        ObjectivesForTest.QUEST13_OBJECTIVE_1.getObjectiveDescription(),
                                        ObjectiveStateEnum.COMPLETED, false, null), true),
                        new MessageFlow(ServerType.AREA_SERVER,
                                ServerType.THIS_PLAYER_CLIENT,
                                new ExperienceChangedMessage(
                                        PlayersForTest.MERLIN.getPlayerID(),
                                        newExperiencePoints,
                                        LevelManagerDTO.getSingleton()
                                                .getLevelForPoints(newExperiencePoints)),
                                true),
                        new MessageFlow(ServerType.AREA_SERVER,
                                ServerType.THIS_PLAYER_CLIENT,
                                new DisplayTextMessage(
                                        PlayersForTest.MERLIN.getPlayerID(),
                                        InteractableItemsForTest.CHEST.getMessage()),
                                true),


                };

        interaction = new Interaction(sequence,
                new CommandKeyInputSent("e"),
                PlayersForTest.MERLIN.getPlayerID(),
                ServerType.THIS_PLAYER_CLIENT);
    }
}
