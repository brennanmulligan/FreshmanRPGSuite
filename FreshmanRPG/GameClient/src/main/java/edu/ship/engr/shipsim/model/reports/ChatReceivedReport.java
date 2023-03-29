package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.datatypes.ChatType;
import edu.ship.engr.shipsim.model.ClientPlayer;
import edu.ship.engr.shipsim.model.ClientPlayerManager;
import edu.ship.engr.shipsim.model.Report;
import edu.ship.engr.shipsim.view.screen.notification.NotificationType;

/**
 * Report for when a chat message is received
 *
 * @author Steve
 */
public class ChatReceivedReport implements NotificationTrigger, Report
{
    private final String message;
    private final int senderID;
    private final int receiverID;


    private final ChatType type;

    /**
     * @param senderID   Who sent the message
     * @param receiverID the id of the recipient
     * @param message    The message we received
     * @param type       What type of message this is
     */
    public ChatReceivedReport(int senderID, int receiverID, String message, ChatType type)
    {
        this.message = message;
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.type = type;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((message == null) ? 0 : message.hashCode());
        result = prime * result + receiverID;
        result = prime * result + senderID;
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        ChatReceivedReport other = (ChatReceivedReport) obj;
        if (message == null)
        {
            if (other.message != null)
            {
                return false;
            }
        }
        else if (!message.equals(other.message))
        {
            return false;
        }
        if (receiverID != other.receiverID)
        {
            return false;
        }
        if (senderID != other.senderID)
        {
            return false;
        }
        return type == other.type;
    }


    /**
     * @return the readable form of the message for the chat history
     */
    @Override
    public String toString()
    {
        ClientPlayer playerFromID = ClientPlayerManager.getSingleton().getPlayerFromID(this.senderID);
        String name;
        if (playerFromID == null)
        {
            name = "Somebody";
        }
        else
        {
            name = playerFromID.getName();
        }
        return name + ": " + this.message;
    }

    /**
     * @return the type of message this chat is broadcast as
     */
    public ChatType getType()
    {
        return this.type;
    }

    /**
     * @return message
     */
    public String getMessage()
    {
        return message;
    }

    /**
     * @return senderID
     */
    public int getSenderID()
    {
        return senderID;
    }

    /**
     * @return receiverID
     */
    public int getReceiverID()
    {
        return receiverID;
    }

    @Override
    public String getNotificationTitle()
    {
        return "New Message";
    }

    @Override
    public String getNotificationBody()
    {
        return "[" + type + "] " + message;
    }

    @Override
    public NotificationType getNotificationType()
    {
        if (this.type.equals(ChatType.System))
        {
            return NotificationType.ALERT;
        }
        return NotificationType.NONE;
    }
}
