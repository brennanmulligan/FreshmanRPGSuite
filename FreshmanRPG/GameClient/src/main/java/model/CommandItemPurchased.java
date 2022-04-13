package model;

import java.io.IOException;

import model.reports.ClientKeyInputSentReport;
import model.reports.ItemPurchasedReport;

/**
 * @author Andrew Stake
 * @author Josh Wood
 *
 */
public class CommandItemPurchased extends Command
{

	private int playerID;
	private int price;
	private String fileTitle;
	private String itemName;

	/**
	 * @param playerID player ID of the player that made the purchase
	 * @param price the price of the thing the player bought
	 * @param fileTitle the name of the file for the prize that was purchased
	 * @param itemName the name of the prize
	 */
	public CommandItemPurchased(int playerID, int price, String fileTitle, String itemName)
	{
		this.playerID = playerID;
		this.price = price;
		this.fileTitle = fileTitle;
		this.itemName = itemName;
	}

	/**
	 * @return the players ID
	 */
	public int getPlayerID()
	{
		return playerID;
	}

	/**
	 * @return price
	 */
	public int getPrice()
	{
		return price;
	}

	/**
	 *
	 * @return the file title for the prize purchased
	 */
	public String getFileTitle()
	{
		return fileTitle;
	}

	@Override
	boolean execute() throws IOException
	{
		//Create and send a report that will make the knowledge points be subtracted
		ItemPurchasedReport report = new ItemPurchasedReport(playerID, price);
		QualifiedObservableConnector.getSingleton().sendReport(report);

		//Create the pdf receipt for the purchase
		PDFPrizeWriter writer = new PDFPrizeWriter();
		writer.createPDFOfPurchasedPrize(fileTitle, price, itemName);
		return true;
	}

}
