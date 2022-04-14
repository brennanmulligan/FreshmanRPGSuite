package view.screen.popup;

import java.util.ArrayList;

import com.badlogic.gdx.scenes.scene2d.Stage;

import model.QualifiedObservableReport;
import model.QualifiedObserver;

/**
 * @author Cody/Scott Sends popup data to constructor from reports observed
 */
public class DisplayPopupPrompt implements QualifiedObserver
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
	 * Sets up the QualifiedObserver for ObjectivesNeedingNotificationReport
	 */
	public void setUpListening()
	{

	}

	/**
	 * @see model.QualifiedObserver#receiveReport(model.QualifiedObservableReport)
	 */
	@Override
	public void receiveReport(QualifiedObservableReport report)
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
