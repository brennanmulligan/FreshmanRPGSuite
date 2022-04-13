package view.screen.menu;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import datatypes.ChatType;
import model.ClientPlayerManager;
import model.QualifiedObservableConnector;
import model.QualifiedObservableReport;
import model.QualifiedObserver;
import model.reports.ChatReceivedReport;
import view.screen.OverlayingScreen;
import view.screen.SkinPicker;

/**
 * MenuUI.java UI for the Menu
 *
 * @author Zachary & Abdul, Ian Keefer, TJ Renninger
 *
 */
public class MenuUI extends Group implements QualifiedObserver
{
	private final float HEIGHT = 30f;
	private Skin skin;
	private Skin unreadSkin = new Skin(Gdx.files.internal("ui-data/ui/screenskins/ui-yellow.json"));
	private ArrayList<OverlayingScreen> overlayingScreens;
	private Table tabs;
	private final String QUEST_BUTTON_TEXT = "Quests";
	private final String HIGHSCORE_BUTTON_TEXT = "Highscore";

	private SelectBox<String> selectBox;

	private Button friendButton;
	private Color defaultButtonColor;


	/**
	 * Create a new chat ui that displays at the bottom of the screen
	 */
	public MenuUI()
	{
		setHeight(HEIGHT);
		setPosition(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() - getHeight());
		overlayingScreens = new ArrayList<>();
		skin = SkinPicker.getSkinPicker().getCrewSkin();
		tabs = new Table(skin);
		tabs.setFillParent(true);
		addActor(tabs);
		setVisible(true);
		setUpListening();
	}

	/**
	 * @param overlayingScreen
	 *            That is to be toggled with the MenuUI
	 * @param buttonLabel
	 *            Text displayed within th button
	 */
	public void addOverlayingScreenToggle(final OverlayingScreen overlayingScreen, String buttonLabel)
	{
		overlayingScreen.setName(buttonLabel);
		overlayingScreens.add(overlayingScreen);
		ClickListener listener = new ClickListener()
		{
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				boolean wasVisible = overlayingScreen.isVisible();
				closeAllOverlayingScreens();
				if (!wasVisible)
				{
					overlayingScreen.toggleVisibility();
				}
				super.clicked(event, x, y);
			}
		};

		ClickListener friendListener = new ClickListener()
		{
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				boolean wasVisible = overlayingScreen.isVisible();
				closeAllOverlayingScreens();
				if (!wasVisible)
				{
					overlayingScreen.toggleVisibility();
				}
				updateFriendButton(false);
				super.clicked(event, x, y);
			}
		};

		/*
		 * If we do not wish to add the button to the top of the screen
		 * a null buttonLabel is passed as a parameter. This is used when we
		 * wish to add an overlayingScreenToggle to an entity that already exists inside
		 * the drop-down menu.
		 */
		if (buttonLabel != null)
		{
			if (buttonLabel.equals("Friends"))
			{
				addButton(buttonLabel, friendListener);
			}
			else
			{
				addButton(buttonLabel, listener);
			}
		}
	}

	/**
	 * @param text
	 *            text the button will display
	 * @param listener
	 *            action the button will do
	 */
	public void addButton(String text, ClickListener listener)
	{
		ButtonStyle style = skin.get(ButtonStyle.class);
		Button button = new Button(style);
		button.add(new Label(text, skin));
		button.addListener(listener);
		if (text.equals("Friends"))
		{
			friendButton = button;
		}
		tabs.add(button);
	}

	/**
	 * Set friend button to yellow if there are unread messages or the default color if there are none
	 * @param unread -  whether or not there are unread messages
	 */
	public void updateFriendButton(boolean unread)
	{
		if (unread)
		{
			friendButton.setStyle(unreadSkin.get(ButtonStyle.class));
		}
		else
		{
			friendButton.setStyle(skin.get(ButtonStyle.class));
		}
	}

	/**
	 * Adds the dropown menu to the UI
	 * @param text - the the label of the dropdown menu
	 * @param listener - The listener listening to the dropdown menu
	 * @param clickListener - the click listener that will close the windows. 
	 */
	public void addDropdown(String text, ChangeListener listener, ClickListener clickListener)
	{
		selectBox = new SelectBox<>(skin);
		selectBox.setColor(Color.GRAY);
		selectBox.setItems(
				QUEST_BUTTON_TEXT,
				HIGHSCORE_BUTTON_TEXT);

		selectBox.addListener(listener);
		selectBox.addListener(clickListener);

		tabs.add(selectBox);
	}


	/**
	 * Closes all open overlaying screen.
	 */
	public void closeAllOverlayingScreens()
	{
		for (OverlayingScreen os : overlayingScreens)
		{
			os.setVisible(false);
		}
	}

	/**
	 * @param clsType the class of the overlaying screen that shout NOT be closed (so that the toggle method still works)
	 */
	public void closeAllScreensExcludingSpecifiedType(Class<? extends OverlayingScreen> clsType)
	{
		for (OverlayingScreen os : overlayingScreens)
		{
			if (!os.getClass().equals(clsType))
			{
				os.setVisible(false);
			}
		}
	}

	/**
	 * @return ArrayList of all the overlaying screens
	 */
	public ArrayList<OverlayingScreen> getOverlayingScreens()
	{
		return overlayingScreens;
	}

	/**
	 * get the select box item selected
	 * @return select box item
	 */
	public SelectBox<String> getSelectBox()
	{
		return selectBox;
	}

	/**
	 * Receive the chat received report and update the friends button color if the screen is not visibile
	 */
	@Override
	public void receiveReport(QualifiedObservableReport report)
	{
		if (report.getClass() == ChatReceivedReport.class)
		{
			ChatReceivedReport rep = (ChatReceivedReport) report;

			if (rep.getType() == ChatType.Private && rep.getReceiverID() == ClientPlayerManager.getSingleton().getThisClientsPlayer().getID())
			{
				OverlayingScreen friendsScreen = null;
				for (OverlayingScreen screen : overlayingScreens)
				{
					if (screen.getName() != null)
					{
						if (screen.getName().equals("Friends"))
						{
							friendsScreen = screen;
						}
					}
				}
				if (!friendsScreen.isVisible())
				{
					updateFriendButton(true);
				}
			}
		}
	}

	/**
	 * Sets up the QualifiedObserver for ChatReceivedReport
	 */
	public void setUpListening()
	{
		QualifiedObservableConnector cm = QualifiedObservableConnector.getSingleton();
		cm.registerObserver(this, ChatReceivedReport.class);
	}
}