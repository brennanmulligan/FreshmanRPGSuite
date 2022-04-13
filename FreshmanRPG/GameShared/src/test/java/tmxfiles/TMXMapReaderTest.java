package tmxfiles;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;

import org.junit.Test;

/**
 * @author Merlin
 *
 */
public class TMXMapReaderTest
{

	/**
	 * Just make sure we can find the locations of the necessary images
	 */
	@Test
	public void test()
	{
		TMXMapReader reader = new TMXMapReader("maps/current.tmx");

		ArrayList<String> imageFileTitles = reader.getImageFileTitles();
		assertEquals(2, imageFileTitles.size());
		assertEquals("tileset/qubodup-bush_0.png", imageFileTitles.get(0));
		assertEquals("tileset/qubodup-bush_berries_0.png", imageFileTitles.get(1));

	}

}
