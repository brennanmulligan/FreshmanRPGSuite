package edu.ship.engr.shipsim.view.screen.friends;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import edu.ship.engr.shipsim.dataDTO.FriendDTO;
import edu.ship.engr.shipsim.datatypes.FriendStatusEnum;
import edu.ship.engr.shipsim.model.ClientPlayerManager;
import edu.ship.engr.shipsim.view.screen.OverlayingScreenTable;
import edu.ship.engr.shipsim.view.screen.SkinPicker;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * UI element for the left side of the friendsUI, shows the player's friends and
 * the number of unread messages from each
 *
 * @author Mina Kindo
 */
public class FriendsTable extends OverlayingScreenTable
{
    private FriendsUI chatTable;
    private static HashMap<Integer, FriendRow> friendLabels = new HashMap<>();
    private ArrayList<FriendDTO> friendList;

    /**
     * @param friendList the friends to display
     * @param scrollable whether or not the window can be scrolled
     */
    public FriendsTable(ArrayList<FriendDTO> friendList, boolean scrollable)
    {
        super(scrollable);
        this.friendList = friendList;
        displayFriends(friendList);
    }

    /**
     * @param chatTable the parent UI element
     */
    public void setChatTable(FriendsUI chatTable)
    {
        this.chatTable = chatTable;
    }

    /**
     * @param friendDTO A friend to be added to the UI
     * @return friendNameLabel
     */
    public Label createFriendLabel(final FriendDTO friendDTO)
    {
        Label friendNameLabel = new Label(friendDTO.getFriendName(), SkinPicker.getSkinPicker().getCrewSkin());

        //accepted friends will be white and clickable
        if (friendDTO.getStatus() == FriendStatusEnum.ACCEPTED && ClientPlayerManager.getSingleton().getPlayerFromID(friendDTO.getFriendID()) != null)
        {
            friendNameLabel.setColor(Color.WHITE);
            friendNameLabel.setName("acceptedOnServer ");
            friendNameLabel.addListener(new ClickListener()
            {

                @Override
                public void clicked(InputEvent event, float x, float y)
                {
                    updateLabels();
                    friendNameLabel.setColor(Color.CYAN);
                    chatTable.setSelectedFriend(friendDTO.getFriendID());
                    super.clicked(event, x, y);
                }
            });
        }
        else if (friendDTO.getStatus() == FriendStatusEnum.ACCEPTED && ClientPlayerManager.getSingleton().getPlayerFromID(friendDTO.getFriendID()) == null)
        {
            friendNameLabel.setColor(Color.LIGHT_GRAY);
            friendNameLabel.setName("acceptedNotOnServer");
            updateLabels();
        }
        //pending friends will be a nice, light gray
        else if (friendDTO.getStatus() == FriendStatusEnum.PENDING)
        {
            friendNameLabel.setColor(Color.GRAY);
            friendNameLabel.setName("pending");
            updateLabels();
        }
        //friends not on server will be regular gray
        else
        {
            //TODO: How to tell if someone is not on the server
            friendNameLabel.setColor(Color.GRAY);
            friendNameLabel.setName("requested");
            updateLabels();
        }
        return friendNameLabel;
    }

    /**
     * Update the friends table to the current friends that the player has.
     *
     * @param friendList The list of friends that the player has
     */
    public void displayFriends(ArrayList<FriendDTO> friendList)
    {
        this.friendList = friendList;
        container.clear();
        for (FriendDTO friendDTO : friendList)
        {
            Label friendNameLabel = createFriendLabel(friendDTO);
            container.add(friendNameLabel).top().left();
            String unreadMessages = "";
            if ((chatTable.getNumUnread(friendDTO.getFriendID()) > 0))
            {
                unreadMessages = String.valueOf(chatTable.getNumUnread(friendDTO.getFriendID()));
            }
            Label unreadLabel = new Label(unreadMessages, SkinPicker.getSkinPicker().getCrewSkin());
            container.add(unreadLabel).top().right().expandX();
            container.row();
            FriendRow friendRow = new FriendRow(friendNameLabel, unreadLabel);
            friendLabels.put(friendDTO.getFriendID(), friendRow);
        }
    }

    /**
     * update the friends list to add the new friend
     *
     * @param friendList
     */
    public void displayNewFriends(ArrayList<FriendDTO> friendList)
    {
        // TODO logic to add new guy to display
        //for (Label label : friendLabels) //check if the friend was pending

        this.friendList = friendList;
        if (friendLabels.containsKey(friendList.get(0).getFriendID()))
        {
            Label label = friendLabels.get(friendList.get(0).getFriendID()).getFriendNameLabel();
            //update label
            //label.remove();
            System.out.println("Updating label");
            label.setName("acceptedOnServer");
            label.addListener(new ClickListener()
            {

                @Override
                public void clicked(InputEvent event, float x, float y)
                {
                    updateLabels();
                    label.setColor(Color.CYAN);
                    chatTable.setSelectedFriend(friendList.get(0).getFriendID());
                    super.clicked(event, x, y);
                }
            });
            //friendLabels.add(friendNameLabel);
            //container.add(friendNameLabel).top().row();

            return;
        }

//		Label friendNameLabel = createFriendLabel((friendList.get(0)));
//		friendLabels.add(friendNameLabel);
//		container.add(friendNameLabel).top().row();

        Label friendNameLabel = createFriendLabel(friendList.get(0));
        container.add(friendNameLabel).top().left();
        String unreadMessages = "";
        if ((chatTable.getNumUnread(friendList.get(0).getFriendID()) > 0))
        {
            unreadMessages = String.valueOf(chatTable.getNumUnread(friendList.get(0).getFriendID()));
        }
        Label unreadLabel = new Label(unreadMessages, SkinPicker.getSkinPicker().getCrewSkin());
        container.add(unreadLabel).top().right().expandX();
        container.row();
        FriendRow friendRow = new FriendRow(friendNameLabel, unreadLabel);
        friendLabels.put(friendList.get(0).getFriendID(), friendRow);

    }

    /**
     * Changes the colors of the labels when a new friend is clicked on in the list
     */
    public void updateLabels()
    {
        for (FriendRow row : friendLabels.values())
        {
            if (row.getFriendNameLabel().getName().equals("acceptedOnServer"))
            {
                row.getFriendNameLabel().setColor(Color.WHITE);
            }
            else if (row.getFriendNameLabel().getName().equals("acceptedNotOnServer"))
            {
                row.getFriendNameLabel().setColor(Color.LIGHT_GRAY);
            }
            else if (row.getFriendNameLabel().getName().equals("pending"))
            {
                row.getFriendNameLabel().setColor(Color.GRAY);
            }
            else if (row.getFriendNameLabel().getName().equals("requested"))
            {
                row.getFriendNameLabel().setColor(Color.GRAY);
            }

        }
    }

    /**
     * Rebuilds the friends list when one of your friends joins or leaves your server
     */
    public void refreshList()
    {
        displayFriends(friendList);
    }

    /**
     * Update the label that contains the unread messages
     *
     * @param friendID       id to update
     * @param unreadMessages number to set it to
     */
    public void updateUnreadLabel(int friendID, int unreadMessages)
    {
        Label label = friendLabels.get(friendID).getNumberUnreadLabel();
        if (unreadMessages == 0)
        {
            label.setText("");
        }
        else
        {
            label.setText(String.valueOf(unreadMessages));
        }
    }

    /**
     * Get a friends name from the list using their ID
     *
     * @param friendID friend to search for
     * @return friend's name
     */
    public String getNameFromFriendList(int friendID)
    {
        for (FriendDTO friend : friendList)
        {
            if (friend.getFriendID() == friendID)
            {
                return friend.getFriendName();
            }
        }
        return "Something bad happened";
    }

}

/**
 * Contains references to both labels that make up a single row in the friends
 * table
 *
 * @author Kevin Marek, Mina Kindo
 */
class FriendRow
{
    Label friendName;
    Label numberUnread;

    public FriendRow(Label friendName, Label numberUnread)
    {
        this.friendName = friendName;
        this.numberUnread = numberUnread;
    }

    public Label getFriendNameLabel()
    {
        return friendName;
    }

    public void setFriendName(Label friendName)
    {
        this.friendName = friendName;
    }

    public Label getNumberUnreadLabel()
    {
        return numberUnread;
    }

    public void setNumberUnread(Label numberUnread)
    {
        this.numberUnread = numberUnread;
    }


}
