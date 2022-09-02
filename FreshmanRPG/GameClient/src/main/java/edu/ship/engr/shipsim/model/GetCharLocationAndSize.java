package edu.ship.engr.shipsim.model;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This is an example on how to get the x/y coordinates and size of each character in PDF
 */
public class GetCharLocationAndSize extends PDFTextStripper
{

    private static String fileName;
    static List<PDFCharacter> charList = new ArrayList<>();

    /**
     * @param fileName
     * @throws IOException
     */
    public GetCharLocationAndSize(String fileName) throws IOException
    {
        this.fileName = fileName;
    }

    public static void main(String[] args) throws IOException
    {
        String file = "ShopPurchaseReceipt.pdf";
        List<PDFCharacter> pos = getCharacterPosition(file);
        PDFCharacter test = pos.get(0);
        PDFCharacter test2 = pos.get(1);
    }

    /**
     * @return
     * @throws IOException If there is an error parsing the document.
     */
    public static List<PDFCharacter> getCharacterPosition(String fileName) throws IOException
    {
        PDDocument document = null;
        try
        {
            document = PDDocument.load(new File("../GameClient-desktop/" + fileName));
            PDFTextStripper stripper = new GetCharLocationAndSize(fileName);
            stripper.setSortByPosition(true);
            stripper.setStartPage(0);
            stripper.setEndPage(document.getNumberOfPages());
            Writer dummy = new OutputStreamWriter(new ByteArrayOutputStream());
            stripper.writeText(document, dummy);
            charList.forEach(System.out::println);
            return charList;
        }
        finally
        {
            if (document != null)
            {
                document.close();
            }
        }
    }

    /**
     * Override the default functionality of PDFTextStripper.writeString()
     */
    @Override
    protected void writeString(String string, List<TextPosition> textPositions) throws IOException
    {
        ArrayList<String> positions = new ArrayList<>();
        positions.add("A");
        positions.add("B");
        positions.add("C");
        positions.add("H");
        for (TextPosition text : textPositions)
        {
            if (positions.contains(text.getUnicode()))
            {
                PDFCharacter character = new PDFCharacter(text.getUnicode(), text.getXDirAdj(), text.getYDirAdj());
                charList.add(character);
            }
        }
    }

    /**
     * @author mina0
     */
    public class PDFCharacter
    {
        public String character;
        private float x;
        public float y;

        /**
         * @param character
         * @param x
         * @param y
         */
        public PDFCharacter(String character, float x, float y)
        {
            this.character = character;
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString()
        {
            return character + ", " + x + ", " + y;
        }

        /**
         * @return x
         */
        public float getX()
        {
            return x;
        }

        public float getY()
        {
            return y;
        }
    }
} 