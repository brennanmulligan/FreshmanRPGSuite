package runners;

import java.io.IOException;
import java.net.Socket;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import communication.ConnectionManager;
import communication.StateAccumulator;
import communication.handlers.MessageHandlerSet;
import communication.packers.MessagePackerSet;
import model.ClientModelFacade;
import model.OptionsManager;
import view.screen.ScreenBasic;
import view.screen.Screens;

/**
 * The most basic gui!
 *
 * @author Merlin
 *
 */
public class GameLibGDX extends Game
{
	private ConnectionManager cm;

	/**
	 *
	 * @see com.badlogic.gdx.ApplicationListener#create()
	 */
	@Override
	public void create()
	{

		Socket socket;
		try
		{
			String host = OptionsManager.getSingleton().getLoginHost();
			socket = new Socket(host, 1871);
			MessagePackerSet messagePackerSet = new MessagePackerSet();
			StateAccumulator stateAccumulator = new StateAccumulator(messagePackerSet);

			cm = new ConnectionManager(socket, stateAccumulator,
					new MessageHandlerSet(stateAccumulator), messagePackerSet, false);


		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.exit(-1);
		}

		// tell the model to initialize itself
		ClientModelFacade.getSingleton();
		Screens.LOGIN_SCREEN.setGame(this);

		// start at the login screen
		ScreenBasic screen = Screens.LOGIN_SCREEN.getScreen();
		this.setScreen(screen);
		Gdx.input.setInputProcessor(screen.getStage());
	}

	/**
	 * @see com.badlogic.gdx.ApplicationListener#dispose()
	 */
	@Override
	public void dispose()
	{
		super.dispose();
		cm.disconnect();
	}
}
