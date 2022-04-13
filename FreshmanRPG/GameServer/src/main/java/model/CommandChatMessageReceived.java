package model;

import datatypes.ChatType;
import datatypes.Position;

/**
 * @author Josh
 *
 *         Receives the message parameters and passes them to the ChatManager
 */
public class CommandChatMessageReceived extends Command
{
	private String chatText;
	private int senderID;
	private int receiverID;

	private Position location;
	private ChatType type;

	/**
	 * @param senderID is the name of the player who sent the message
	 * @param receiverID is the name of the player who receives the message
	 * @param chatText is the message that will be sent to server
	 * @param location of the player when the message was sent
	 * @param type is the type of message: local, world, area
	 */
	public CommandChatMessageReceived(int senderID, int receiverID, String chatText, Position location, ChatType type)
	{
		this.senderID = senderID;
		this.receiverID = receiverID;
		this.chatText = chatText;
		this.location = location;
		this.type = type;
	}

	/**
	 * The ChatManager will call the sendChatToUI method with the following
	 * parameters.
	 */
	@Override
	boolean execute()
	{
		ChatManager.getSingleton().processChatMessage(senderID, receiverID, chatText, location, type);
		return true;
	}

	/**
	 * @return the message that was received the server
	 */
	public String getChatText()
	{
		return chatText;
	}

	/**
	 * @return the location of the player when the message was sent
	 */
	public Position getLocation()
	{
		return location;
	}

	/**
	 * @return the ID of the player who sent the text
	 */
	public int getSenderID()
	{
		return senderID;
	}

	/**
	 *
	 * @return receiverID
	 */
	public int getReceiverID()
	{
		return receiverID;
	}

	/**
	 * @return the type of message
	 */
	public ChatType getType()
	{
		return type;
	}
}
