package view.screen.chat;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ObjectFloatMap;

import datatypes.AdventureStateEnum;
import datatypes.ChatTextDetails;
import datatypes.ChatType;
import datatypes.QuestStateEnum;
import model.ClientModelFacade;
import model.CommandChatMessageSent;
import model.QualifiedObservableConnector;
import model.QualifiedObservableReport;
import model.QualifiedObserver;
import model.reports.AdventureNeedingNotificationReport;
import model.reports.AdventureStateChangeReportInClient;
import model.reports.BuffPoolChangedReport;
import model.reports.ChatReceivedReport;
import model.reports.DisplayTextReport;
import model.reports.InteractionDeniedReport;
import model.reports.QuestNeedingNotificationReport;
import model.reports.QuestStateChangeReport;
import view.screen.OverlayingScreen;
import view.screen.SkinPicker;
import view.screen.popup.AdventureNotificationCompleteBehavior;
import view.screen.popup.QuestNotificationCompleteBehavior;

/**
 * Pops up the chat UI
 */
public class PopUpChatUI extends OverlayingScreen implements QualifiedObserver
{
	private final float WIDTH = 600f;
	private final float HEIGHT = 400f;
	private ChatTable localChatTable;
	private ChatTable zoneChatTable;
	private ChatTable systemChatTable;
	private ChatTable activeView;
	ChatType systemChatType = ChatType.System;
	ChatType localChatType = ChatType.Local;
	ChatType zoneChatType = ChatType.Zone;
	private Table grid;
	private TextButton localButton;
	private TextButton zoneButton;
	private TextButton systemButton;
	private TextFieldStyle tfs;
	private float width = 600f;
	private float height = 300f;
	private TextField entryField;
	private ObjectFloatMap<Label> newLabels;
	private Skin skin;
	private ScrollPane listPane;
	private Table listPaneContainer;


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
	public void receiveReport(QualifiedObservableReport report)
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
		else if (report.getClass().equals(AdventureNeedingNotificationReport.class))
		{
			AdventureNeedingNotificationReport r = (AdventureNeedingNotificationReport) report;
			AdventureNotificationCompleteBehavior behavior = new AdventureNotificationCompleteBehavior(r.getPlayerID(), r.getQuestID(), r.getAdventureID());
			AdventureStateEnum state = r.getState();

			systemChatTable.addChatResponse(new ChatTextDetails(systemLabel + "Adventure " + state.getDescription() + ": " + r.getAdventureDescription(), systemChatType));
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
		else if (report.getClass().equals(AdventureStateChangeReportInClient.class))
		{
			AdventureStateChangeReportInClient r = (AdventureStateChangeReportInClient) report;
			AdventureNotificationCompleteBehavior behavior = new AdventureNotificationCompleteBehavior(r.getPlayerID(), r.getQuestID(), r.getAdventureID());
			if (r.getNewState() == AdventureStateEnum.COMPLETED)
			{
				systemChatTable.addChatResponse(new ChatTextDetails(systemLabel + "Adventure Completed: " + r.getAdventureDescription(), systemChatType));
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
			systemChatTable.addChatResponse(new ChatTextDetails(systemLabel + "You have used 1 Bonus Point. You have " + r.getBuffPool() + " Quiznasium Bonus Points Left.", systemChatType));
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
		boolean scrollToBottom = !(listPane.getScrollPercentY() <= 0.9f);
		if (scrollToBottom)
		{
			listPane.layout();
			listPane.setScrollPercentY(1f);
		}
	}

	/**
	 * Sets up the QualifiedObserver for ChatReceivedReport
	 */
	public void setUpListening()
	{
		QualifiedObservableConnector cm = QualifiedObservableConnector.getSingleton();
		cm.registerObserver(this, QuestNeedingNotificationReport.class);
		cm.registerObserver(this, AdventureNeedingNotificationReport.class);
		cm.registerObserver(this, QuestStateChangeReport.class);
		cm.registerObserver(this, AdventureStateChangeReportInClient.class);
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
		newLabels = new ObjectFloatMap<>();
		skin = SkinPicker.getSkinPicker().getDefaultSkin();
		tfs = skin.get(TextField.TextFieldStyle.class);

		grid.setWidth(width);
		grid.setHeight(height);
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

		//TODO
		grid.row();
		listPane = new ScrollPane(activeView.getChatHistory(), skin);
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
