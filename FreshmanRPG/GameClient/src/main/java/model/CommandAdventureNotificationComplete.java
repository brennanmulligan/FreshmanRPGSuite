package model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Timer;
import model.reports.AdventureNotificationCompleteReport;

import java.io.File;

/**
 * @author Ryan
 *
 */
public class CommandAdventureNotificationComplete extends Command
{
	private int playerID;
	private int questID;
	private int adventureID;

	/**
	 * @param playerID the player ID
	 * @param questID the quest ID
	 * @param adventureID the adventure ID
	 */
	public CommandAdventureNotificationComplete(int playerID, int questID, int adventureID)
	{
		this.playerID = playerID;
		this.questID = questID;
		this.adventureID = adventureID;
	}

	/**
	 * Command's execute method
	 */
	@Override
	boolean execute()
	{
		SoundManager.addSound(Gdx.audio.newSound(new FileHandle(new File("../GameClient/assets/notification_adventure_complete.mp3"))), 2);

		AdventureNotificationCompleteReport report = new AdventureNotificationCompleteReport(playerID, questID, adventureID);
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

	/**
	 * @return adventureID the adventure ID
	 */
	public int getAdventureID()
	{
		return adventureID;
	}
}
