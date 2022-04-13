package model;

import static org.junit.Assert.assertEquals;

import org.easymock.EasyMock;
import org.junit.Test;

import datasource.DatabaseException;
import datatypes.Crew;
import datatypes.Major;
import datatypes.Position;
import model.reports.ChangePlayerAppearanceReport;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

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
		p.setName("name");
		p.setAppearanceTypeNoReport("type");
		p.setCrew(Crew.NULL_POINTER);
		p.setMajor(Major.ELECTRICAL_ENGINEERING);

		assertEquals("name", p.getName());
		assertEquals("type", p.getAppearanceType());
		assertEquals(Crew.NULL_POINTER, p.getCrew());
		assertEquals(Major.ELECTRICAL_ENGINEERING, p.getMajor());

		p.setAppearanceTypeReport("type2");
		assertEquals( "type2", p.getAppearanceType() );
	}

	/**
	 * Make sure that we generate a report when changing the appearance should cause a report
	 * and that we don't when we shouldn't
	 */
	@Test
	public void testAppearanceReportIsGenerated()
	{
		QualifiedObserver obs = EasyMock.createMock(QualifiedObserver.class);
		obs.receiveReport(new ChangePlayerAppearanceReport(1,"Nothing"));
		QualifiedObservableConnector.getSingleton().registerObserver(obs, ChangePlayerAppearanceReport.class);
		EasyMock.replay(obs);

		ClientPlayer p = new ClientPlayer(1);
		p.setAppearanceTypeReport("Nothing");
		EasyMock.verify(obs);

		obs = EasyMock.createMock(QualifiedObserver.class);
		QualifiedObservableConnector.getSingleton().registerObserver(obs, ChangePlayerAppearanceReport.class);
		EasyMock.replay(obs);

		p = new ClientPlayer(1);
		p.setAppearanceTypeNoReport("Nothing2");
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
