package communication.handlers;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import communication.messages.MapFileMessage;
import communication.messages.Message;
import model.ClientModelFacade;
import model.CommandNewMap;
import model.OptionsManager;

/**
 * Should process an incoming LoginResponseMessage. This means that we should
 * move our connection to the area server specified by that msg and initiate a
 * session with that server
 *
 * @author merlin
 *
 */
public class MapFileMessageHandler extends MessageHandler
{
	/**
	 *
	 * @see MessageHandler#process(Message)
	 */
	@Override
	public void process(Message msg)
	{
		URI path;
		try
		{
			path = MapFileMessageHandler.class.getProtectionDomain().getCodeSource()
					.getLocation().toURI();
		}
		catch (URISyntaxException e1)
		{
			e1.printStackTrace();
			return;
		}

		try
		{
			URL decodedPath = path.toURL();
			MapFileMessage mapFileMessage = (MapFileMessage) msg;
			String mapFileTitle = "maps/" + mapFileMessage.getMapFileName();
			String mapFile;
			String hostName = OptionsManager.getSingleton().getLoginHost();
			if (hostName.equals("localhost"))
			{
				mapFile = (new URL(decodedPath,
						"../../" + mapFileTitle))
						.toURI().getSchemeSpecificPart();
			}
			else
			{
				mapFile = (new URL(decodedPath, mapFileTitle)).toURI()
						.getSchemeSpecificPart();
			}

			ClientModelFacade.getSingleton().queueCommand(new CommandNewMap(mapFile,
					mapFileMessage.getFileContents() ));
		}
		catch (MalformedURLException | URISyntaxException e)
		{
			e.printStackTrace();
		}

	}

	/**
	 * @see communication.handlers.MessageHandler#getMessageTypeWeHandle()
	 */
	@Override
	public Class<?> getMessageTypeWeHandle()
	{
		return MapFileMessage.class;
	}
}
