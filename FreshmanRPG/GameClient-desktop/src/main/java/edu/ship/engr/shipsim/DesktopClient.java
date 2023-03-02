package edu.ship.engr.shipsim;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import edu.ship.engr.shipsim.datasource.LoggerManager;
import edu.ship.engr.shipsim.model.OptionsManager;
import edu.ship.engr.shipsim.runners.GameLibGDX;

import static edu.ship.engr.shipsim.view.screen.Screens.DEFAULT_RES;

/**
 * @author Merlin
 */
public class DesktopClient
{

    /**
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

        ClientContentLoader.execute();

        OptionsManager.getSingleton().setLoginHost(host);
        OptionsManager.getSingleton().setTestMode(false);
        LoggerManager.createLogger("Client");
        new LwjglApplication(new GameLibGDX(), "Game", (int) DEFAULT_RES[0], (int) DEFAULT_RES[1]);
    }
}