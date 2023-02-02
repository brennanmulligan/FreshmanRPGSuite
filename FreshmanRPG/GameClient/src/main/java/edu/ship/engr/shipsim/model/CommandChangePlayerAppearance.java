package edu.ship.engr.shipsim.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import edu.ship.engr.shipsim.dataDTO.VanityDTO;
import edu.ship.engr.shipsim.datasource.ContentLoader;

import java.util.List;

/**
 * Command to change the player's appearance type.
 */
public class CommandChangePlayerAppearance extends Command
{
    private final int playerID;
    private final List<VanityDTO> vanities;

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
     */
    @Override
    void execute()
    {
//		SoundManager.addSound(Gdx.audio.newSound(new FileHandle(new File("../GameClient/assets/zipper.mp3"))), 2);
        SoundManager.addSound(Gdx.audio.newSound(new FileHandle(ContentLoader.getAssetFile("zipper.mp3"))), 2);

        ClientPlayer player = ClientPlayerManager.getSingleton().getPlayerFromID(playerID);
        player.setVanityReport(vanities);
    }

    /**
     * @return player id
     */
    public int getPlayerID()
    {
        return playerID;
    }
}
