package edu.ship.engr.shipsim.tmxfiles;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * @author Merlin
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
