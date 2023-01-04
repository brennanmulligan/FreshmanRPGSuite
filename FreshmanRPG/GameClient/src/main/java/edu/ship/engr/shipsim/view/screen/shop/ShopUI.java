package edu.ship.engr.shipsim.view.screen.shop;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import edu.ship.engr.shipsim.dataDTO.DoubloonPrizeDTO;
import edu.ship.engr.shipsim.model.Report;
import edu.ship.engr.shipsim.model.ReportObserverConnector;
import edu.ship.engr.shipsim.model.ReportObserver;
import edu.ship.engr.shipsim.model.reports.DoubloonChangeReport;
import edu.ship.engr.shipsim.model.reports.DoubloonPrizeListReport;
import edu.ship.engr.shipsim.view.screen.OverlayingScreen;

import java.util.List;

/**
 * Builds the ShopUI to be displayed when the "Shop" button is activated
 *
 * @author Zachary Semanco, Kevin Marek
 */
public class ShopUI extends OverlayingScreen implements ReportObserver
{
    private final float WIDTH = 600f;
    private final float HEIGHT = 400f;

    private ShopTable shopTable;

    /**
     * Constructor to set up the tables and button
     */
    public ShopUI()
    {
        super();
        setUpListener();
        shopTable = new ShopTable(false);
        shopTable.setFillParent(true);
        container.addActor(shopTable);
    }

    /**
     * TODO: Fill comments when code is written
     */
    private void setUpListener()
    {
        ReportObserverConnector cm = ReportObserverConnector.getSingleton();
        cm.registerObserver(this, DoubloonPrizeListReport.class);
        cm.registerObserver(this, DoubloonChangeReport.class);
    }

    /**
     * When the prize report is received, ask the ShopTable to add each item to the
     * UI
     *
     */
    @Override
    public void receiveReport(Report report)
    {
        if (report.getClass() == DoubloonPrizeListReport.class)
        {
            DoubloonPrizeListReport rep = (DoubloonPrizeListReport) report;
            List<DoubloonPrizeDTO> temp = rep.getPrizes();
            DoubloonPrizeDTO[] biggerTemp = new DoubloonPrizeDTO[temp.size()];
            for (int i = 0; i < temp.size(); i++)
            {
                biggerTemp[i] = temp.get(i);
            }
            shopTable.addShopItem(biggerTemp);
        }

        if (report.getClass() == DoubloonChangeReport.class)
        {
            DoubloonChangeReport rep = (DoubloonChangeReport) report;
            shopTable.updateDoubloons(rep.getDoubloons());
        }
    }

    /**
     * @see OverlayingScreen#getMyWidth()
     */
    @Override
    public float getMyWidth()
    {
        return WIDTH;
    }

    /**
     * @see OverlayingScreen#getMyHeight()
     */
    @Override
    public float getMyHeight()
    {
        return HEIGHT;

    }

    /**
     * @see OverlayingScreen#toggleVisibility()
     */
    @Override
    public void toggleVisibility()
    {
        if (isVisible())
        {
            this.addAction(Actions.hide());
        }
        else
        {
            this.addAction(Actions.show());
        }
    }
}
