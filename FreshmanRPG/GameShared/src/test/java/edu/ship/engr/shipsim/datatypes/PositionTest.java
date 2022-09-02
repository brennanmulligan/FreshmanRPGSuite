package edu.ship.engr.shipsim.datatypes;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test the Position class
 *
 * @author merlin
 */
public class PositionTest
{

    /**
     * It just holds some data
     */
    @Test
    public void initialization()
    {
        Position p = new Position(42, 47);
        assertEquals(42, p.getRow());
        assertEquals(47, p.getColumn());
    }

    /**
     * Make sure that the equals method checks the right stuff
     */
    @Test
    public void equalsContract()
    {
        EqualsVerifier.forClass(Position.class).verify();
    }
}
