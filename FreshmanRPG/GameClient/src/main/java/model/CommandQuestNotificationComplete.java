package model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Timer;
import model.reports.QuestNotificationCompleteReport;

import java.io.File;

/**
 * @author Merlin
 *
 */
public class CommandQuestNotificationComplete extends Command
{
	private int playerID;
	private int questID;

	/**
	 * @param playerID
	 *            the player ID
	 * @param questID
	 *            the quest ID
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
	boolean execute()
	{
		SoundManager.addSound(Gdx.audio.newSound(new FileHandle(new File("../GameClient/assets/notification_quest_complete.mp3"))), 8);

		QuestNotificationCompleteReport report = new QuestNotificationCompleteReport(
				playerID, questID);
		QualifiedObservableConnector.getSingleton().sendReport(report);
		return true;
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
