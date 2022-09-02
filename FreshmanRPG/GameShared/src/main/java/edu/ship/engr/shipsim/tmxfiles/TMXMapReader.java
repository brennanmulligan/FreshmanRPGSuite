package edu.ship.engr.shipsim.tmxfiles;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.util.ArrayList;

/**
 * parses a TMX map. Right now, all we need to find are the locations of the
 * images the file references
 *
 * @author Merlin
 */
public class TMXMapReader extends DefaultHandler
{

    private ArrayList<String> imageFileTitles = new ArrayList<>();

    /**
     * @param fileTitle The title of the tmx file we should parse
     */
    public TMXMapReader(String fileTitle)
    {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try
        {
            javax.xml.parsers.SAXParser saxParser = factory.newSAXParser();
            saxParser.parse(new File(fileTitle), this);

        }
        catch (Throwable err)
        {
        }
    }

    /**
     * @see org.xml.sax.helpers.DefaultHandler#startDocument()
     */
    public void startDocument() throws SAXException
    {
    }

    /**
     * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String,
     * java.lang.String, java.lang.String, org.xml.sax.Attributes)
     */
    public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException
    {
        if (qName.equals("image"))
        {
            imageFileTitles.add(atts.getValue("source"));
        }
    }

    /**
     * @see org.xml.sax.helpers.DefaultHandler#endDocument()
     */
    public void endDocument() throws SAXException
    {

    }

    /**
     * @return a list of the image file titles the document references
     */
    public ArrayList<String> getImageFileTitles()
    {
        return imageFileTitles;
    }
}