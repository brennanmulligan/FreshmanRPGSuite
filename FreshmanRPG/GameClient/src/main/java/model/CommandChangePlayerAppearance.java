package model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Timer;
import dataDTO.VanityDTO;

import java.io.File;
import java.util.List;

/**
 * Command to change the player's appearance type.
 */
public class CommandChangePlayerAppearance extends Command
{
	private int playerID;
	private List<VanityDTO> vanities;

	/**
	 * Construct and initialize a CommandChangePlayerAppearance.
	 *
	 * @param playerId - the player ID
	 * @param vanities the list of all vanity objects the player is wearing
	 */
	public CommandChangePlayerAppearance(int playerId, List<VanityDTO> vanities)
	{
		this.playerID = playerId;
		this.vanities = vanities;
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

		ClientPlayer player = ClientPlayerManager.getSingleton().getPlayerFromID(playerID);
		player.setVanityReport(vanities);

		return true;
	}

	/**
	 * @return player id
	 */
	public int getPlayerID()
	{
		return playerID;
	}
}
