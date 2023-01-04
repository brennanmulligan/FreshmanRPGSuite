package edu.ship.engr.shipsim.view.player;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import edu.ship.engr.shipsim.datatypes.VanityType;
import edu.ship.engr.shipsim.model.ReportObserverConnector;
import edu.ship.engr.shipsim.model.Report;
import edu.ship.engr.shipsim.model.ReportObserver;
import edu.ship.engr.shipsim.model.reports.ChatReceivedReport;
import edu.ship.engr.shipsim.view.screen.SkinPicker;

import java.util.PriorityQueue;
import java.util.Queue;

public class ChatBubble extends Group implements ReportObserver
{
    protected Vector2 dest;

    protected VanityType type;
    public float moveDuration = 0.3f;
    private float offsetX = 42;
    private float offSetY = 60;
    private int playerID;

    static Queue<ChatBubbleTable> queuedMessageTables;
    int bubbleId = 0;

    Skin skin;

    //constructor
    public ChatBubble(int playerID)
    {
        super();
        setUpListening();

        dest = new Vector2();
        this.playerID = playerID;

        skin = SkinPicker.getSkinPicker().getDefaultSkin();

        queuedMessageTables = new PriorityQueue<>();
    }

    /**
     * Sets up the ReportObserver for ChatReceivedReport
     */
    public void setUpListening()
    {
        ReportObserverConnector cm = ReportObserverConnector.getSingleton();
        cm.registerObserver(this, ChatReceivedReport.class);
    }

    public void setOffsetX(float offsetX)
    {
        this.offsetX = offsetX;
    }

    public float getOffsetX()
    {
        return offsetX;
    }

    public float getOffSetY()
    {
        return offSetY;
    }

    public void update(float x, float y)
    {
        Vector2 current = new Vector2();
        current.set(dest.x, dest.y);

        clearActions();

        addAction(
                Actions.moveTo(x + offsetX, y + offSetY, moveDuration, Interpolation.linear)
        );

        dest.set(x + offsetX, y + offSetY);
    }

    @Override
    public void receiveReport(Report report)
    {
        if (report.getClass() == ChatReceivedReport.class)
        {
            ChatReceivedReport chatReport = (ChatReceivedReport) report;
            if (chatReport.getSenderID() == playerID)
            {
                // Queue the message
                buildMessageTable(chatReport.getMessage());
            }
        }
    }

    public void buildMessageTable(String message)
    {
        ChatBubbleTable bubbleTable = new ChatBubbleTable(bubbleId++, message);

        SequenceAction sequence = new SequenceAction();
        sequence.addAction(Actions.fadeIn(0.2f));
        sequence.addAction(Actions.delay(6));
        sequence.addAction(Actions.fadeOut(0.4f));
        sequence.addAction(Actions.hide());
        sequence.addAction(Actions.run(() ->
        {
            if (queuedMessageTables.size() == 0)
            {
                return;
            }

            if (getChildren().size == 1)
            {
                ChatBubbleTable queuedBubble = queuedMessageTables.poll();
                addActor(queuedBubble);
            }
            else
            {
                queuedMessageTables.add(bubbleTable);
            }
        }));
        sequence.addAction(Actions.removeActor());

        bubbleTable.addAction(sequence);

        // Add the alert
        if (getChildren().size == 0)
        {
            // Add the alert normally
            addActor(bubbleTable);
        }
        else
        {
            // Queue it to be added later
            queuedMessageTables.add(bubbleTable);
        }
    }
}


