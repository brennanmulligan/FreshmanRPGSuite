package model;

import datatypes.Position;

import java.util.Comparator;
import java.util.PriorityQueue;

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
    private void AStar()
    {
        PriorityQueue<AStarPosition> openPathSteps = new PriorityQueue<AStarPosition>(700, new AStarPositionComparator());
    }

    private class AStarPositionComparator implements Comparator {

        @Override
        public int compare(Object o1, Object o2) {
            return 0;
        }
    }
}