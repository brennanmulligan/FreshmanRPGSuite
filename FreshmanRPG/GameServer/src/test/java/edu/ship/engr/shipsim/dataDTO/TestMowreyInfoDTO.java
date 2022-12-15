package edu.ship.engr.shipsim.dataDTO;

import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@GameTest("GameServer")
public class TestMowreyInfoDTO
{
    @Test
    public void testMowreyInfoDTOConstructor()
    {
        LocalDate ed = LocalDate.of(2001, 1, 1);
        LocalDate sd = LocalDate.of(2000, 1, 1);

        MowreyInfoDTO mowreyDTO = new MowreyInfoDTO(710, "How you doin'?", "Dandy", sd, ed);

        assertEquals(710, mowreyDTO.getId());
        assertEquals("How you doin'?", mowreyDTO.getQuestion());
        assertEquals("Dandy", mowreyDTO.getAnswer());
        assertEquals(sd, mowreyDTO.getStartDate());
        assertEquals(ed, mowreyDTO.getEndDate());
    }
}
