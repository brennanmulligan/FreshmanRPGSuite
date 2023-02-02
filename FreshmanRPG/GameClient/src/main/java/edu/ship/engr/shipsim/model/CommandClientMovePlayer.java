package edu.ship.engr.shipsim.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import edu.ship.engr.shipsim.datasource.ContentLoader;
import edu.ship.engr.shipsim.datatypes.Position;

import java.io.File;

/**
 * @author Matt Kujawski
 */
public class CommandClientMovePlayer extends Command
{
    private final int playerID;
    private final Position position;
    private boolean isTeleporting;

    /**
     * @param playerID is the name of the player
     * @param position is the position of the player
     */
    public CommandClientMovePlayer(int playerID, Position position)
    {
        this.playerID = playerID;
        this.position = position;
        isTeleporting = false;
    }

    /**
     * Only dump when teleporting
     */
    @Override
    protected boolean doDump()
    {
        return isTeleporting;
    }

    /**
     * Moves a certain Player into the designated position. If the player moving
     * is this client's player, we check to see if the move is legal and
     * initiate teleportation if appropriate. If the player is another player,
     * we have just been informed that they moved and we should just put them
     * there.
     *
     * @see Command#execute()
     */
    @Override
    void execute()
    {

        ThisClientsPlayer thisClientsPlayer = ClientPlayerManager.getSingleton()
                .getThisClientsPlayer();
        if (thisClientsPlayer.getID() == playerID)
        {
            if (MapManager.getSingleton().getIsTileTeleport(position))
            {
                thisClientsPlayer.teleport(position);

                if (!OptionsManager.getSingleton().isTestMode())
                {
//					File soundFile = new File("../GameClient/assets/teleport.mp3");
                    File soundFile = ContentLoader.getAssetFile("teleport.mp3");
                    FileHandle fileHandle = new FileHandle(soundFile);
                    SoundManager.addSound(Gdx.audio.newSound(fileHandle), 3);

                    SoundManager.stopLoopingSounds();
                }
                isTeleporting = true;
            }
            else if (MapManager.getSingleton().getIsTilePassable(position))
            {
                thisClientsPlayer.move(position, true);
            }
        }
        else
        {
            ClientPlayer playerFromID = ClientPlayerManager.getSingleton()
                    .getPlayerFromID(playerID);
            if (playerFromID != null)
            {
                playerFromID.move(position, false);
            }
        }
    }

    /**
     * @return the ID of the player who moved
     */
    public int getPlayerID()
    {
        return playerID;
    }

    /**
     * @return where the player moved to
     */
    public Position getPosition()
    {
        return position;
    }

    /**
     * @return true if the player is being teleported to another map
     */
    public boolean isTeleporting()
    {
        return isTeleporting;
    }
}
