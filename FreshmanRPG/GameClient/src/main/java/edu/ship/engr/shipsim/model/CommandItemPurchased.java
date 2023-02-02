package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.model.reports.ItemPurchasedReport;

import java.io.IOException;

/**
 * @author Andrew Stake
 * @author Josh Wood
 */
public class CommandItemPurchased extends Command
{

    private final int playerID;
    private final int price;
    private final String fileTitle;
    private final String itemName;

    /**
     * @param playerID  player ID of the player that made the purchase
     * @param price     the price of the thing the player bought
     * @param fileTitle the name of the file for the prize that was purchased
     * @param itemName  the name of the prize
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
     * @return the file title for the prize purchased
     */
    public String getFileTitle()
    {
        return fileTitle;
    }

    @Override
    void execute()
    {
        //Create and send a report that will make the doubloons be subtracted
        ItemPurchasedReport report = new ItemPurchasedReport(playerID, price);
        ReportObserverConnector.getSingleton().sendReport(report);

        //Create the pdf receipt for the purchase
        PDFPrizeWriter writer = new PDFPrizeWriter();
        try
        {
            writer.createPDFOfPurchasedPrize(fileTitle, price, itemName);
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

}
