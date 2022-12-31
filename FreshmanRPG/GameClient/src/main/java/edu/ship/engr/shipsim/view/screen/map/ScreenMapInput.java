package edu.ship.engr.shipsim.view.screen.map;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import edu.ship.engr.shipsim.datatypes.Position;
import edu.ship.engr.shipsim.model.ClientModelFacade;
import edu.ship.engr.shipsim.model.ClientPlayer;
import edu.ship.engr.shipsim.model.CommandClientMovePlayer;
import edu.ship.engr.shipsim.model.SoundManager;
import edu.ship.engr.shipsim.view.player.Direction;
import edu.ship.engr.shipsim.view.player.PlayerInputEnum;
import edu.ship.engr.shipsim.view.player.PlayerSprite;

import static edu.ship.engr.shipsim.view.player.Direction.*;

/**
 * Issues movement commands of the client player using keyboard input.
 *
 * @author nhydock
 */
public class ScreenMapInput extends PlayerSprite implements InputProcessor
{
    private PlayerSprite sprite;
    private boolean up, down, left, right;
    private Position position;
    private int playerID;
    private int tps = 0; // "Tiles per step"
    private boolean isShiftDown;
    ClientPlayer player = new ClientPlayer(playerID);


    /**
     * Initializes key held input values of the input processor
     */
    public void initialize()
    {
        up = false;
        down = false;
        left = false;
        right = false;
    }

    /**
     * @see com.badlogic.gdx.InputProcessor
     */
    @Override
    public boolean keyDown(int keycode)
    {
        if (sprite == null)
        {
            return false;
        }

        SoundManager.startWalkingSound();

        Direction direction = PlayerInputEnum.getDirection(keycode);

        if (keycode == Keys.SHIFT_LEFT)
        {
            isShiftDown = true;
            System.out.println("Pressed Shift");
            tps = 2;
        }

        if (direction != null)
        {
            enableDirection(direction);
        }

        return false;

    }

    /**
     * @see com.badlogic.gdx.InputProcessor
     */
    @Override
    public boolean keyUp(int keycode)
    {
        SoundManager.stopWalkingSound();

        Direction direction = PlayerInputEnum.getDirection(keycode);

        if (keycode == Keys.SHIFT_LEFT)
        {
            System.out.println("Released Shift");
            isShiftDown = false;
            tps = 0;
            //sprite.setMoveDuration(moveDuration);
        }

        if (direction != null)
        {
            disableDirection(direction);
        }

        return false;
    }


    /**
     * Enables movement in the specified direction.
     *
     * @param direction The direction to enable movement for.
     */
    public void enableDirection(Direction direction)
    {
        switch (direction)
        {
            case North -> this.up = true;
            case South -> this.down = true;
            case West -> this.left = true;
            case East -> this.right = true;
        }
    }

    /**
     * Disables movement in the specified direction.
     *
     * @param direction The direction to disable movement for.
     */
    public void disableDirection(Direction direction)
    {
        switch (direction)
        {
            case North -> this.up = false;
            case South -> this.down = false;
            case West -> this.left = false;
            case East -> this.right = false;
        }
    }

    /**
     * @param playerID this the player's playerID
     */
    public void setPlayerID(int playerID)
    {
        this.playerID = playerID;
    }

    /**
     * @return the current playerID
     */
    public int getPlayerID()
    {
        return this.playerID;
    }

    /**
     * @param pos the player's position
     */
    public void setPosition(Position pos)
    {
        this.position = pos;

    }

    /**
     * Set the sprite of the player in which we're following the state of
     *
     * @param sprite the player's sprite
     */
    public void setSprite(PlayerSprite sprite)
    {
        this.sprite = sprite;
    }

    /**
     * Handle held down keys for sending input
     */
    public void update()
    {
        if (sprite != null)
        {
            CommandClientMovePlayer cm = null;
            Position to;
            if (up)
            {
                to = Direction.getPositionInDirection(new Position(position.getRow() - tps, position.getColumn()), North);
                cm = new CommandClientMovePlayer(playerID, to);
            }
            else if (down)
            {
                to = Direction.getPositionInDirection(new Position(position.getRow() + tps, position.getColumn()), South);
                cm = new CommandClientMovePlayer(playerID, to);
            }
            else if (left)
            {
                to = Direction.getPositionInDirection(new Position(position.getRow(), position.getColumn() - tps), West);
                cm = new CommandClientMovePlayer(playerID, to);
            }
            else if (right)
            {
                to = Direction.getPositionInDirection(new Position(position.getRow(), position.getColumn() + tps), East);
                cm = new CommandClientMovePlayer(playerID, to);
            }
            if (cm != null)
            {
                ClientModelFacade.getSingleton().queueCommand(cm);
            }
        }
    }

    /**
     * @see com.badlogic.gdx.InputProcessor
     */
    @Override
    public boolean keyTyped(char character)
    {
        return false;
    }

    /**
     * @see com.badlogic.gdx.InputProcessor
     */
    @Override
    public boolean mouseMoved(int screenX, int screenY)
    {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY)
    {
        return false;
    }

    /**
     * @see com.badlogic.gdx.InputProcessor#touchDown(int, int, int, int)
     */
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        return false;
    }

    /**
     * @see com.badlogic.gdx.InputProcessor#touchUp(int, int, int, int)
     */
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button)
    {
        return false;
    }

    /**
     * @see com.badlogic.gdx.InputProcessor#touchDragged(int, int, int)
     */
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer)
    {
        return false;
    }
}