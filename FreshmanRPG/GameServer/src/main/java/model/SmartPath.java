package model;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class SmartPath
{
    private boolean[][] passabilityMap;
    private int playerId;

    public SmartPath(int playerId)
    {
        this.playerId = playerId;

        String mapFileName = OptionsManager.getSingleton().getMapName();
        String mapFilePath = giveMeAUTI(mapFileName);
        ServerMapManager.getSingleton().changeToGardlightNewFile(mapFilePath);

        passabilityMap = ServerMapManager.getSingleton().getPassabilityMap();
        printPassabilityMap();
    }

    public String giveMeAUTI(String mapFile)
    {
        String mapFilePath = "";
        URI path;
        try
        {
            path = SmartPath.class.getProtectionDomain().getCodeSource()
                    .getLocation().toURI();
        }
        catch (URISyntaxException e1)
        {
            e1.printStackTrace();
            return "";
        }

        try
        {
            URL decodedPath = path.toURL();

            mapFilePath = (new URL(decodedPath, "../../../../maps/" + mapFile))
                    .toURI().getSchemeSpecificPart();
        }
        catch (MalformedURLException | URISyntaxException e)
        {
            e.printStackTrace();
        }
        
        return mapFilePath;
    }

    public void printPassabilityMap()
    {
        for (int i = 0; i < passabilityMap.length; i++)
        {
            for (int j = 0; j < passabilityMap[i].length; j++)
            {
                System.out.print(passabilityMap[i][j] + " ");
            }
            System.out.println();
        }
    }

}