package edu.ship.engr.shipsim.model;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.util.Matrix;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Creates a PDF printout for Prize Items bought with Doubloons
 *
 * @author Kevin Marek
 */
public class PDFPrizeWriter
{
    private static PDFont DESCRIPTIONFONT = PDType1Font.HELVETICA;
    int fontSize = 14;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    private String timestamp;
    private String playerName;
    private String finalTitle;

    private PDPage page;
    private PDRectangle pageSize;

    /**
     * Create a file that serves as a receipt for a doubloon prize purchase
     *
     * @param fileTitle - item purchased, becomes part of the final file name
     * @param price     - doubloons spent on the item
     * @throws IOException
     */
    public void createPDFOfPurchasedPrize(String fileTitle, int price, String itemName) throws IOException
    {
        // Retrieve the required information to put in the pdf
        getTimestamp();
        playerName = ClientPlayerManager.getSingleton().getThisClientsPlayer().getName();
        finalTitle = formatFileTitle(fileTitle);
        PDDocument doc = new PDDocument();
        page = new PDPage(PDRectangle.LETTER);
        page.setRotation(90);
        doc.addPage(page);
        page = doc.getPage(0);
        pageSize = page.getMediaBox();
        float pageWidth = pageSize.getWidth();


        PDPageContentStream contents;
        try
        {
            contents = new PDPageContentStream(doc, page);

            // add the rotation using the current transformation
            // matrix
            // including a translation of pageWidth to use the lower
            // left corner as 0,0 reference
            contents.transform(new Matrix(0, 1, -1, 0, pageWidth, 0));

            File fileImage = new File("../GameClient-desktop/ShopPurchaseReceipt.jpg");
            PDImageXObject pdImage = PDImageXObject.createFromFile(
                    fileImage.getAbsolutePath(), doc);
            contents.saveGraphicsState();
            contents.drawImage(pdImage, 0, 0);
            contents.restoreGraphicsState();

            // Set up formatting of text
            contents.beginText();
            contents.setFont(DESCRIPTIONFONT, fontSize);
            contents.setLeading(20f);


            // Add the player name to the pdf
            contents.newLineAtOffset(400, 270);
            System.out.println(playerName);
            contents.showText(playerName);
            contents.endText();

            contents.beginText();
            contents.setFont(DESCRIPTIONFONT, fontSize);
            contents.setLeading(20f);

            // Add the item name to the pdf
            contents.newLineAtOffset(400, 230);
//			fileTitle = fileTitle.substring(fileTitle.lastIndexOf('\\') + 1);
//			fileTitle = fileTitle.substring(0, fileTitle.indexOf('.'));
            contents.showText(itemName);
            contents.endText();

            contents.beginText();
            contents.setFont(DESCRIPTIONFONT, fontSize);
            contents.setLeading(20f);

            // Add the price to the pdf
            contents.newLineAtOffset(400, 190);
            contents.showText("" + price);
            contents.endText();

            contents.beginText();
            contents.setFont(DESCRIPTIONFONT, fontSize);
            contents.setLeading(20f);

            // Add the timestamp to the pdf
            contents.newLineAtOffset(400, 150);
            contents.showText(timestamp);


            contents.endText();

            contents.close();

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        // Store the pdf in the file system
        try
        {
            doc.save(finalTitle);
            doc.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    /**
     * Get the current timestamp and format it into an appropriate string
     */
    private void getTimestamp()
    {
        LocalDateTime timestamp = LocalDateTime.now();
        this.timestamp = timestamp.format(formatter);
    }

    /**
     * Make sure the file title has a pdf suffix
     *
     * @param fileTitle - the name of the item
     * @return
     */
    private String formatFileTitle(String fileTitle)
    {
        if (!fileTitle.endsWith(".pdf"))
        {
            fileTitle += ".pdf";
        }
        return fileTitle;
    }
}
