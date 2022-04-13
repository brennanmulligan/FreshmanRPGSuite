package model;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Merlin
 * 
 */
public class ModelFacadeTest
{

	/**
	 * reset the necessary singletons
	 */
	@Before
	public void setup()
	{
		ClientModelFacade.resetSingleton();
		MapManager.resetSingleton();
	}

	/**
	 * make sure it is a singleton
	 */
	@Test
	public void isResetableSingleton()
	{
		ClientModelFacade facade1 = ClientModelFacade.getSingleton(true, true);
		ClientModelFacade facade2 = ClientModelFacade.getSingleton(true, true);
		assertSame(facade1, facade2);
		ClientModelFacade.resetSingleton();
		assertNotSame(facade1, ClientModelFacade.getSingleton(true, true));
	}

	/**
	 * Make sure that we get an exception if we ask for a facade in a different
	 * mode than the current one without resetting it first
	 */
	@Test
	public void cantChangeModes()
	{
		boolean sawException = false;
		ClientModelFacade.getSingleton(true, true);
		try
		{
			ClientModelFacade.getSingleton(true, false);
		}
		catch (IllegalArgumentException e)
		{
			sawException = true;
		}
		assertTrue(sawException);

		ClientModelFacade.resetSingleton();
		ClientModelFacade.getSingleton(true, true);

		try
		{
			ClientModelFacade.getSingleton(false, true);
		}
		catch (IllegalArgumentException e)
		{
			sawException = true;
		}
		assertTrue(sawException);
	}

}
