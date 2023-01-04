package edu.ship.engr.shipsim.model;

import com.google.common.collect.Lists;
import edu.ship.engr.shipsim.dataDTO.VanityDTO;
import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datatypes.Crew;
import edu.ship.engr.shipsim.datatypes.Major;
import edu.ship.engr.shipsim.datatypes.Position;
import edu.ship.engr.shipsim.model.reports.ChangePlayerAppearanceReport;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Tests the player class
 *
 * @author merlin
 */
@GameTest("GameClient")
public class ClientPlayerTest
{

    /**
     * Make sure that players get initialized correctly.
     */
    @Test
    public void initialization()
    {
        ClientPlayer p = new ClientPlayer(1);
        assertEquals(1, p.getID());
    }

    /**
     * Simple test for all setters and getters
     */
    @Test
    public void testSettersGetters()
    {
        ClientPlayer p = new ClientPlayer(1);
        VanityDTO vanityDTO = new VanityDTO();
        List<VanityDTO> vanityDTOS = new ArrayList<>();
        vanityDTOS.add(vanityDTO);

        p.setName("name");
        p.setVanityReport(vanityDTOS);
        p.setCrew(Crew.FORTY_PERCENT);
        p.setMajor(Major.ELECTRICAL_ENGINEERING);

        assertEquals("name", p.getName());
        assertEquals(vanityDTOS, p.getVanities());
        assertEquals(Crew.FORTY_PERCENT, p.getCrew());
        assertEquals(Major.ELECTRICAL_ENGINEERING, p.getMajor());
    }

    @Test
    public void testSetVanityReport()
    {
        // mock the connector and observer
        ReportObserverConnector connector = spy(ReportObserverConnector.getSingleton());
        ReportObserver observer = mock(ReportObserver.class);

        // register the observer to be notified if a ChangePlayerAppearanceReport is sent
        connector.registerObserver(observer, ChangePlayerAppearanceReport.class);

        // create empty list because we need one
        List<VanityDTO> vanities = Lists.newArrayList();

        // create a player and call setVanityReport. This should send out a ChangePlayerAppearanceReport
        ClientPlayer player = new ClientPlayer(4);
        player.setVanityReport(vanities);

        // set up report for verification
        ChangePlayerAppearanceReport expectedReport = new ChangePlayerAppearanceReport(player.getID(), vanities);

        // since setVanityReport sends a report, verify that the observer was notified of it
        verify(observer, times(1)).receiveReport(eq(expectedReport));
    }

    @Test
    public void testSetVanityNoReport()
    {
        // mock the connector and observer
        ReportObserverConnector connector = spy(ReportObserverConnector.getSingleton());
        ReportObserver observer = mock(ReportObserver.class);

        // register the observer to be notified if a ChangePlayerAppearanceReport is sent
        connector.registerObserver(observer, ChangePlayerAppearanceReport.class);

        // create empty list because we need one
        List<VanityDTO> vanities = Lists.newArrayList();

        // create a player and call setVanityReport. This should not send out a ChangePlayerAppearanceReport
        ClientPlayer player = new ClientPlayer(4);
        player.setVanityNoReport(vanities);

        // set up report for verification
        ChangePlayerAppearanceReport expectedReport = new ChangePlayerAppearanceReport(player.getID(), vanities);

        // since setVanityNoReport doesn't send a report, verify that the observer wasn't notified of it
        verify(observer, never()).receiveReport(eq(expectedReport));
    }

    /**
     * Check the equals method
     */
    @Test
    public void equalsContract()
    {
        EqualsVerifier.forClass(ClientPlayer.class).suppress(Warning.NONFINAL_FIELDS).verify();
    }

    /**
     * Sets the players position and checks it
     *
     * @throws DatabaseException shouldn'ts
     */
    @Test
    public void testPlayerPosition() throws DatabaseException
    {
        ClientPlayer p = new ClientPlayer(1);
        Position pos = new Position(3, 3);
        p.move(pos, false);
        assertEquals(pos, p.getPosition());
    }
}
