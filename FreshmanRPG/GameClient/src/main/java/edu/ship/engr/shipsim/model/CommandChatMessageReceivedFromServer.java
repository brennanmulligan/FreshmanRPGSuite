package edu.ship.engr.shipsim.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import edu.ship.engr.shipsim.datasource.ContentLoader;
import edu.ship.engr.shipsim.datatypes.ChatType;
import edu.ship.engr.shipsim.datatypes.Position;

/**
 * @author Matthew Kujawski
 * <p>
 * Receives the message parameters and passes them to the ChatManager
 */
public class CommandChatMessageReceivedFromServer extends Command
{
    private final String chatText;
    private final int senderID;
    private final int receiverID;
    private final Position location;
    private final ChatType type;

    /**
     * @param senderID   is the ID of the player who sent the message
     * @param receiverID is the ID of the player who receives the message
     * @param chatText   is the message that will be displayed by the UI
     * @param location   of the player when the message was sent
     * @param type       is the type of message: local, world, area
     */
    public CommandChatMessageReceivedFromServer(int senderID, int receiverID,
                                                String chatText, Position location, ChatType type)
    {
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.chatText = chatText;
        this.location = location;
        this.type = type;
    }

    /**
     * @return the message that was received the server
     */
    public String getChatText()
    {
        return chatText;
    }

    /**
     * @return the name of the player that sent the message
     */
    public int getSenderID()
    {
        return senderID;
    }

    /**
     * @return the name of the player who receives the message
     */
    public int getReceiverID()
    {
        return receiverID;
    }

    /**
     * @return the location of the player when the message was sent
     */
    public Position getLocation()
    {
        return location;
    }

    /**
     * @return the type of message
     */
    public ChatType getType()
    {
        return type;
    }

    /**
     * The ChatManager will call the sendChatToUI method with the following parameters.
     */
    @Override
    void execute()
    {
//		SoundManager.addSound(Gdx.audio.newSound(new FileHandle(new File("../GameClient/assets/message_notification.mp3"))), 1);
        SoundManager.addSound(Gdx.audio.newSound(new FileHandle(ContentLoader.getAssetFile("message_notification.mp3"))), 1);

        ClientChatManager.getSingleton().sendChatToUI(senderID, receiverID, chatText, location, type);
    }
}