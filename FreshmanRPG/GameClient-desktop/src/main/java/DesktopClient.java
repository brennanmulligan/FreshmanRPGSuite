import static view.screen.Screens.DEFAULT_RES;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

import model.OptionsManager;
import runners.GameLibGDX;

/**
 *
 * @author Merlin
 *
 */
public class DesktopClient
{

	/**
	 *
	 * @param args nothing
	 */
	public static void main(String[] args)
	{
		String host = "clipper.cs.ship.edu";
		for (String arg : args)
		{
			String[] splitArg = arg.split("=");
			if (splitArg[0].equals("--loginhost"))
			{
				host = splitArg[1];
			}
			if (splitArg[0].equals("--localhost"))
			{
				host = "localhost";
			}

		}
		OptionsManager.getSingleton().setLoginHost(host);
		OptionsManager.getSingleton().setUsingMocKDataSource(false);

		new LwjglApplication(new GameLibGDX(), "Game", (int) DEFAULT_RES[0], (int) DEFAULT_RES[1]);
	}
}