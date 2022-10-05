package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author alan, regi
 */
@GameTest("GameServer")
public class AddExistingPlayerReportTest
{

    /**
     * Test the report constructor and values.
     */
    @Test
    public void testConstructor()
    {
        AddExistingPlayerReport report = new AddExistingPlayerReport(
                PlayersForTest.QUIZBOT.getPlayerID(),
                PlayersForTest.JOHN.getPlayerID(),
                PlayersForTest.JOHN.getPlayerName(),
                PlayersForTest.JOHN.getAppearanceType(),
                PlayersForTest.JOHN.getPosition(),
                PlayersForTest.JOHN.getCrew(),
                PlayersForTest.JOHN.getMajor(),
                PlayersForTest.JOHN.getSection(),
                new ArrayList<>()
        );

        assertEquals(PlayersForTest.QUIZBOT.getPlayerID(), report.getRecipientPlayerID());
        assertEquals(PlayersForTest.JOHN.getPlayerID(), report.getPlayerID());
        assertEquals(PlayersForTest.JOHN.getPlayerName(), report.getPlayerName());
        assertEquals(PlayersForTest.JOHN.getAppearanceType(), report.getAppearanceType());
        assertEquals(PlayersForTest.JOHN.getPosition(), report.getPosition());
        assertEquals(PlayersForTest.JOHN.getCrew(), report.getCrew());
        assertEquals(PlayersForTest.JOHN.getMajor(), report.getMajor());
        assertEquals(PlayersForTest.JOHN.getSection(), report.getSection());

    }

}
