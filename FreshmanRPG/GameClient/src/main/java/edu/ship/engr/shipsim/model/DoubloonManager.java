package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.dataDTO.DoubloonPrizeDTO;
import edu.ship.engr.shipsim.model.reports.DoubloonPrizeListReport;

import java.util.ArrayList;

/**
 * @author am3243
 * @author Henry Wyatt
 */

public class DoubloonManager
{

    private static DoubloonManager singleton;
    private final ArrayList<DoubloonPrizeDTO> doubloonList;

    /**
     * initialize resource with existing widgets. Act as database.
     */
    private DoubloonManager()
    {
        this.doubloonList = new ArrayList<>();
    }

    /**
     * There should be only one
     *
     * @return the only player
     */
    public static synchronized DoubloonManager getSingleton()
    {
        if (singleton == null)
        {
            singleton = new DoubloonManager();
        }
        return singleton;
    }

    /**
     * Used only in testing to re-initialize the singleton
     */
    public static void resetSingleton()
    {
        OptionsManager.getSingleton().assertTestMode();
        singleton = null;
    }

    /**
     * Get the player that is playing on this client
     *
     * @return All Doubloons
     */
    public ArrayList<DoubloonPrizeDTO> getDoubloons()
    {
        return doubloonList;
    }

    /**
     * @param prize DoubloonPrize being added.
     */
    public void add(DoubloonPrizeDTO prize)
    {
        doubloonList.add(prize);
    }

    /**
     * @param prizes DoubloonPrize List being added.
     */
    public void add(ArrayList<DoubloonPrizeDTO> prizes)
    {
        doubloonList.addAll(prizes);
        receivedPrizeList(doubloonList);
    }

    /**
     * @param prize DoubloonPrize being deleted.
     */
    public void delete(DoubloonPrizeDTO prize)
    {
        doubloonList.remove(prize);
    }

    /**
     * @param prizes DoubloonPrize List being deleted.
     */
    public void delete(ArrayList<DoubloonPrizeDTO> prizes)
    {
        doubloonList.removeAll(prizes);
    }

    /**
     * Method for handling the reception of the Prize List
     *
     * @param prizeList - List of DTO Prizes
     */
    public void receivedPrizeList(ArrayList<DoubloonPrizeDTO> prizeList)
    {
        if (!DoubloonManager.getSingleton().getDoubloons().isEmpty())
        {
            ReportObserverConnector.getSingleton().sendReport(new DoubloonPrizeListReport(doubloonList));
        }
    }
}