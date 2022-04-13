package model;

import java.util.Observable;

import datatypes.ChatType;
import datatypes.Position;
import model.reports.ChatReceivedReport;
import model.reports.ChatSentReport;

/**
 * Handles all chat reporting and messaging
 *
 * @author Steve
 */
public class ClientChatManager
{
	/**
	 * The square radius that local chat can reach from a position
	 */
	private static int LOCAL_CHAT_RADIUS = 5;

	private static ClientChatManager singleton;

	/**
	 * Protected for singleton
	 */
	private ClientChatManager()
	{

	}

	/**
	 * There should be only one
	 *
	 * @return the only player
	 */
	public static synchronized ClientChatManager getSingleton()
	{
		if (singleton == null)
		{
			singleton = new ClientChatManager();
		}
		return singleton;
	}

	/**
	 * Reset the singleton but don't instantiate a new one
	 */
	public static synchronized void resetSingleton()
	{
		singleton = null;
	}

	/**
	 * @param senderID id of the player who sent the message
	 * @param receiverID the id of the player who is receiving the message
	 * @param message chat message to send to the ui
	 * @param position position of the player who sent the message
	 * @param type the type of this message
	 */
	public void sendChatToUI(int senderID, int receiverID, String message, Position position, ChatType type)
	{
		ChatReceivedReport report = new ChatReceivedReport(senderID, receiverID, message, type);

		// if chat type is not local chat, just send the message as is.
		if (!type.equals(ChatType.Local) && !type.equals(ChatType.Private))
		{
			QualifiedObservableConnector.getSingleton().sendReport(report);
		}
		//if chat type is Private check for IDs
		else if (type.equals(ChatType.Private))
		{
			if (senderID == ClientPlayerManager.getSingleton().getThisClientsPlayer().getID() ||
					receiverID == ClientPlayerManager.getSingleton().getThisClientsPlayer().getID())
			{
				QualifiedObservableConnector.getSingleton().sendReport(report);
			}
		}
		else
		{
			//if it is local, however, we need to check if they can hear it.
			if (this.canReceiveLocalMessage(position))
			{
				QualifiedObservableConnector.getSingleton().sendReport(report);
			}
		}
	}

	/**
	 * Send a report for a message this client generated, fetching the proper player
	 * name and location
	 *
	 * @param message chat message to send to the ui
	 * @param type the type of this message
	 */
	public void sendChatToServer(String message, ChatType type, int receiverID)
	{
		ClientPlayer thisPlayer = ClientPlayerManager.getSingleton().getThisClientsPlayer();
		ChatSentReport report = new ChatSentReport(thisPlayer.getID(), receiverID, message, thisPlayer.getPosition(), type);
		QualifiedObservableConnector.getSingleton().sendReport(report);
	}

	/**
	 * When receiving a local message check if the player is close enough to hear
	 *
	 * @param position position of the sender
	 */
	protected boolean canReceiveLocalMessage(Position position)
	{
		Position myPosition = ClientPlayerManager.getSingleton().getThisClientsPlayer().getPosition();

		return Math.abs(myPosition.getColumn() - position.getColumn()) <= LOCAL_CHAT_RADIUS && Math.abs(myPosition.getRow() - position.getRow()) <= LOCAL_CHAT_RADIUS;
	}
}