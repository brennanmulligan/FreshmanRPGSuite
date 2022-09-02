package edu.ship.engr.shipsim.datatypes;

/**
 * @author John Lang and Ryan Carroll
 * a subclass of basic positions, this also stores our heurisitic score (h(n)), our total path cost to this position from the start position (g(n)), and adds
 * them together to get the f(n).
 */
public class AStarPosition extends Position
{

    private static final double COLLISION_SCORE = 1000000;
    private double hOfN;
    private double gOfN;
    private double fOfN;
    //keep track of the parent, as we need to backtrace our path from end to begininng
    private AStarPosition parent;
    //have we encountered this position previously in our A* search algorithm
    private boolean visited = false;

    /**
     * @param positionToCopy make a new A star position based on an all ready exisiting A star position
     */
    public AStarPosition(AStarPosition positionToCopy)
    {
        super(positionToCopy.getRow(), positionToCopy.getColumn());

        this.fOfN = positionToCopy.getFOfN();
        this.parent = positionToCopy.getParent();
        this.visited = positionToCopy.isVisited();

    }

    /**
     * @param moveToConsider the current position
     * @param start          the path start position
     * @param end            the ending position of the path
     * @param isCollision    whether this space is blocked by the collision layer
     * @param parent         the previous position in the potential path our A star is considering
     */
    public AStarPosition(Position moveToConsider,
                         Position start,
                         Position end,
                         boolean isCollision,
                         AStarPosition parent)
    {
        super(moveToConsider.getRow(), moveToConsider.getColumn());

        /*
         * make sure to set the cost of getting to a collision spot arbitrarily high, when constructing the viable path
         * this is handled, as we check if a collision occurred by checking if the g(n) score is equal to a collision score.
         */
        if (isCollision)
        {
            this.parent = parent;
            this.gOfN = COLLISION_SCORE;
            this.hOfN = 0;
        }
        else
        {
            //in the case we are at the starting position, make its f(n) = 0
            if (moveToConsider.getRow() == start.getRow() && moveToConsider.getColumn() == start.getColumn())
            {
                this.hOfN = 0;
                this.gOfN = 0;
            }
            //in the case we are anywhere but the start, add the total cost so far (parent's g(n) + 1) and assign and h(n) based on as the crow flies distance
            else
            {
                this.parent = parent;
                this.gOfN = 1 + parent.getGOfN();
                this.hOfN = Math.abs(end.getRow() - moveToConsider.getRow())
                        + Math.abs(end.getColumn() - moveToConsider.getColumn());
            }
        }

        this.fOfN = gOfN + hOfN;
    }

    /**
     * Getter for visited
     *
     * @return visited
     */
    public boolean isVisited()
    {
        return visited;
    }

    /**
     * Setter for visited
     *
     * @param visited boolean
     */
    public void setVisited(boolean visited)
    {
        this.visited = visited;
    }

    /**
     * Getter for parent
     *
     * @return parent
     */
    public AStarPosition getParent()
    {
        return parent;
    }

    /**
     * Setter for parent
     *
     * @param parent AStarPosition
     */
    public void setParent(AStarPosition parent)
    {
        this.parent = parent;
    }

    /**
     * Getter for GOfN
     *
     * @return GOfN
     */
    public double getGOfN()
    {
        return this.gOfN;
    }

    /**
     * Getter for FOfN
     *
     * @return FOfN
     */
    public double getFOfN()
    {
        return this.fOfN;
    }
}
