package edu.ship.engr.shipsim.view.screen.map;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import edu.ship.engr.shipsim.model.ClientModelFacade;
import edu.ship.engr.shipsim.model.CommandKeyInputSent;
import edu.ship.engr.shipsim.view.screen.chat.PopUpChatUI;
import edu.ship.engr.shipsim.view.screen.closet.ClosetUI;
import edu.ship.engr.shipsim.view.screen.clothingShop.ClothingShopUI;
import edu.ship.engr.shipsim.view.screen.highscore.HighScoreUI;
import edu.ship.engr.shipsim.view.screen.menu.MenuUI;
import edu.ship.engr.shipsim.view.screen.qas.QuestUI;

/**
 * @author Adam Pine
 */
public class ScreenMapKeyInputSentProcessor implements InputProcessor
{
    private final Stage stage;
    private final MenuUI menuArea;
    private final PopUpChatUI popUpChatUI;

    /**
     * @param stage       - The stage
     * @param menuArea    - The MenuUI object
     * @param popUpChatUI - The ChatUI object
     */
    public ScreenMapKeyInputSentProcessor(Stage stage, MenuUI menuArea, PopUpChatUI popUpChatUI)
    {
        this.stage = stage;
        this.menuArea = menuArea;
        this.popUpChatUI = popUpChatUI;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.badlogic.gdx.InputProcessor#keyDown(int)
     */
    @Override
    public boolean keyDown(int keycode)
    {
        if (stage.getKeyboardFocus() == null)
        {
            String inputStr = Keys.toString(keycode);
            CommandKeyInputSent cmd = new CommandKeyInputSent(inputStr);
            // Create the command, but don't queue it to be sent to the server unless it
            // actually needs to go to the server.
            // Handling of Enter is done in the ScreenMap.
            if (keycode == Keys.E)
            {
                // If the key was E, send it to the server, so that it can handle and check to
                // see if they are in range of an interactable object.
                ClientModelFacade.getSingleton().queueCommand(cmd);
                return true;
            }
            else if (keycode == Keys.Q) // Key to open the quest/objective screen
            {
                menuArea.closeAllScreensExcludingSpecifiedType(QuestUI.class);
                ClientModelFacade.getSingleton().queueCommand(cmd);
                return true;
            }
            else if (keycode == Keys.H) // Key to open the HighScore screen
            {
                menuArea.closeAllScreensExcludingSpecifiedType(HighScoreUI.class);
                ClientModelFacade.getSingleton().queueCommand(cmd);
            }
            else if (keycode == Keys.C)
            {
                if (stage.getKeyboardFocus() == null)
                {
                    popUpChatUI.setVisible(!popUpChatUI.isVisible());
                }
            }
            else if (keycode == Keys.I)
            {
                menuArea.closeAllScreensExcludingSpecifiedType(ClosetUI.class);
                ClientModelFacade.getSingleton().queueCommand(cmd);
                return true;
            }
            else if (keycode == Keys.U)
            {
                menuArea.closeAllScreensExcludingSpecifiedType(ClothingShopUI.class);
                ClientModelFacade.getSingleton().queueCommand(cmd);
                return true;
            }
            else if (keycode == Keys.UP || keycode == Keys.DOWN || keycode == Keys.LEFT || keycode == Keys.RIGHT
                    || keycode == Keys.W || keycode == Keys.A || keycode == Keys.S || keycode == Keys.D)
            {
                // Send movement keys to server in order to trigger sorting room objective
                // criteria.
                // Possible refactor: make the quest completion criteria listen for a
                // PlayerMovement event instead of key press.
                if (stage.getKeyboardFocus() == null)
                {
                    ClientModelFacade.getSingleton().queueCommand(cmd);
                }
                return false;
            }
        }
        return false;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.badlogic.gdx.InputProcessor#keyUp(int)
     */
    @Override
    public boolean keyUp(int keycode)
    {
        return false;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.badlogic.gdx.InputProcessor#keyTyped(char)
     */
    @Override
    public boolean keyTyped(char character)
    {
        return false;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.badlogic.gdx.InputProcessor#touchDown(int, int, int, int)
     */
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        return false;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.badlogic.gdx.InputProcessor#touchUp(int, int, int, int)
     */
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button)
    {
        return false;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.badlogic.gdx.InputProcessor#touchDragged(int, int, int)
     */
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer)
    {
        return false;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.badlogic.gdx.InputProcessor#mouseMoved(int, int)
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

}
