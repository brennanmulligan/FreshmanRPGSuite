package edu.ship.engr.shipsim.view.screen.friends;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import edu.ship.engr.shipsim.view.screen.OverlayingScreenTable;
import edu.ship.engr.shipsim.view.screen.SkinPicker;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The right side of the friends tab, shows the message history
 *
 * @author Zack Semanco, Kevin Marek
 */
public class FriendsChatTable extends OverlayingScreenTable
{
    private static final float HEIGHT = 390f;
    private static final float WIDTH = 380f;

    HashMap<Integer, ArrayList<FriendMessage>> messages;
    ArrayList<FriendMessage> activeMessages;

    private Skin skin;
    private Table itemTable;

    /**
     * Constructor
     *
     * @param scrollable whether the window can be scrolled
     */
    public FriendsChatTable(boolean scrollable)
    {
        super(scrollable);
        if (messages == null)
        {
            messages = new HashMap<>();
        }
        setupUI();
    }

    /**
     * Adds the new message to existing history or creates a new entry for that
     * friend
     *
     * @param friendID  Id of the sender
     * @param message   content of their message
     * @param direction whether the message is incoming or outgoing
     */
    public void addMessage(int friendID, String message, MessageType direction)
    {
        FriendMessage friendMessage = new FriendMessage(message, direction, false);

        if (messages.containsKey(friendID))
        {
            messages.get(friendID).add(friendMessage);
        }
        else
        {
            ArrayList<FriendMessage> newMessage = new ArrayList<>();
            newMessage.add(friendMessage);
            messages.put(friendID, newMessage);
        }
    }

    /**
     * Clears the current messages in the table and replaces them with the desired
     * friend's messages (if they exist)
     *
     * @param friendID id of the desired friend
     */
    public void setActiveFriend(int friendID)
    {
        if (messages.containsKey(friendID))
        {
            itemTable.clear();
            activeMessages = messages.get(friendID);

            for (FriendMessage message : activeMessages)
            {
                message.setRead(true);
                Label wrapAttempt;

                if (message.getDirection() == MessageType.INCOMING)
                {
                    wrapAttempt = new Label(message.getMessage(), skin);
                    wrapAttempt.setWrap(true);
                    wrapAttempt.setAlignment(Align.left);
                    itemTable.add(wrapAttempt).width(WIDTH - 120).pad(10, 20, 10, 100);
                    itemTable.row();
                }
                else if (message.getDirection() == MessageType.OUTGOING)
                {
                    wrapAttempt = new Label(message.getMessage(), skin);
                    wrapAttempt.setWrap(true);
                    wrapAttempt.setAlignment(Align.right);
                    itemTable.add(wrapAttempt).width(WIDTH - 120).pad(10, 100, 10, 20);
                    itemTable.row();
                }

            }
        }
        else
        {
            // If the friend is a new friend with no messages, just clear the current
            // messages but don't add any messages
            itemTable.clear();
        }

    }

    /**
     * Create the table to show the chat history
     */
    public void setupUI()
    {
        skin = SkinPicker.getSkinPicker().getDefaultSkin();
        Table grid = new Table();
        itemTable = new Table(skin);
        ScrollPane listPane = new ScrollPane(itemTable, skin);
        listPane.setScrollingDisabled(true, false);
        listPane.setFadeScrollBars(false);

        grid.add(listPane).expand().fillX().height(HEIGHT).width(WIDTH);

        grid.bottom();
        container.add(grid);
    }

    /**
     * Gets the number of unread messages in the conversation with the friend who's id is passed in
     *
     * @param friendID id of friend
     * @return num unread messages
     */
    public int getNumUnread(int friendID)
    {
        int numUnread = 0;
        if (messages.containsKey(friendID))
        {
            for (FriendMessage message : messages.get(friendID))
            {
                if (!message.getRead())
                {
                    numUnread++;
                }
            }
        }
        else
        {
            return 0;
        }
        return numUnread;
    }

    /**
     * Specifies incoming or outgoing messages
     */
    protected enum MessageType
    {
        INCOMING, OUTGOING
    }

    /**
     * Represents a message's content as well as whether it is an incoming or an
     * outgoing message
     */
    private static class FriendMessage
    {
        MessageType direction;
        String message;
        boolean read;

        public FriendMessage(String message, MessageType direction, boolean read)
        {
            this.message = message;
            this.direction = direction;
            this.read = read;
        }

        public void setRead(boolean b)
        {
            read = b;
        }

        public boolean getRead()
        {
            return read;
        }

        public MessageType getDirection()
        {
            return direction;
        }

        public String getMessage()
        {
            return message;
        }
    }
}
