package model;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.util.Matrix;

import dataDTO.ClientPlayerAdventureStateDTO;
import dataDTO.ClientPlayerQuestStateDTO;
import datatypes.AdventureStateEnum;

/**
 * Creates a PDF file of the current players triggered adventures
 *
 * @author Merlin
 *
 */
public class PDFAdventureWriter
{

	private static PDFont FANCYFONT = PDType1Font.TIMES_BOLD_ITALIC;
	private static PDFont DESCRIPTIONFONT = PDType1Font.HELVETICA;
	private PDPage page;
	private PDRectangle pageSize;

	/**
	 * Creates a file containing the player's currently triggered adventures
	 *
	 * @param fileTitle
	 *            the title of the file we should create
	 */
	public void createPDFOfTriggeredExternalAdventures(String fileTitle)
	{
		PDDocument doc = new PDDocument();
		for (ClientPlayerQuestStateDTO q : ClientPlayerManager.getSingleton()
				.getThisClientsPlayer().getQuests())
		{
			for (ClientPlayerAdventureStateDTO a : q.getAdventureList())
			{

				if (a.isRealLifeAdventure() && a.getAdventureState().equals(AdventureStateEnum.TRIGGERED))
				{
					page = new PDPage(PDRectangle.LETTER);
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
						File file = new File("AdventureTemplate.jpg");
						PDImageXObject pdImage = PDImageXObject.createFromFile(
								file.getAbsolutePath(), doc);
						contents.saveGraphicsState();
						contents.drawImage(pdImage, 0, 0);
						contents.restoreGraphicsState();

						// Quest name
						contents.beginText();
						contents.newLineAtOffset(75, 545);
						contents.setFont(FANCYFONT, 14);
						contents.showText("Part of quest titled '" + q.getQuestTitle()
								+ "'");
						contents.endText();

						// Adventure description
						showAdventureDescription(contents, a.getAdventureDescription());

						// Experience points
						showExperiencePoints(contents, a.getAdventureXP());

						// Witness title
						contents.beginText();
						contents.newLineAtOffset(580, 85);
						contents.setFont(FANCYFONT, 18);
						contents.showText(a.getWitnessTitle());
						contents.endText();

						contents.close();

					}
					catch (IOException e)
					{
						// TODO Auto-generated catch block
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * If points are two digits, center it differently
	 *
	 * @param contents
	 * @param points
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * If description is > 100 chars, split it into two strings based on nearest
	 * whitespace to middle
	 *
	 * @param contents
	 * @param description
	 */
	private void showAdventureDescription(PDPageContentStream contents, String description)
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
