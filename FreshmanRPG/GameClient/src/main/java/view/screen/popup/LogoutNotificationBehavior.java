package view.screen.popup;

import model.ClientModelFacade;
import model.CommandLogout;

/**
 * Behavior for the Logout Notification
 * @author Zachary Thompson & Abdullah Alsuwailem
 *
 */
public class LogoutNotificationBehavior implements PopupBehavior
{

	/**
	 * Method called when button is hit
	 */
	@Override
	public void clicked()
	{
		CommandLogout cmd = new CommandLogout();
		ClientModelFacade.getSingleton().queueCommand(cmd);
	}

}
