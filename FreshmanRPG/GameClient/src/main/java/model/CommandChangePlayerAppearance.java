package model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Timer;

import java.io.File;

/**
 * Command to change the player's appearance type.
 */
public class CommandChangePlayerAppearance extends Command
{
	private int playerID;
	private String appearanceType;

	/**
	 * Construct and initialize a CommandChangePlayerAppearance.
	 *
	 * @param playerId - the player ID
	 * @param appearanceType - the appearance type we want to change to
	 */
	public CommandChangePlayerAppearance(int playerId, String appearanceType)
	{
		this.playerID = playerId;
		this.appearanceType = appearanceType;
	}

	/**
	 * Execute the command.
	 *
	 * @return true if successful
	 */
	@Override
	boolean execute()
	{
		SoundManager.addSound(Gdx.audio.newSound(new FileHandle(new File("../GameClient/assets/zipper.mp3"))), 2);

		ClientPlayerManager.getSingleton().getPlayerFromID(playerID).setAppearanceTypeReport(appearanceType);
		return true;
	}

	/**
	 * @return player id
	 */
	public int getPlayerID()
	{
		return playerID;
	}

	/**
	 * @return player appearance
	 */
	public String getAppearanceType()
	{
		return appearanceType;
	}

}
