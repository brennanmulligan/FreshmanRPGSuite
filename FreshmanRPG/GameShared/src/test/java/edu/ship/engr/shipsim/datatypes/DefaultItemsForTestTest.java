package edu.ship.engr.shipsim.datatypes;

import edu.ship.engr.shipsim.dataDTO.VanityDTO;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DefaultItemsForTestTest
{

    @Test
    void getTheList()
    {
        List<VanityDTO> result = DefaultItemsForTest.getDefaultItemsIsWearing();
        VanityDTO first = result.get(0);
        VanityItemsForTest firstInEnum = VanityItemsForTest.values()[DefaultItemsForTest.values()[0].getDefaultID()-1];
        assertEquals(firstInEnum.getName(), first.getName());
    }
}
