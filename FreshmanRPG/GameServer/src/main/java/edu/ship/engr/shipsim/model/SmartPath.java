package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.datatypes.AStarPosition;
import edu.ship.engr.shipsim.datatypes.Position;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Stack;

/**
 * @author Ryan Carroll and John Lang
 * <p>
 * Loads collision data and uses A* search algorithm to provide NPC smart path
 */
public class SmartPath
{
    private boolean[][] collisionMap;

    public SmartPath()
    {
        collisionMap = ServerMapManager.getSingleton().getCollisionMap(OptionsManager.getSingleton().getMapFileTitle());
    }

    /**
     * Sets up our priority queue (as per the A* algorithm) that uses our custom comparator. Our comparator uses each position's f(n) and puts the lowest
     * in the front of the queue. Adds initial position and positions directly accesible from that initial position. We then add those positions and repeat the
     * process, starting with the lowest f(n).
     *
     * @param source      the original square which we begin traveling
     * @param destination the target square we wish to get to with the optimal path
     * @return a stack containing the path steps composing the optimal path
     */
    public Stack<Position> aStar(Position source, Position destination)
    {
        boolean[][] isVisited = new boolean[collisionMap.length][collisionMap[0].length];
        /*
         * we use a comparator that looks at the AStarPosition's f(n), which is equal to
         * g(n) + h(n), where g() is the total path cost from source to current square and h() is our heuristic
         * estimating the total remaining distance from the current square to the destination square and n is our
         * current square. We put the lowest f(n) in the beginning of our priority queue and end with the highest
         * f(n).
         */
        PriorityQueue<AStarPosition> openPathSteps = new PriorityQueue<>(700,
                new AStarPositionComparator());
        AStarPosition originalSquare = new AStarPosition(source, source, destination, false, null);
        originalSquare.setVisited(true);
        /*
         * add the initial square to our priority queue, as per our algorithm
         */
        openPathSteps.add(originalSquare);

        while (!openPathSteps.isEmpty())
        {
            AStarPosition temp = openPathSteps.remove();

            //add square to the north
            Position north = new Position(temp.getRow() - 1, temp.getColumn());
            if (isValidLocation(north))
            {
                Stack<Position> northStar = addAStarPathStep(destination, openPathSteps,
                        originalSquare, temp, north, isVisited);
                if (northStar != null)
                {
                    return northStar;
                }
            }

            //add square to south
            Position south = new Position(temp.getRow() + 1, temp.getColumn());
            if (isValidLocation(south))
            {
                Stack<Position> southStar = addAStarPathStep(destination, openPathSteps,
                        originalSquare, temp, south, isVisited);
                if (southStar != null)
                {
                    return southStar;
                }
            }
            //add square to the east
            Position east = new Position(temp.getRow(), temp.getColumn() + 1);
            if (isValidLocation(east))
            {
                Stack<Position> eastStar = addAStarPathStep(destination, openPathSteps,
                        originalSquare, temp, east, isVisited);
                if (eastStar != null)
                {
                    return eastStar;
                }
            }

            //add square to the west
            Position west = new Position(temp.getRow(), temp.getColumn() - 1);
            if (isValidLocation(west))
            {
                Stack<Position> westStar = addAStarPathStep(destination, openPathSteps,
                        originalSquare, temp, west, isVisited);
                if (westStar != null)
                {
                    return westStar;
                }
            }
        }

        return null;
    }

    /**
     * Constructs and adds the new positions we are considering to our AStar open step tracker, if it hasn't been visited.
     *
     * @param destination    the square we wish to get to
     * @param openPathSteps  the queue containing the path steps we have encountered and have yet to take
     * @param originalSquare the starting square of our traversal
     * @param temp           represents the parent node of our new A Star position object we create
     * @param direction      the new direction to add to our list of openPathSteps
     * @param isVisited      boolean array of visited values
     * @return null if we haven't completed our traversal, otherwise return the path that led us to our destination
     * as a stack, with the first path step at the top, the destination at the bottom.
     */
    private Stack<Position> addAStarPathStep(Position destination, PriorityQueue<AStarPosition> openPathSteps,
                                             AStarPosition originalSquare, AStarPosition temp, Position direction,
                                             boolean[][] isVisited)
    {
        AStarPosition cardinalStar = new AStarPosition(direction,
                new Position(originalSquare.getRow(), originalSquare.getColumn()), destination,
                collisionMap[direction.getRow()][direction.getColumn()], temp);
        if (!isVisited[direction.getRow()][direction.getColumn()])
        {
            cardinalStar.setParent(temp);
            openPathSteps.add(cardinalStar);
            isVisited[cardinalStar.getRow()][cardinalStar.getColumn()] = true;
            if (cardinalStar.getRow() == destination.getRow() && cardinalStar.getColumn() == destination.getColumn())
            {
                return getPathSteps(cardinalStar);
            }
        }
        return null;
    }

    /**
     * Checks of position is valid
     *
     * @param pos position to check
     * @return true if valid, false if invalid
     */
    private boolean isValidLocation(Position pos)
    {
        if (pos.getRow() < collisionMap.length && pos.getRow() >= 0)
        {
            return pos.getColumn() < collisionMap[0].length && pos.getColumn() >= 0;
        }

        return false;
    }

    /**
     * Setter for collision map
     *
     * @param collisionMap value to set it as
     */
    public void setCollisionMap(boolean[][] collisionMap)
    {
        this.collisionMap = collisionMap;
    }

    /**
     * Loads path steps into a stack of positions
     *
     * @param targetPosition the target destination we successfully reached
     * @return Stack of positions path
     */
    private Stack<Position> getPathSteps(AStarPosition targetPosition)
    {
        Stack<Position> pathSteps = new Stack<>();
        AStarPosition currentPosition = new AStarPosition(targetPosition);
        if (currentPosition.getFOfN() == 1000000)
        {
            return null;
        }
        pathSteps.add(new Position(currentPosition.getRow(), currentPosition.getColumn()));
        while (currentPosition.getParent() != null)
        {
            pathSteps.add(new Position(currentPosition.getParent().getRow(), currentPosition.getParent().getColumn()));
            currentPosition = currentPosition.getParent();
            if (currentPosition.getFOfN() == 1000000)
            {
                return null;
            }
        }
        return pathSteps;
    }

    /**
     * Comparator for comparing astar positions
     */
    public static class AStarPositionComparator implements Comparator<AStarPosition>
    {
        @Override
        public int compare(AStarPosition a1, AStarPosition a2)
        {
            return Double.compare(a1.getFOfN(), a2.getFOfN());
        }
    }
}