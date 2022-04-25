package datatypes;

/**
 * @author John Lang and Ryan Carroll
 */
public class AStarPosition extends Position
{

    private double hOfN;
    private double gOfN;
    private double fOfN;
    private AStarPosition parent;

    private boolean visited = false;


    public AStarPosition(AStarPosition positionToCopy)
    {
        super(positionToCopy.getRow(), positionToCopy.getColumn());

        this.fOfN = positionToCopy.getFOfN();
        this.parent = positionToCopy.getParent();
        this.visited = positionToCopy.isVisited();

    }

    public AStarPosition(Position moveToConsider,
                         Position start,
                         Position end,
                         boolean isCollision,
                         AStarPosition parent)
    {
        super(moveToConsider.getRow(), moveToConsider.getColumn());

        if(isCollision)
        {
            this.parent = parent;
            this.gOfN = 1000000;
            this.hOfN = 0;
        }
        else
        {

            if(moveToConsider.getRow() == start.getRow() && moveToConsider.getColumn() == start.getColumn())
            {
                this.hOfN = 0;
                this.gOfN = 0;
            }
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
     * @return visited
     */
    public boolean isVisited()
    {
        return visited;
    }

    /**
     * Setter for visited
     * @param visited boolean
     */
    public void setVisited(boolean visited)
    {
        this.visited = visited;
    }

    /**
     * Getter for parent
     * @return parent
     */
    public AStarPosition getParent()
    {
        return parent;
    }

    /**
     * Setter for parent
     * @param parent AStarPosition
     */
    public void setParent(AStarPosition parent)
    {
        this.parent = parent;
    }

    /**
     * Getter for GOfN
     * @return GOfN
     */
    public double getGOfN()
    {
        return this.gOfN;
    }

    /**
     * Getter for FOfN
     * @return FOfN
     */
    public double getFOfN()
    {
        return this.fOfN;
    }
}
