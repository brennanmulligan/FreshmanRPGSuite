package edu.ship.engr.shipsim.datasource;

import edu.ship.engr.shipsim.model.OptionsManager;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ContentLoader
{
    private static Path getMapLocation()
    {
        if (OptionsManager.getSingleton().isRunningInIntelliJ())
        {
            return Paths.get(System.getProperty("user.dir"), "maps");
        }
        else if (OptionsManager.getSingleton().isRunningInDocker() || OptionsManager.getSingleton().isRunningInCI() || OptionsManager.getSingleton().isTestMode())
        {
            return Paths.get(System.getProperty("user.dir"), "..", "GameServer", "maps");
        }
        else
        {
            return Paths.get(getContentPath().toString(), "maps");
        }
    }

    private static Path getAssetLocation()
    {
        if (OptionsManager.getSingleton().isRunningInIntelliJ())
        {
            return Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "assets");
        }
        else if (OptionsManager.getSingleton().isRunningInCI() || OptionsManager.getSingleton().isTestMode())
        {
            throw new UnsupportedOperationException("Sounds aren't accessible in CI/Test mode");
        }
        else
        {
            return Paths.get(getContentPath().toString(), "assets");
        }
    }

    public static File getMapFile(String mapName)
    {
        return new File(getMapLocation().toFile(), mapName);
    }

    public static File getAssetFile(String assetName)
    {
        return new File(getAssetLocation().toFile(), assetName);
    }

    public static Path getContentPath()
    {
        return Paths.get(System.getProperty("user.home") + "/.frpg");
    }
}
