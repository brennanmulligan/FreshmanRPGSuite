package edu.ship.engr.shipsim.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import edu.ship.engr.shipsim.datasource.ContentLoader;
import edu.ship.engr.shipsim.model.reports.QuestNotificationCompleteReport;

/**
 * @author Merlin
 */
public class CommandQuestNotificationComplete extends Command
{
    private final int playerID;
    private final int questID;

    /**
     * @param playerID the player ID
     * @param questID  the quest ID
     */
    public CommandQuestNotificationComplete(int playerID, int questID)
    {
        this.playerID = playerID;
        this.questID = questID;
    }

    /**
     * Command's execute method
     */
    @Override
    void execute()
    {
//		SoundManager.addSound(Gdx.audio.newSound(new FileHandle(new File("../GameClient/assets/notification_quest_complete.mp3"))), 8);
        SoundManager.addSound(Gdx.audio.newSound(new FileHandle(ContentLoader.getAssetFile("notification_quest_complete.mp3"))), 8);

        QuestNotificationCompleteReport report = new QuestNotificationCompleteReport(
                playerID, questID);
        ReportObserverConnector.getSingleton().sendReport(report);
    }

    /**
     * @return playerID the players ID
     */
    public int getPlayerID()
    {
        return playerID;
    }

    /**
     * @return questID the quest ID
     */
    public int getQuestID()
    {
        return questID;
    }
}
