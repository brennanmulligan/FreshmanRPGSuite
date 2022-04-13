package model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * 
 * @author sk5587
 *
 */
public class CommandDisplayTextTest
{

	/**
	 * make sure command holds right information 
	 */
	@Test
	public void test()
	{
		CommandDisplayText cmd = new CommandDisplayText("text");
		assertEquals("text", cmd.getText());
	}

}
