package sequencetests;

import communication.messages.*;
import communication.packers.MapFileMessagePacker;
import dataDTO.*;
import datasource.*;
import datatypes.LevelsForTest;
import datatypes.PlayersForTest;
import datatypes.QuestStateEnum;
import datatypes.ServersForTest;
import model.*;

import java.io.IOException;
import java.util.ArrayList;

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
                            PlayersForTest.MERLIN_OFFLINE.getPlayerPassword()), true),
                    new MessageFlow(ServerType.LOGIN_SERVER,
                            ServerType.THIS_PLAYER_CLIENT, new LoginSuccessfulMessage(
                            PlayersForTest.MERLIN_OFFLINE.getPlayerID(),
                            ServersForTest.QUAD.getHostName(),
                            ServersForTest.QUAD.getPortNumber(), 33), true),
                    new MessageFlow(ServerType.THIS_PLAYER_CLIENT, ServerType.AREA_SERVER,
                            new ConnectMessage(
                                    PlayersForTest.MERLIN_OFFLINE.getPlayerID(),
                                    PlayersForTest.MERLIN_OFFLINE.getPin()), true),
                    new MessageFlow(ServerType.AREA_SERVER, ServerType.THIS_PLAYER_CLIENT,
                            new PlayerJoinedMessage(
                                    PlayersForTest.MERLIN_OFFLINE.getPlayerID(),
                                    PlayersForTest.MERLIN_OFFLINE.getPlayerName(),
                                    PlayersForTest.MERLIN_OFFLINE.getVanityItems(),
                                    PlayersForTest.MERLIN_OFFLINE.getPosition(),
                                    PlayersForTest.MERLIN_OFFLINE.getCrew(),
                                    PlayersForTest.MERLIN_OFFLINE.getMajor(),
                                    PlayersForTest.MERLIN_OFFLINE.getSection(),
                                    PlayersForTest.MERLIN_OFFLINE.getOwnedItems()), true),
                    new MessageFlow(ServerType.AREA_SERVER, ServerType.OTHER_CLIENT,
                            new PlayerJoinedMessage(
                                    PlayersForTest.MERLIN_OFFLINE.getPlayerID(),
                                    PlayersForTest.MERLIN_OFFLINE.getPlayerName(),
                                    PlayersForTest.MERLIN_OFFLINE.getVanityItems(),
                                    PlayersForTest.MERLIN_OFFLINE.getPosition(),
                                    PlayersForTest.MERLIN_OFFLINE.getCrew(),
                                    PlayersForTest.MERLIN_OFFLINE.getMajor(),
                                    PlayersForTest.MERLIN_OFFLINE.getSection()), true),
                    new MessageFlow(ServerType.AREA_SERVER, ServerType.THIS_PLAYER_CLIENT,
                            new MapFileMessage(MapFileMessagePacker.DIRECTORY_PREFIX +
                                    ServersForTest.QUAD.getMapName()), true),
                    new MessageFlow(ServerType.AREA_SERVER, ServerType.THIS_PLAYER_CLIENT,
                            new InitializeThisClientsPlayerMessage(DataGatheringUtilities.getPlayersQuest(
                                    PlayersForTest.MERLIN_OFFLINE.getPlayerID()),
                                    DataGatheringUtilities.getPlayersFriends(
                                            PlayersForTest.MERLIN_OFFLINE.getPlayerID()),
                                    PlayersForTest.MERLIN_OFFLINE.getExperiencePoints(),
                                    PlayersForTest.MERLIN_OFFLINE.getDoubloons(),
                                    LEVEL_TWO_RECORD), true),

                    new MessageFlow(ServerType.AREA_SERVER, ServerType.THIS_PLAYER_CLIENT,
                            new TimeToLevelUpDeadlineMessage(
                                    PlayersForTest.MERLIN_OFFLINE.getPlayerID(),
                                    LEVEL_TWO_RECORD.getDeadlineDate(),
                                    LevelsForTest.TWO.getDescription()), true),
                    new MessageFlow(ServerType.AREA_SERVER, ServerType.THIS_PLAYER_CLIENT,
                            new DoubloonPrizeMessage(
                                    PlayersForTest.MERLIN_OFFLINE.getPlayerID(),
                                    DataGatheringUtilities.getDoubloonPrizeList()),
                            true)};


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
                ServerType.THIS_PLAYER_CLIENT, sequence));
    }

    /**
     * @see model.SequenceTest#resetNecessarySingletons()
     */
    @Override
    public void resetNecessarySingletons()
    {
        try
        {
            PlayerManager.resetSingleton();
            (new PlayerConnectionRowDataGatewayMock(
                    PlayersForTest.MERLIN_OFFLINE.getPlayerID())).resetData();
        } catch (DatabaseException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * @see model.SequenceTest#setUpMachines()
     */
    @Override
    public void setUpMachines()
    {
        OptionsManager.getSingleton()
                .setMapName(PlayersForTest.MERLIN_OFFLINE.getMapName());
        PlayerManager.resetSingleton();
        QuestManager.resetSingleton();
    }



}
