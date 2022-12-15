package edu.ship.engr.shipsim.view.screen.chat;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import edu.ship.engr.shipsim.datatypes.ChatTextDetails;
import edu.ship.engr.shipsim.datatypes.ChatType;
import edu.ship.engr.shipsim.view.screen.OverlayingScreenTable;
import edu.ship.engr.shipsim.view.screen.SkinPicker;

import java.util.ArrayList;

/**
 * View for the PopUpChatUI
 */
public class ChatTable extends OverlayingScreenTable
{
    private Skin skin;

    private ArrayList<ChatTextDetails> chatHistory = new ArrayList<>();
    private Table chatHistoryView;
    private ChatType type;

    /**
     * @param scrollable = defines if the table is scrollable
     *                   It now takes a ChatType as a parameter
     */
    public ChatTable(boolean scrollable, ChatType type)
    {
        super(scrollable);
        this.type = type;
        skin = SkinPicker.getSkinPicker().getDefaultSkin();
        chatHistoryView = new Table();
    }


    /**
     * Add's chatResponse to chatHistory
     *
     * @param chatResponse new chat message
     */
    public void addChatResponse(ChatTextDetails chatResponse)
    {


        chatHistory.add(chatResponse);
        Label l = new Label(chatResponse.getMessage(), skin);
        l.setWrap(true);

        if (type == ChatType.System)
        {
            l.setColor(Color.CYAN);
        }

        chatHistoryView.top().add(l).expandX().fillX().pad(1);
        chatHistoryView.row();
    }


    /**
     * @return the ChatType of this ChatTable
     */
    public ChatType getChatType()
    {
        return type;
    }


    /**
     * @return the table containing the labels of messages
     */
    public Table getChatHistory()
    {
        return chatHistoryView;
    }


}
