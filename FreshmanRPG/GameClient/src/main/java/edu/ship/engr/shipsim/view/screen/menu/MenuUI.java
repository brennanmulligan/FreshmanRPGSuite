package edu.ship.engr.shipsim.view.screen.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import edu.ship.engr.shipsim.datatypes.ChatType;
import edu.ship.engr.shipsim.model.ClientPlayerManager;
import edu.ship.engr.shipsim.model.ReportObserver;
import edu.ship.engr.shipsim.model.ReportObserverConnector;
import edu.ship.engr.shipsim.model.Report;
import edu.ship.engr.shipsim.model.reports.ChatReceivedReport;
import edu.ship.engr.shipsim.view.screen.OverlayingScreen;
import edu.ship.engr.shipsim.view.screen.SkinPicker;
import edu.ship.engr.shipsim.view.screen.notification.AlertContainer;

import java.util.ArrayList;

/**
 * MenuUI.java UI for the Menu
 *
 * @author Zachary & Abdul, Ian Keefer, TJ Renninger
 */
public class MenuUI extends Group implements ReportObserver
{
    private final float HEIGHT = 30f;
    private final Skin skin;
    private final Skin unreadSkin = new Skin(Gdx.files.internal("ui-data/ui/screenskins" +
            "/ui-yellow.json"));
    private final ArrayList<OverlayingScreen> overlayingScreens;
    private final Table tabs;
    public static final String CLOSET_BUTTON_TEXT = "Closet";
    public static final String IN_GAME_SHOP_BUTTON_TEXT = "In Game Shop";
    public static final String REAL_LIFE_SHOP_BUTTON_TEXT = "Real Life Game Shop";

    private SelectBox<String> questSelectBox;
    private SelectBox<String> clothingSelectBox;

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
     * @param overlayingScreen That is to be toggled with the MenuUI
     * @param buttonLabel      Text displayed within th button
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
     * @param text     text the button will display
     * @param listener action the button will do
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
     *
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
     * Adds the clothing dropdown menu to the UI
     *
     * @param text          - the the label of the dropdown menu
     * @param listener      - The listener listening to the dropdown menu
     * @param clickListener - the click listener that will close the windows.
     */
    public void addDropdownClothing(String text, ChangeListener listener, ClickListener clickListener)
    {
        clothingSelectBox = new SelectBox<>(skin);
        clothingSelectBox.setColor(Color.GRAY);
        clothingSelectBox.setItems(
                CLOSET_BUTTON_TEXT,
                IN_GAME_SHOP_BUTTON_TEXT,
                REAL_LIFE_SHOP_BUTTON_TEXT);

        clothingSelectBox.addListener(listener);
        clothingSelectBox.addListener(clickListener);

        tabs.add(clothingSelectBox);
    }

    /**
     * Closes all open overlaying screen.
     */
    public void closeAllOverlayingScreens()
    {
        for (OverlayingScreen os : overlayingScreens)
        {
            if (os.getClass() == AlertContainer.class)
            {
                continue;
            }
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
                if (os.getClass() == AlertContainer.class)
                {
                    continue;
                }
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
     * get the quest select box item selected
     *
     * @return select box item
     */
    public SelectBox<String> getQuestSelectBox()
    {
        return questSelectBox;
    }


    /**
     * get the clothing select box item selected
     *
     * @return select box item
     */
    public SelectBox<String> getClothingSelectBox()
    {
        return clothingSelectBox;
    }

    /**
     * Receive the chat received report and update the friends button color if the screen is not visibile
     */
    @Override
    public void receiveReport(Report report)
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
                if (friendsScreen != null && !friendsScreen.isVisible())
                {
                    updateFriendButton(true);
                }
            }
        }
    }

    /**
     * Sets up the ReportObserver for ChatReceivedReport
     */
    public void setUpListening()
    {
        ReportObserverConnector cm = ReportObserverConnector.getSingleton();
        cm.registerObserver(this, ChatReceivedReport.class);
    }
}