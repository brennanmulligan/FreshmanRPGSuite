package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.dataDTO.ClientPlayerObjectiveStateDTO;
import edu.ship.engr.shipsim.dataDTO.ClientPlayerQuestStateDTO;
import edu.ship.engr.shipsim.datatypes.ObjectiveStateEnum;
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

/**
 * Creates a PDF file of the current players triggered objectives
 *
 * @author Merlin
 */
public class PDFObjectiveWriter
{

    private static final PDFont FANCYFONT = PDType1Font.TIMES_BOLD_ITALIC;
    private static final PDFont DESCRIPTIONFONT = PDType1Font.HELVETICA;

    private PDRectangle pageSize;

    /**
     * Creates a file containing the player's currently triggered objectives
     *
     * @param fileTitle the title of the file we should create
     */
    public void createPDFOfTriggeredExternalObjectives(String fileTitle)
    {
        PDDocument doc = new PDDocument();
        for (ClientPlayerQuestStateDTO quest : ClientPlayerManager.getSingleton()
                .getThisClientsPlayer().getQuests())
        {
            for (ClientPlayerObjectiveStateDTO objective : quest.getObjectiveList())
            {

                if (objective.isRealLifeObjective() && objective.getObjectiveState().equals(ObjectiveStateEnum.TRIGGERED))
                {
                    PDPage page = new PDPage(PDRectangle.LETTER);
                    page.setRotation(90);
                    doc.addPage(page);

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

                        // Place the image as the background
                        File file = new File("ObjectiveTemplate.jpg");
                        PDImageXObject pdImage = PDImageXObject.createFromFile(
                                file.getAbsolutePath(), doc);
                        contents.saveGraphicsState();
                        contents.drawImage(pdImage, 0, 0);
                        contents.restoreGraphicsState();

                        // Quest name
                        contents.beginText();
                        contents.newLineAtOffset(75, 545);
                        contents.setFont(FANCYFONT, 14);
                        contents.showText("Part of quest titled '" + quest.getQuestTitle()
                                + "'");
                        contents.endText();

                        // Objective description
                        showObjectiveDescription(contents, objective.getObjectiveDescription());

                        // Experience points
                        showExperiencePoints(contents, objective.getObjectiveXP());

                        // Witness title
                        contents.beginText();
                        contents.newLineAtOffset(580, 85);
                        contents.setFont(FANCYFONT, 18);
                        contents.showText(objective.getWitnessTitle());
                        contents.endText();

                        contents.close();

                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            }

        }
        try
        {
            doc.save(fileTitle);
            doc.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    /**
     * If points are two digits, center it differently
     *
     * @param contents What should go onto the page
     * @param points   How many points the objective is worth
     */
    private void showExperiencePoints(PDPageContentStream contents, int points)
    {
        int fontSize = 18;
        try
        {
            if (points < 10)
            {
                contents.beginText();
                contents.newLineAtOffset(432, 341);
                contents.setFont(FANCYFONT, fontSize);
                contents.showText(String.valueOf(points));
                contents.endText();
            }
            else
            {
                contents.beginText();
                contents.newLineAtOffset(426, 341);
                contents.setFont(FANCYFONT, fontSize);
                contents.showText(String.valueOf(points));
                contents.endText();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * If description is > 100 chars, split it into two strings based on nearest
     * whitespace to middle
     *
     * @param contents    The contents of the page
     * @param description The description of the objective
     */
    private void showObjectiveDescription(PDPageContentStream contents, String description)
    {
        int fontSize = 14;
        try
        {
            if (description.length() <= 100)
            {
                float descriptionWidth = DESCRIPTIONFONT.getStringWidth(description)
                        / 1000 * fontSize;

                contents.beginText();
                contents.newLineAtOffset((pageSize.getHeight() - descriptionWidth) / 2,
                        430);
                contents.setFont(DESCRIPTIONFONT, 14);
                contents.showText(description);
                contents.endText();
            }
            else
            {
                int index = description.length() / 2 - 1;

                // Find the next whitespace and split it there
                while (description.charAt(index) != ' ')
                {
                    index++;
                }

                String desc1 = description.substring(0, index + 1);
                String desc2 = description.substring(index + 1);

                float desc1Width = DESCRIPTIONFONT.getStringWidth(desc1) / 1000
                        * fontSize;
                float desc2Width = DESCRIPTIONFONT.getStringWidth(desc2) / 1000
                        * fontSize;

                contents.setFont(DESCRIPTIONFONT, fontSize);

                contents.beginText();
                contents.newLineAtOffset((pageSize.getHeight() - desc1Width) / 2, 440);
                contents.showText(desc1);
                contents.endText();

                contents.beginText();
                contents.newLineAtOffset((pageSize.getHeight() - desc2Width) / 2, 415);
                contents.showText(desc2);
                contents.endText();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }
}
