package model;

import java.util.Comparator;
import java.util.PriorityQueue;

public class SmartPath
{
    private boolean[][] collisionMap;

    public SmartPath(int playerId)
    {
        collisionMap = ServerMapManager.getSingleton().getCollisionMap();
    }

    private void AStar()
    {
        PriorityQueue<AStarPosition> openPathSteps = new PriorityQueue<AStarPosition>(700, new AStarPositionComparator());
    }

    /**
     * Comparator for comparing astar positions
     */
    public static class AStarPositionComparator implements Comparator<AStarPosition>
    {
        @Override
        public int compare(Object o1, Object o2) {
            return 0;
        }
    }
}