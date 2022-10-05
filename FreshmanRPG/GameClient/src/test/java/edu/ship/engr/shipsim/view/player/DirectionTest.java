package edu.ship.engr.shipsim.view.player;

import com.badlogic.gdx.math.Vector2;
import edu.ship.engr.shipsim.datatypes.Position;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author nhydock
 */
@GameTest("GameClient")
public class DirectionTest
{

    /**
     * Test direction determination from vector coordinates
     */
    @Test
    public void testGetDirectionFromVector()
    {
        Vector2 from = new Vector2();
        Vector2 to = new Vector2();

        // look to the east
        from.set(0, 0);
        to.set(1, 0);
        assertEquals(Direction.East, Direction.getFacing(from, to));

        // look down to the east (tests major x)
        from.set(0, 1);
        to.set(1, 0);
        assertEquals(Direction.East, Direction.getFacing(from, to));

        // look to the west
        from.set(2, 0);
        to.set(1, 0);
        assertEquals(Direction.West, Direction.getFacing(from, to));

        // look north
        from.set(0, 0);
        to.set(0, -1);
        assertEquals(Direction.South, Direction.getFacing(from, to));

        // look south
        from.set(0, 0);
        to.set(0, 1);
        assertEquals(Direction.North, Direction.getFacing(from, to));

        // test priority difference for setting direction
        from.set(0, 0);
        to.set(5, 3);
        assertEquals(Direction.East, Direction.getFacing(from, to));
        to.set(3, 5);
        assertEquals(Direction.North, Direction.getFacing(from, to));
    }

    /**
     * Test getting a next position in a direction
     */
    @Test
    public void testNextStepInDirection()
    {
        Position p = new Position(0, 0);

        Position next;

        // get in south
        next = Direction.getPositionInDirection(p, Direction.South);
        assertEquals(0, next.getColumn());
        assertEquals(1, next.getRow());

        // get in north
        next = Direction.getPositionInDirection(p, Direction.North);
        assertEquals(0, next.getColumn());
        assertEquals(-1, next.getRow());

        // get in east
        next = Direction.getPositionInDirection(p, Direction.East);
        assertEquals(1, next.getColumn());
        assertEquals(0, next.getRow());

        // get in west
        next = Direction.getPositionInDirection(p, Direction.West);
        assertEquals(-1, next.getColumn());
        assertEquals(0, next.getRow());

    }
}
