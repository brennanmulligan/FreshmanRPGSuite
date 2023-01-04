package edu.ship.engr.shipsim.view.screen.popup;

import com.badlogic.gdx.scenes.scene2d.Stage;
import edu.ship.engr.shipsim.model.Report;
import edu.ship.engr.shipsim.model.ReportObserver;

import java.util.ArrayList;

/**
 * @author Cody/Scott Sends popup data to constructor from reports observed
 */
public class DisplayPopupPrompt implements ReportObserver
{

    private ArrayList<ScreenPopUp> waitingPopUps;

    /**
     * Constructor for pop up display, set up observer
     *
     * @param stage stage of screen map
     */
    public DisplayPopupPrompt(Stage stage)
    {
        waitingPopUps = new ArrayList<>();
        setUpListening();
    }

    /**
     * Sets up the ReportObserver for ObjectivesNeedingNotificationReport
     */
    public void setUpListening()
    {

    }

    /**
     * @see ReportObserver#receiveReport(Report)
     */
    @Override
    public void receiveReport(Report report)
    {

    }

    /**
     * First popup was closed, show next one
     *
     * @param popup the popup that closed
     */
    public void dialogClosed(ScreenPopUp popup)
    {
        if (waitingPopUps.contains(popup))
        {
            waitingPopUps.remove(popup);
            showNextPopUp();
        }
    }

    /**
     * @param popup to be added
     */
    public void addWaitingPopUp(ScreenPopUp popup)
    {
        waitingPopUps.add(popup);

        // Display the first one in the list right away
        if (waitingPopUps.size() == 1)
        {
            showNextPopUp();
        }
    }

    /**
     * causes the next popUp to be shown;
     */
    public void showNextPopUp()
    {
        if (waitingPopUps.size() > 0)
        {
            waitingPopUps.get(0).showDialog();
        }
    }

    /**
     * @return true: if there is currently a popup being shown, false otherwise.
     */
    public boolean isPopUpCurrentlyShowing()
    {
        return this.waitingPopUps.size() != 0;
    }
}
