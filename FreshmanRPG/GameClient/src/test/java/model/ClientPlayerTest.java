package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import dataDTO.VanityDTO;
import datatypes.VanityType;
import org.easymock.EasyMock;
import org.junit.Test;

import datasource.DatabaseException;
import datatypes.Crew;
import datatypes.Major;
import datatypes.Position;
import model.reports.ChangePlayerAppearanceReport;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import view.player.Vanity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Tests the player class
 *
 * @author merlin
 *
 */
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

	/**
	 * Make sure that we generate a report when changing the appearance should cause a report
	 * and that we don't when we shouldn't
	 */
	@Test
	public void testAppearanceReportIsGenerated()
	{
		VanityDTO vanityDTO = new VanityDTO();
		List<VanityDTO> vanityDTOS = new ArrayList<>();
		vanityDTOS.add(vanityDTO);

		QualifiedObserver obs = EasyMock.createMock(QualifiedObserver.class);
		obs.receiveReport(new ChangePlayerAppearanceReport(4,vanityDTOS));
		QualifiedObservableConnector.getSingleton().registerObserver(obs, ChangePlayerAppearanceReport.class);
		EasyMock.replay(obs);

		ClientPlayer p = new ClientPlayer(4);
		p.setVanityReport(vanityDTOS);
		EasyMock.verify(obs);

		obs = EasyMock.createMock(QualifiedObserver.class);
		QualifiedObservableConnector.getSingleton().registerObserver(obs, ChangePlayerAppearanceReport.class);
		EasyMock.replay(obs);

		VanityDTO vanityDTO2 = new VanityDTO(0, "Hat", "", "", VanityType.BODY);
		List<VanityDTO> vanityDTOS2 = new ArrayList<>();
		vanityDTOS.add(vanityDTO);

		p = new ClientPlayer(4);
		p.setVanityNoReport(vanityDTOS2);
		EasyMock.verify(obs);
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
	 * @throws DatabaseException
	 *             shouldn'ts
	 */
	@Test
	public void testPlayerPosition() throws DatabaseException
	{
		ClientPlayer p = new ClientPlayer(1);
		Position pos = new Position(3, 3);
		p.move(pos,false);
		assertEquals(pos, p.getPosition());
	}
}
