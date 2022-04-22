package model;

public class SmartPath
{
    private boolean[][] passabilityMap;
    private int playerId;

    public SmartPath(int playerId)
    {
        this.playerId = playerId;

        passabilityMap = ServerMapManager.getSingleton().getPassabilityMap();
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