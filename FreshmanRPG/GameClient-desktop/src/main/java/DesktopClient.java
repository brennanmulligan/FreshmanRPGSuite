import static view.screen.Screens.DEFAULT_RES;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

import datasource.LoggerManager;
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
		String host = OptionsManager.getSingleton().getProductionHostName();
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

		if (!OptionsManager.getSingleton().isRunningInIntelliJ())
		{
			OptionsManager.getSingleton().setUsingClient(true);
			ClientContentLoader.execute();
		}

		OptionsManager.getSingleton().setLoginHost(host);
		OptionsManager.getSingleton().setTestMode(false);
		LoggerManager.createLogger("Client");
		new LwjglApplication(new GameLibGDX(), "Game", (int) DEFAULT_RES[0], (int) DEFAULT_RES[1]);
	}
}