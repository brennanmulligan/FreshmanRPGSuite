package ui.fx.framework;

import ui.fx.IconFont;
import ui.fx.contentviews.MessageBoardContentView;

public class MessageBoardMenuButton extends MainMenuButton
{
    private static MessageBoardMenuButton instance;

    private MessageBoardMenuButton()
    {
        super("MessageBoardMenuButton", "Message Board", IconFont.BOARD, MessageBoardContentView.getInstance());
    }

    /**
     * @return Singleton reference to this custom menu button.
     */
    public static MainMenuButton getInstance()
    {
        if (instance == null)
        {
            instance = new MessageBoardMenuButton();
        }
        return instance;
    }
}
