package edu.ship.engr.shipsim.model.reports;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.Array;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

/**
 * @author Merlin
 */
@GameTest("GameClient")
public class NewMapReportTest
{

    /**
     * make sure it gets built correctly
     */
    @Test
    public void creation()
    {
        TiledMap mockedMap = mock(TiledMap.class);
        NewMapReport newMapReport = new NewMapReport(mockedMap);

        assertEquals(mockedMap, newMapReport.getTiledMap());
        assertEquals(Objects.hash(mockedMap), newMapReport.hashCode());
    }

    /**
     * Make sure the equals contract is obeyed. In this case, we had to do some
     * magic to suppress some troubles in the TiledMap object we are
     * transporting. In particular, its structure is recursive (twice), so we
     * had to use .withPrefabValues to stop the recursion and it contains
     * mutable fields so we had to suppress that warning. These things shouldn't
     * cause problems in this environment
     */
    @Test
    public void equalsContract()
    {
        Array<Integer> array1 = new Array<>();
        array1.add(3);
        MapProperties p1 = new MapProperties();
        p1.put("stupid", 42);
        EqualsVerifier.forClass(NewMapReport.class)
                .withPrefabValues(Array.class, array1, new Array<Integer>())
                .withPrefabValues(MapProperties.class, p1, new MapProperties())
                .suppress(Warning.NONFINAL_FIELDS).verify();
    }
}
