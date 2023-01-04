package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.dataDTO.ClientPlayerQuestStateDTO;
import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.model.reports.PlayerQuestReport;

import java.io.IOException;
import java.util.List;

/**
 * @author Derek
 */
public class CommandFetchPlayerObjectives extends Command
{
    private final Player player;

    public CommandFetchPlayerObjectives(Player player)
    {
        this.player = player;
    }

    @Override
    boolean execute() throws IOException
    {
        try
        {
            List<ClientPlayerQuestStateDTO> quests = QuestManager.getSingleton().getQuests(player);

            PlayerQuestReport report = new PlayerQuestReport(player, quests);

            ReportObserverConnector.getSingleton().sendReport(report);

            return true;
        }
        catch (DatabaseException e)
        {
            throw new RuntimeException(e);
        }
    }
}
