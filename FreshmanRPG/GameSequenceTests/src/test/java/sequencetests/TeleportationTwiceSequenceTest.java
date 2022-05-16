package sequencetests;

import communication.messages.*;
import communication.packers.MapFileMessagePacker;
import datasource.DatabaseException;
import datasource.LevelRecord;
import datasource.PlayerConnectionRowDataGatewayMock;
import datatypes.LevelsForTest;
import datatypes.PlayersForTest;
import datatypes.ServersForTest;
import model.*;

import java.io.IOException;

/**
 * When cd Map1 is entered into the terminal, this sequence is sent
 *
 * @author bl5922
 */
public class TeleportationTwiceSequenceTest extends SequenceTest
{
    private static final LevelRecord LEVEL_TWO_RECORD =
            new LevelRecord(LevelsForTest.TWO.getDescription(),
                    LevelsForTest.TWO.getLevelUpPoints(),
                    LevelsForTest.TWO.getLevelUpMonth(),
                    LevelsForTest.TWO.getLevelUpDayOfMonth());
    @SuppressWarnings("FieldCanBeLocal")
    private final MessageFlow[] teleportAway =
            {new MessageFlow(ServerType.THIS_PLAYER_CLIENT, ServerType.AREA_SERVER,
                    new SendTerminalTextMessage(PlayersForTest.MERLIN.getPlayerID(),
                            "cd " + ServersForTest.FIRST_SERVER.getMapTitle()), true),
                    new MessageFlow(ServerType.AREA_SERVER, ServerType.THIS_PLAYER_CLIENT,
                            new TeleportationContinuationMessage(
                                    ServersForTest.FIRST_SERVER.getMapName(),
                                    ServersForTest.FIRST_SERVER.getHostName(),
                                    ServersForTest.FIRST_SERVER.getPortNumber(),
                                    PlayersForTest.MERLIN.getPlayerID(), 1111), true)};
    @SuppressWarnings("FieldCanBeLocal")
    private final MessageFlow[] disconnect =
            {
                    new MessageFlow(ServerType.AREA_SERVER, ServerType.OTHER_CLIENT,
                            new PlayerLeaveMessage(PlayersForTest.MERLIN.getPlayerID())
                            ,true),
                    new MessageFlow(ServerType.AREA_SERVER, ServerType.THIS_PLAYER_CLIENT,
                            new PlayerLeaveMessage(PlayersForTest.MERLIN.getPlayerID())
                            ,true)
            };
    @SuppressWarnings("FieldCanBeLocal")
    private final MessageFlow[] reconnect =
            {new MessageFlow(ServerType.THIS_PLAYER_CLIENT, ServerType.AREA_SERVER,
                    new ConnectMessage(PlayersForTest.MERLIN.getPlayerID(),
                            PlayersForTest.MERLIN.getPin()), false),
                    new MessageFlow(ServerType.AREA_SERVER, ServerType.THIS_PLAYER_CLIENT,
                            new PlayerJoinedMessage(
                                    PlayersForTest.MERLIN.getPlayerID(),
                                    PlayersForTest.MERLIN.getPlayerName(),
                                    PlayersForTest.MERLIN.getVanityItems(),
                                    // This is the default position for the map we
                                    // originally teleported to.
                                    // Because of the way servers are mocked here, we
                                    // can't use CD twice, so the position coming back
                                    // hasn't been updated
                                    ServerMapManager.getDefaultPositionForMap("Map1"),
                                    PlayersForTest.MERLIN.getCrew(),
                                    PlayersForTest.MERLIN.getMajor(),
                                    PlayersForTest.MERLIN.getSection(),
                                    PlayersForTest.MERLIN.getOwnedItems()),
                            true),
                    new MessageFlow(ServerType.AREA_SERVER, ServerType.OTHER_CLIENT,
                            new PlayerJoinedMessage(
                                    PlayersForTest.MERLIN.getPlayerID(),
                                    PlayersForTest.MERLIN.getPlayerName(),
                                    PlayersForTest.MERLIN.getVanityItems(),
                                    ServerMapManager.getDefaultPositionForMap("Map1"),
                                    PlayersForTest.MERLIN.getCrew(),
                                    PlayersForTest.MERLIN.getMajor(),
                                    PlayersForTest.MERLIN.getSection()), true),
                    new MessageFlow(ServerType.AREA_SERVER, ServerType.THIS_PLAYER_CLIENT,
                            new MapFileMessage(MapFileMessagePacker.DIRECTORY_PREFIX +
                                    ServersForTest.QUAD.getMapName()), true),
                    new MessageFlow(ServerType.AREA_SERVER, ServerType.THIS_PLAYER_CLIENT,
                            new InitializeThisClientsPlayerMessage(
                                    DataGatheringUtilities.getPlayersQuest(
                                            PlayersForTest.MERLIN.getPlayerID()),
                                    DataGatheringUtilities.getPlayersFriends(
                                            PlayersForTest.MERLIN.getPlayerID()),
                                    PlayersForTest.MERLIN.getExperiencePoints(),
                                    PlayersForTest.MERLIN.getDoubloons(),
                                    LEVEL_TWO_RECORD), true),

                    new MessageFlow(ServerType.AREA_SERVER, ServerType.THIS_PLAYER_CLIENT,
                            new TimeToLevelUpDeadlineMessage(
                                    PlayersForTest.MERLIN.getPlayerID(),
                                    LEVEL_TWO_RECORD.getDeadlineDate(),
                                    LevelsForTest.TWO.getDescription()), true),
                    new MessageFlow(ServerType.AREA_SERVER, ServerType.THIS_PLAYER_CLIENT,
                            new DoubloonPrizeMessage(
                                    PlayersForTest.MERLIN.getPlayerID(),
                                    DataGatheringUtilities.getDoubloonPrizeList()),
                            true)};

    /**
     *
     */
    public TeleportationTwiceSequenceTest() throws IOException
    {
        serverList.add(ServerType.THIS_PLAYER_CLIENT);
        serverList.add(ServerType.AREA_SERVER);

        // First, Merlin teleports away
        interactions.add(new Interaction(new CommandSendTerminalText("cd Firstserver"),
                PlayersForTest.MERLIN.getPlayerID(), ServerType.THIS_PLAYER_CLIENT,
                teleportAway));
        // Second, Merlin disconnects
        interactions.add(new Interaction(
                new CommandRemovePlayer(PlayersForTest.MERLIN.getPlayerID()),
                PlayersForTest.MERLIN.getPlayerID(), ServerType.AREA_SERVER, disconnect));
        // Finally, Merlin reconnects
        interactions.add(new Interaction(null, PlayersForTest.MERLIN.getPlayerID(),
                ServerType.AREA_SERVER, reconnect));

    }

    /**
     * @see SequenceTest#resetNecessarySingletons()
     */
    @Override
    public void resetNecessarySingletons()
    {
        try
        {
            PlayerManager.resetSingleton();
            (new PlayerConnectionRowDataGatewayMock(
                    PlayersForTest.MERLIN.getPlayerID())).resetData();
            ClientPlayerManager.resetSingleton();
            MapManager.resetSingleton();
            QuestManager.resetSingleton();
        } catch (DatabaseException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * @see SequenceTest#setUpMachines()
     */
    @Override
    public void setUpMachines() throws DatabaseException
    {
        OptionsManager.getSingleton().setMapName(PlayersForTest.MERLIN.getMapName());
        PlayerManager.resetSingleton();
        PlayerManager.getSingleton()
                .addPlayerSilently(PlayersForTest.MERLIN.getPlayerID());

        ClientModelTestUtilities.setUpThisClientsPlayerForTest(PlayersForTest.MERLIN);
        PlayerManager.getSingleton().addPlayer(PlayersForTest.MERLIN.getPlayerID());
        MapManager.getSingleton().changeToNewFile(PlayersForTest.MERLIN.getMapName());
    }

}

