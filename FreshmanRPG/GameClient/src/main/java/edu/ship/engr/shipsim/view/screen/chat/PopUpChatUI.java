package edu.ship.engr.shipsim.view.screen.chat;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import edu.ship.engr.shipsim.datatypes.ChatTextDetails;
import edu.ship.engr.shipsim.datatypes.ChatType;
import edu.ship.engr.shipsim.datatypes.ObjectiveStateEnum;
import edu.ship.engr.shipsim.datatypes.QuestStateEnum;
import edu.ship.engr.shipsim.model.*;
import edu.ship.engr.shipsim.model.reports.*;
import edu.ship.engr.shipsim.view.screen.OverlayingScreen;
import edu.ship.engr.shipsim.view.screen.SkinPicker;
import edu.ship.engr.shipsim.view.screen.popup.ObjectiveNotificationCompleteBehavior;
import edu.ship.engr.shipsim.view.screen.popup.QuestNotificationCompleteBehavior;

/**
 * Pops up the chat UI
 */
public class PopUpChatUI extends OverlayingScreen implements ReportObserver
{
    private static final float WIDTH = 600f;
    private static final float HEIGHT = 300f;
    private final ChatTable localChatTable;
    private final ChatTable zoneChatTable;
    private final ChatTable systemChatTable;
    private ChatTable activeView;
    ChatType systemChatType = ChatType.System;
    ChatType localChatType = ChatType.Local;
    ChatType zoneChatType = ChatType.Zone;
    private Table grid;
    private TextButton localButton;
    private TextButton zoneButton;
    private TextButton systemButton;

    private TextField entryField;
    private ScrollPane listPane;


    /**
     * constructor for PopUpChatUI
     */
    public PopUpChatUI()
    {
        super();
        setUpListening();

        localChatTable = new ChatTable(true, localChatType);
        localChatTable.setFillParent(true);
        zoneChatTable = new ChatTable(true, zoneChatType);
        zoneChatTable.setFillParent(true);
        systemChatTable = new ChatTable(true, systemChatType);
        systemChatTable.setFillParent(true);

        activeView = zoneChatTable;
        container.addActor(activeView);

        setupUI();

    }

    /**
     * Handles chat received reports
     */
    @Override
    public void receiveReport(Report report)
    {
        String systemLabel = "[System]: ";

        if (report.getClass().equals(QuestNeedingNotificationReport.class))
        {
            QuestNeedingNotificationReport r = (QuestNeedingNotificationReport) report;
            QuestNotificationCompleteBehavior behavior = new QuestNotificationCompleteBehavior(r.getPlayerID(), r.getQuestID());
            QuestStateEnum state = r.getState();

            systemChatTable.addChatResponse(new ChatTextDetails(systemLabel + "Quest " + state.getDescription() + ": " + r.getQuestDescription(), systemChatType));
            behavior.clicked();

        }
        else if (report.getClass().equals(ObjectiveNeedingNotificationReport.class))
        {
            ObjectiveNeedingNotificationReport r = (ObjectiveNeedingNotificationReport) report;
            ObjectiveNotificationCompleteBehavior behavior = new ObjectiveNotificationCompleteBehavior(r.getPlayerID(), r.getQuestID(), r.getObjectiveID());
            ObjectiveStateEnum state = r.getState();

            systemChatTable.addChatResponse(new ChatTextDetails(systemLabel + "Objective " + state.getDescription() + ": " + r.getObjectiveDescription(), systemChatType));
            behavior.clicked();
        }
        else if (report.getClass().equals(QuestStateChangeReport.class))
        {
            QuestStateChangeReport r = (QuestStateChangeReport) report;
            QuestNotificationCompleteBehavior behavior = new QuestNotificationCompleteBehavior(r.getPlayerID(), r.getQuestID());
            if (r.getNewState() == QuestStateEnum.FULFILLED)
            {
                systemChatTable.addChatResponse(new ChatTextDetails(systemLabel + "Quest Fulfilled: " + r.getQuestDescription(), systemChatType));
                behavior.clicked();

            }
            else if (r.getNewState() == QuestStateEnum.COMPLETED)
            {
                systemChatTable.addChatResponse(new ChatTextDetails(systemLabel + "Quest Completed: " + r.getQuestDescription(), systemChatType));
                behavior.clicked();
            }
        }
        else if (report.getClass().equals(ObjectiveStateChangeReportInClient.class))
        {
            ObjectiveStateChangeReportInClient r = (ObjectiveStateChangeReportInClient) report;
            ObjectiveNotificationCompleteBehavior behavior = new ObjectiveNotificationCompleteBehavior(r.getPlayerID(), r.getQuestID(), r.getObjectiveID());
            if (r.getNewState() == ObjectiveStateEnum.COMPLETED)
            {
                systemChatTable.addChatResponse(new ChatTextDetails(systemLabel + "Objective Completed: " + r.getObjectiveDescription(), systemChatType));
                behavior.clicked();
            }
            else if (r.getNewState() == ObjectiveStateEnum.LATE)
            {
                systemChatTable.addChatResponse(new ChatTextDetails(systemLabel + "Objective Late: " + r.getObjectiveDescription(), systemChatType));
                behavior.clicked();
            }
        }
        else if (report.getClass().equals(InteractionDeniedReport.class))
        {
            InteractionDeniedReport r = (InteractionDeniedReport) report;
            systemChatTable.addChatResponse(new ChatTextDetails(systemLabel + "Interact Denied: " + r.getPopupMessage(), systemChatType));
        }
        else if (report.getClass().equals(DisplayTextReport.class))
        {
            DisplayTextReport r = (DisplayTextReport) report;
            systemChatTable.addChatResponse(new ChatTextDetails(r.getText(), systemChatType));
        }
        else if (report.getClass().equals(BuffPoolChangedReport.class))
        {
            BuffPoolChangedReport r = (BuffPoolChangedReport) report;
            systemChatTable.addChatResponse(new ChatTextDetails(systemLabel + "You have used 1 Bonus Point. You have " + r.getBuffPool() + " Rec Center Bonus Points Left.", systemChatType));
        } // adds a message spoken by users to be displayed in the UI
        else if (report.getClass().equals(ChatReceivedReport.class))
        {
            //used for zone and local chat types
            ChatReceivedReport r = (ChatReceivedReport) report;
            if (r.getType() == localChatType)
            {
                localChatTable.addChatResponse(new ChatTextDetails(r.toString(), r.getType()));
            }
            else if (r.getType() == zoneChatType)
            {
                zoneChatTable.addChatResponse(new ChatTextDetails(r.toString(), r.getType()));
            }
            else
            {
                systemChatTable.addChatResponse(new ChatTextDetails(r.toString(), r.getType()));
            }
        }
        listPane.layout();
        listPane.setScrollPercentY(1f);
    }

    /**
     * Sets up the ReportObserver for ChatReceivedReport
     */
    public void setUpListening()
    {
        ReportObserverConnector cm = ReportObserverConnector.getSingleton();
        cm.registerObserver(this, QuestNeedingNotificationReport.class);
        cm.registerObserver(this, ObjectiveNeedingNotificationReport.class);
        cm.registerObserver(this, QuestStateChangeReport.class);
        cm.registerObserver(this, ObjectiveStateChangeReportInClient.class);
        cm.registerObserver(this, InteractionDeniedReport.class);
        cm.registerObserver(this, DisplayTextReport.class);
        cm.registerObserver(this, BuffPoolChangedReport.class);
        cm.registerObserver(this, ChatReceivedReport.class);

    }

    /**
     * Sets up the UI
     */
    private void setupUI()
    {

        if (grid != null)
        {
            removeActor(grid);
        }

        grid = new Table();
        Skin skin = SkinPicker.getSkinPicker().getDefaultSkin();
        TextFieldStyle tfs = skin.get(TextFieldStyle.class);

        grid.setWidth(WIDTH);
        grid.setHeight(HEIGHT);
        //set up zone chat button
        zoneButton = new TextButton("Zone", skin);
        zoneButton.setColor(Color.DARK_GRAY);
        //on local button click
        zoneButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                //show only zone chat
                activeView = zoneChatTable;
                listPane.setWidget(activeView.getChatHistory());
                //highlight zone button
                localButton.setColor(Color.LIGHT_GRAY);
                zoneButton.setColor(Color.DARK_GRAY);
                systemButton.setColor(Color.LIGHT_GRAY);
                //enable text box
                entryField.setVisible(true);
            }
        });

        localButton = new TextButton("Local", skin);
        localButton.setColor(Color.LIGHT_GRAY);
        //on local button click
        localButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                //show only local chat
                activeView = localChatTable;
                listPane.setWidget(activeView.getChatHistory());
                //highlight local button
                localButton.setColor(Color.DARK_GRAY);
                zoneButton.setColor(Color.LIGHT_GRAY);
                systemButton.setColor(Color.LIGHT_GRAY);
                //enable text box
                entryField.setVisible(true);
            }
        });

        //set up system chat buttons
        systemButton = new TextButton("System", skin);
        systemButton.setColor(Color.LIGHT_GRAY);
        //on system button click
        systemButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                //show only system chat
                activeView = systemChatTable;
                listPane.setWidget(activeView.getChatHistory());
                //highlight system button
                localButton.setColor(Color.LIGHT_GRAY);
                zoneButton.setColor(Color.LIGHT_GRAY);
                systemButton.setColor(Color.DARK_GRAY);
                //disable text box
                entryField.setVisible(false);
            }
        });

        HorizontalGroup group = new HorizontalGroup();
        group.addActor(zoneButton);
        group.addActor(localButton);
        group.addActor(systemButton);
        grid.add(group).left();
        grid.row();

        entryField = new TextField("", skin);
        tfs.fontColor = Color.BLACK;
        entryField.setStyle(tfs);

        //listener for user submitting text with enter key
        entryField.addListener(new InputListener()
        {
            @Override
            public boolean keyDown(InputEvent event, int keycode)
            {
                if (keycode == Keys.ENTER)
                {
                    sendMessage();
                    return true;
                }
                return super.keyDown(event, keycode);
            }
        });

        grid.row();
        listPane = new ScrollPane(activeView.getChatHistory(), skin);
        listPane.setForceScroll(false, true);
        listPane.setFadeScrollBars(false);
        grid.add(listPane).expandX().fillX().height(300f).width(567.5f);
        grid.row();
        grid.add(entryField).expandX().fillX().height(32f);
        container.add(grid);
        grid.pad(15);
    }

    /**
     * Generates a chat message command and updates the ui
     */
    private void sendMessage()
    {
        String message = entryField.getText().trim();

        ChatTextDetails cm = new ChatTextDetails(message, activeView.getChatType());
        CommandChatMessageSent cmd = new CommandChatMessageSent(cm);
        ClientModelFacade.getSingleton().queueCommand(cmd);
        entryField.setText("");
    }

    /**
     * Returns the width of the PopUpChatUI
     */
    @Override
    public float getMyWidth()
    {
        return WIDTH;
    }

    /**
     * Returns the height of the PopUpChatUI
     */
    @Override
    public float getMyHeight()
    {
        return HEIGHT;
    }

    /**
     * Toggles the visibility of the PopUpChatUI
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
