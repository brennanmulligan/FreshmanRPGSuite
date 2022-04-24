package model;

import java.util.Comparator;
import java.util.PriorityQueue;

public class SmartPath
{
    private boolean[][] collisionMap;
    private int playerId;

    public SmartPath(int playerId)
    {
        this.playerId = playerId;

        passabilityMap = ServerMapManager.getSingleton().getPassabilityMap();
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