package edu.ship.engr.shipsim.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import edu.ship.engr.shipsim.datasource.ContentLoader;
import edu.ship.engr.shipsim.model.reports.ObjectiveNotificationCompleteReport;

/**
 * @author Ryan
 */
public class CommandObjectiveNotificationComplete extends Command
{
    private final int playerID;
    private final int questID;
    private final int objectiveID;

    /**
     * @param playerID    the player ID
     * @param questID     the quest ID
     * @param objectiveID the objective ID
     */
    public CommandObjectiveNotificationComplete(int playerID, int questID, int objectiveID)
    {
        this.playerID = playerID;
        this.questID = questID;
        this.objectiveID = objectiveID;
    }

    /**
     * Command's execute method
     */
    @Override
    void execute()
    {
//		SoundManager.addSound(Gdx.audio.newSound(new FileHandle(new File("../GameClient/assets/notification_objective_complete.mp3"))), 2);
        SoundManager.addSound(Gdx.audio.newSound(new FileHandle(ContentLoader.getAssetFile("notification_objective_complete.mp3"))), 2);

        ObjectiveNotificationCompleteReport report = new ObjectiveNotificationCompleteReport(playerID, questID, objectiveID);
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

    /**
     * @return objectiveID the objective ID
     */
    public int getObjectiveID()
    {
        return objectiveID;
    }
}
