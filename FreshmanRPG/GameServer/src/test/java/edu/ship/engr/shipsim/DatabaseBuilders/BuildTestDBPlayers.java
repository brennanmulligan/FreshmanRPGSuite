package edu.ship.engr.shipsim.DatabaseBuilders;

import edu.ship.engr.shipsim.datasource.*;
import edu.ship.engr.shipsim.datatypes.NPCsForTest;
import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.model.OptionsManager;

import java.sql.SQLException;

/**
 * Builds the Player portion of the database
 *
 * @author Merlin
 */
public class BuildTestDBPlayers
{
    /**
     * @param args unused
     * @throws DatabaseException shouldn't
     * @throws SQLException      shouldn't
     */
    public static void main(String[] args) throws DatabaseException, SQLException
    {
        OptionsManager.getSingleton().setUsingTestDB(true);
        createPlayerTable();
        createNpcTable();
        createPlayerLoginTable();
        createPlayerConnectionTable();
    }

    private static void createPlayerTable() throws DatabaseException
    {
        System.out.println("Building the Player Table");
        PlayerRowDataGateway.createTable();

        ProgressBar bar = new ProgressBar(PlayersForTest.values().length);
        for (PlayersForTest p : PlayersForTest.values())
        {
            new PlayerRowDataGateway(p.getAppearanceType(), p.getDoubloons(),
                    p.getExperiencePoints(), p.getCrew(), p.getMajor(), p.getSection(), p.getBuffPool(), p.getOnline());

            bar.update();
        }

    }

    private static void createNpcTable() throws DatabaseException
    {
        System.out.println("Building the NPC Table");
        NPCRowDataGateway.createTable();

        ProgressBar bar = new ProgressBar(NPCsForTest.values().length);
        for (NPCsForTest p : NPCsForTest.values())
        {
            new NPCRowDataGateway(p.getPlayerID(), p.getBehaviorClass(), p.getFilePath());

            bar.update();
        }
    }

    private static void createPlayerLoginTable() throws DatabaseException
    {
        System.out.println("Building the PlayerLogin Table");
        PlayerLoginRowDataGateway.createPlayerLoginTable();

        ProgressBar bar = new ProgressBar(PlayersForTest.values().length);
        for (PlayersForTest p : PlayersForTest.values())
        {
            new PlayerLoginRowDataGateway(p.getPlayerID(), p.getPlayerName(), p.getPlayerPassword());

            bar.update();
        }

    }

    private static void createPlayerConnectionTable() throws DatabaseException
    {
        System.out.println("Building the PlayerConnection Table");
        PlayerConnectionRowDataGateway.createPlayerConnectionTable();

        ProgressBar bar = new ProgressBar(PlayersForTest.values().length);
        for (PlayersForTest p : PlayersForTest.values())
        {
            new PlayerConnectionRowDataGateway(p.getPlayerID(), p.getPin(), p.getMapName(), p.getPosition());

            bar.update();
        }

    }
}
