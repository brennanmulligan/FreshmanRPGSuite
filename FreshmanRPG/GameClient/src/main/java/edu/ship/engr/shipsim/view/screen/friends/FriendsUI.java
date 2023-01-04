package edu.ship.engr.shipsim.view.screen.friends;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import edu.ship.engr.shipsim.dataDTO.FriendDTO;
import edu.ship.engr.shipsim.datatypes.ChatTextDetails;
import edu.ship.engr.shipsim.datatypes.ChatType;
import edu.ship.engr.shipsim.model.*;
import edu.ship.engr.shipsim.model.reports.ChatReceivedReport;
import edu.ship.engr.shipsim.model.reports.CurrentFriendListReport;
import edu.ship.engr.shipsim.model.reports.FriendChangedStateReport;
import edu.ship.engr.shipsim.model.reports.UpdateFriendsListReport;
import edu.ship.engr.shipsim.view.screen.OverlayingScreen;
import edu.ship.engr.shipsim.view.screen.SkinPicker;
import edu.ship.engr.shipsim.view.screen.friends.FriendsChatTable.MessageType;

import java.util.ArrayList;

/**
 * Builds the FriendsUI when the "Friends" button is pressed
 *
 * @author Zack Semanco, Kevin Marek
 */
public class FriendsUI extends OverlayingScreen implements ReportObserver
{

    private final float WIDTH = 600f;
    private final float HEIGHT = 475f;

    private FriendsTable friendsTable;
    private FriendsChatTable friendsChatTable;
    // private FriendsChatInputTable friendsChatInputTable;

    private ArrayList<FriendDTO> friendsList;
    private Table subContainer;

    TextField inputTextField;
    TextFieldStyle tfs;
    Skin skin;

    int selectedFriend;

    /**
     * Constructor
     */
    public FriendsUI()
    {
        super();
        setUpListening();
        friendsList = new ArrayList<>();
        friendsTable = new FriendsTable(friendsList, true);
        friendsChatTable = new FriendsChatTable(true);
        // friendsChatInputTable = new FriendsChatInputTable(false);
        friendsTable.setChatTable(this);
        setupUI();
    }

    /**
     * Method to build the Friends UI
     */
    public void setupUI()
    {
        subContainer = new Table();
        subContainer.add(friendsTable).width(.30f * WIDTH).height(.90f * HEIGHT);
        subContainer.add(friendsChatTable).width(.70f * WIDTH).height(.90f * HEIGHT);
        container.add(subContainer);
        container.row();

        subContainer = new Table();

        skin = SkinPicker.getSkinPicker().getDefaultSkin();
        tfs = skin.get(TextField.TextFieldStyle.class);
        inputTextField = new TextField("", skin);
        tfs.fontColor = Color.BLACK;
        inputTextField.setStyle(tfs);

        // Listener for user submitting text with enter key
        inputTextField.addListener(new InputListener()
        {
            @Override
            public boolean keyDown(InputEvent event, int keycode)
            {
                if (keycode == Keys.ENTER)
                {
                    sendMessage(inputTextField.getText(), selectedFriend);
                    inputTextField.setText("");
                    return true;
                }
                return super.keyDown(event, keycode);
            }
        });

        subContainer.add(inputTextField).width(WIDTH);
        container.add(subContainer);
    }

    /**
     * Use the CommandChatMessageSent report to send a private chat to the desired
     * friend
     *
     * @param message  - text of message
     * @param friendID - id of the desired recipient
     */
    public void sendMessage(String message, int friendID)
    {
        ChatTextDetails ctd = new ChatTextDetails(message, ChatType.Private);
        CommandChatMessageSent cmd = new CommandChatMessageSent(ctd, friendID);
        ClientModelFacade.getSingleton().queueCommand(cmd);
    }

    /**
     * Sets up the ReportObserver for ChatReceivedReport
     */
    public void setUpListening()
    {
        ReportObserverConnector cm = ReportObserverConnector.getSingleton();
        cm.registerObserver(this, FriendChangedStateReport.class);
        cm.registerObserver(this, ChatReceivedReport.class);
        cm.registerObserver(this, CurrentFriendListReport.class);
        cm.registerObserver(this, UpdateFriendsListReport.class);
    }

    /**
     * Receive report that a chat has been received. If it is of private type and we
     * are the recipient, add the message to our list
     */
    @Override
    public void receiveReport(Report report)
    {
        if (report.getClass() == ChatReceivedReport.class)
        {
            ChatReceivedReport rep = (ChatReceivedReport) report;

            // IF THE MESSAGE IS INCOMING
            if (rep.getType() == ChatType.Private
                    && rep.getReceiverID() == ClientPlayerManager.getSingleton().getThisClientsPlayer().getID())
            {
                friendsChatTable.addMessage(rep.getSenderID(), rep.getMessage(), MessageType.INCOMING);
                // If the friends table is already displaying this friend's messages, update the
                // screen with the new message, if not, update the number of unread message
                if (rep.getSenderID() == selectedFriend)
                {
                    friendsChatTable.setActiveFriend(rep.getSenderID());
                }
                else
                {
                    friendsTable.updateUnreadLabel(rep.getSenderID(), getNumUnread(rep.getSenderID()));
                }
            }
            // IF THE MESSAGE IS OUTGOING
            if (rep.getType() == ChatType.Private
                    && rep.getSenderID() == ClientPlayerManager.getSingleton().getThisClientsPlayer().getID())
            {
                friendsChatTable.addMessage(rep.getReceiverID(), rep.getMessage(), MessageType.OUTGOING);

                // If the friends table is already displaying this friend's messages, update the
                // screen with the new message
                if (rep.getReceiverID() == selectedFriend)
                {
                    friendsChatTable.setActiveFriend(rep.getReceiverID());
                }
            }

        }
        else if (report.getClass().equals(CurrentFriendListReport.class))
        {
            //System.out.println("I've updated the friends list");
            CurrentFriendListReport r = (CurrentFriendListReport) report;
            friendsList = r.getFriendList();
            friendsTable.displayFriends(friendsList);
        }
        else if (report.getClass().equals(UpdateFriendsListReport.class))
        {
            UpdateFriendsListReport r = (UpdateFriendsListReport) report;
            ArrayList<FriendDTO> friendList = r.getFriendList();
            System.out.println(friendList.size());
            friendsTable.displayNewFriends(friendList);
            friendsTable.updateLabels();

        }
        else if (report.getClass().equals(FriendChangedStateReport.class))
        {
            FriendChangedStateReport r = (FriendChangedStateReport) report;
            friendsTable.refreshList();
        }
    }

    /**
     * Setter for selected friend
     *
     * @param selectedFriend id of the friend whose messages are to be displayed on the right
     *                       side
     */
    public void setSelectedFriend(int selectedFriend)
    {
        this.selectedFriend = selectedFriend;
        friendsChatTable.setActiveFriend(selectedFriend);
        friendsTable.updateUnreadLabel(selectedFriend, 0);
    }

    /**
     * Only exists so that friendsTable doesn't need a reference to the
     * FriendsChatTable
     *
     * @param friendID which friend to get num unread for
     * @return the number of unread messages for this friend
     */
    public int getNumUnread(int friendID)
    {
        return friendsChatTable.getNumUnread(friendID);
    }

    /**
     * Returns width of the window
     */
    @Override
    public float getMyWidth()
    {
        return WIDTH;
    }

    /**
     * Returns height of the window
     */
    @Override
    public float getMyHeight()
    {
        return HEIGHT;
    }

    /**
     * Swaps the visibility of the window
     */
    @Override
    public void toggleVisibility()
    {
        if (isVisible())
        {
            this.addAction(Actions.hide());
        }
        else
        {
            this.addAction(Actions.show());
        }

    }

}
