package edu.ship.engr.shipsim.sequencetests;

import edu.ship.engr.shipsim.communication.messages.*;
import edu.ship.engr.shipsim.dataDTO.VanityDTO;
import edu.ship.engr.shipsim.datasource.LevelRecord;
import edu.ship.engr.shipsim.datatypes.*;
import edu.ship.engr.shipsim.model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Defines the protocol for a successful login sequence
 *
 * @author Merlin
 */
public class LoginSuccessSequenceTest extends SequenceTest
{

    private static final LevelRecord LEVEL_TWO_RECORD =
            new LevelRecord(LevelsForTest.TWO.getDescription(),
                    LevelsForTest.TWO.getLevelUpPoints(),
                    LevelsForTest.TWO.getLevelUpMonth(),
                    LevelsForTest.TWO.getLevelUpDayOfMonth());

    @SuppressWarnings("FieldCanBeLocal")
    private final MessageFlow[] sequence =
            {new MessageFlow(ServerType.THIS_PLAYER_CLIENT, ServerType.LOGIN_SERVER,
                    new LoginMessage(PlayersForTest.MERLIN_OFFLINE.getPlayerName(),
                            PlayersForTest.MERLIN_OFFLINE.getPlayerPassword(), false), true),
                    new MessageFlow(ServerType.LOGIN_SERVER,
                            ServerType.THIS_PLAYER_CLIENT, new LoginSuccessfulMessage(
                            PlayersForTest.MERLIN_OFFLINE.getPlayerID(), false,
                            ServersForTest.QUAD.getHostName(),
                            ServersForTest.QUAD.getPortNumber(), 33), true),
                    new MessageFlow(ServerType.THIS_PLAYER_CLIENT, ServerType.AREA_SERVER,
                            new ConnectMessage(
                                    PlayersForTest.MERLIN_OFFLINE.getPlayerID(), false,
                                    PlayersForTest.MERLIN_OFFLINE.getPin()), true),
                    new MessageFlow(ServerType.AREA_SERVER, ServerType.THIS_PLAYER_CLIENT,
                            new PlayerJoinedMessage(
                                    PlayersForTest.MERLIN_OFFLINE.getPlayerID(), false,
                                    PlayersForTest.MERLIN_OFFLINE.getPlayerName(),
                                    addDefaultItemsToVanityItems(PlayersForTest.MERLIN_OFFLINE.getVanityItems()),
                                    PlayersForTest.MERLIN_OFFLINE.getPosition(),
                                    PlayersForTest.MERLIN_OFFLINE.getCrew(),
                                    PlayersForTest.MERLIN_OFFLINE.getMajor(),
                                    PlayersForTest.MERLIN_OFFLINE.getSection(),
                                    PlayersForTest.MERLIN_OFFLINE.getOwnedItems()), true),
                    new MessageFlow(ServerType.AREA_SERVER, ServerType.OTHER_CLIENT,
                            new PlayerJoinedMessage(
                                    PlayersForTest.MERLIN_OFFLINE.getPlayerID(), false,
                                    PlayersForTest.MERLIN_OFFLINE.getPlayerName(),
                                    addDefaultItemsToVanityItems(PlayersForTest.MERLIN_OFFLINE.getVanityItems()),
                                    PlayersForTest.MERLIN_OFFLINE.getPosition(),
                                    PlayersForTest.MERLIN_OFFLINE.getCrew(),
                                    PlayersForTest.MERLIN_OFFLINE.getMajor(),
                                    PlayersForTest.MERLIN_OFFLINE.getSection(),
                                    PlayersForTest.MERLIN_OFFLINE.getOwnedItems()), true),
                    new MessageFlow(ServerType.AREA_SERVER, ServerType.THIS_PLAYER_CLIENT,
                            new MapFileMessage(
                                    PlayersForTest.MERLIN_OFFLINE.getPlayerID(), false, ServersForTest.QUAD.getMapName()), true),
                    new MessageFlow(ServerType.AREA_SERVER, ServerType.THIS_PLAYER_CLIENT,
                            new InitializeThisClientsPlayerMessage(
                                    PlayersForTest.MERLIN_OFFLINE.getPlayerID(), false, DataGatheringUtilities.getPlayersQuest(
                                            PlayersForTest.MERLIN_OFFLINE.getPlayerID()),
                                    DataGatheringUtilities.getPlayersFriends(
                                            PlayersForTest.MERLIN_OFFLINE.getPlayerID()),
                                    PlayersForTest.MERLIN_OFFLINE.getExperiencePoints(),
                                    PlayersForTest.MERLIN_OFFLINE.getDoubloons(),
                                    LEVEL_TWO_RECORD), true),

                    new MessageFlow(ServerType.AREA_SERVER, ServerType.THIS_PLAYER_CLIENT,
                            new TimeToLevelUpDeadlineMessage(
                                    PlayersForTest.MERLIN_OFFLINE.getPlayerID(), false,
                                    LEVEL_TWO_RECORD.getDeadlineDate(),
                                    LevelsForTest.TWO.getDescription()), true),
                    new MessageFlow(ServerType.AREA_SERVER, ServerType.THIS_PLAYER_CLIENT,
                            new DoubloonPrizeMessage(
                                    PlayersForTest.MERLIN_OFFLINE.getPlayerID(), false,
                                    DataGatheringUtilities.getDoubloonPrizeList()),
                            true)};

    private List<VanityDTO> addDefaultItemsToVanityItems(List<VanityDTO> vanityItems)
    {
        ArrayList<VanityDTO> combined = new ArrayList<>(vanityItems);
        for (DefaultItemsForTest item : DefaultItemsForTest.values())
        {
            if (item.getDefaultWearing() == 1)
            {
                int id = item.getDefaultID();
                VanityItemsForTest vanity = VanityItemsForTest.values()[id - 1]; // TODO: Make sense of this
                VanityDTO x = new VanityDTO(vanity.getId(), vanity.getName(),
                        vanity.getDescription(), vanity.getTextureName(),
                        vanity.getVanityType(),
                        vanity.getPrice());
                combined.add(x);
            }
        }
        return combined;
    }


    /**
     * @throws IOException shouldn't
     */
    public LoginSuccessSequenceTest() throws IOException
    {
        serverList.add(ServerType.THIS_PLAYER_CLIENT);
        serverList.add(ServerType.OTHER_CLIENT);
        serverList.add(ServerType.LOGIN_SERVER);
        serverList.add(ServerType.AREA_SERVER);

        interactions.add(new Interaction(
                new CommandLogin(PlayersForTest.MERLIN_OFFLINE.getPlayerName(),
                        PlayersForTest.MERLIN_OFFLINE.getPlayerPassword()),
                PlayersForTest.MERLIN_OFFLINE.getPlayerID(),
                ServerType.THIS_PLAYER_CLIENT, 0, sequence));
    }

    /**
     * @see edu.ship.engr.shipsim.model.SequenceTest#resetNecessarySingletons()
     */
    @Override
    public void resetNecessarySingletons()
    {
        PlayerManager.resetSingleton();
    }

    /**
     * @see edu.ship.engr.shipsim.model.SequenceTest#setUpMachines()
     */
    @Override
    public void setUpMachines()
    {
        OptionsManager.getSingleton()
                .setMapFileTitle(PlayersForTest.MERLIN_OFFLINE.getMapName());
        PlayerManager.resetSingleton();
        QuestManager.resetSingleton();
    }


}
