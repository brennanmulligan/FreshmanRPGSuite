package model;

import datatypes.ChatType;
import datatypes.Position;
import model.reports.ChatMessageReceivedReport;
import model.reports.ChatMessageToClientReport;
import model.reports.NPCChatReport;

/**
 * @author Dave
 *
 *         Handles chat messages. Broadcasting chat from one client out to
 *         everyone unless it is a cheat code. In that case, do the thing it
 *         warrants.
 */
public class ChatManager
{
	private static ChatManager me;
	private final CheatCodeManager cheatCodeManager;

	/**
	 * Singleton constructor
	 */
	private ChatManager()
	{
		cheatCodeManager = new CheatCodeManager();
	}

	/**
	 * There can be only one.
	 *
	 * @return The current instance of the singleton
	 */
	protected static synchronized ChatManager getSingleton()
	{
		if (me == null)
		{
			me = new ChatManager();
		}

		return me;
	}

	/**
	 * Reset the singleton
	 */
	public static void resetSingleton()
	{
		OptionsManager.getSingleton().assertTestMode();
		me = null;
	}

	/**
	 * Broadcasts the chat message out to all connected clients
	 *
	 * @param senderID
	 *            The name of the player who sent the message
	 * @param receiverID
	 * 		      the player who receives the text
	 * @param message
	 *            The text of the message
	 * @param pos
	 *            The position of the player when they sent the message
	 * @param type
	 *            The type of chat message this is
	 */
	protected void sendChatToClients(int senderID, int receiverID, String message, Position pos, ChatType type)
	{
		ChatMessageToClientReport report = new ChatMessageToClientReport(senderID,
				receiverID,	message, pos, type);
		QualifiedObservableConnector.getSingleton().sendReport(report);

	}

	/**
	 * Broadcasts the chat message out to all NPCs
	 *
	 * @param senderID
	 *            The name of the player who sent the message
	 * @param receiverID
	 * 		      the player who receives the text
	 * @param message
	 *            The text of the message
	 * @param pos
	 *            The position of the player when they sent the message
	 * @param type
	 *            The type of chat message this is
	 */
	protected void sendChatToNPCs(int senderID, int receiverID, String message, Position pos, ChatType type)
	{
		NPCChatReport report = new NPCChatReport(senderID, receiverID, message, pos, type);
		QualifiedObservableConnector.getSingleton().sendReport(report);

	}

	/**
	 *
	 * @param senderID
	 *            the player who sent the text
	 * @param receiverID
	 * 		      the player who receives the text
	 * @param chatText
	 *            the text they sent
	 * @param location
	 *            where they are standing on this map
	 * @param type
	 *            the type of chat they are attempting to complete
	 */
	public void processChatMessage(int senderID, int receiverID, String chatText, Position location, ChatType type)
	{
		if (!cheatCodeManager.handleChatTextForCheatBehaviors(senderID, chatText))
		{
			//Send the chat to clients before sending to NPCS
			//So player messages show up before NPC responses
			sendChatToClients(senderID, receiverID, chatText, location, type);
			sendChatToNPCs(senderID, receiverID, chatText, location, type);
			QualifiedObservableConnector.getSingleton().sendReport(new ChatMessageReceivedReport(senderID,
					receiverID, chatText, location,
				type));
		}
	}
}