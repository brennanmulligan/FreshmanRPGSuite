package view.screen.shop;

import java.util.List;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import dataDTO.DoubloonPrizeDTO;
import model.QualifiedObservableConnector;
import model.QualifiedObservableReport;
import model.QualifiedObserver;
import model.reports.DoubloonPrizeListReport;
import model.reports.DoubloonChangeReport;
import view.screen.OverlayingScreen;

/**
 * Builds the ShopUI to be displayed when the "Shop" button is activated
 *
 * @author Zachary Semanco, Kevin Marek
 */
public class ShopUI extends OverlayingScreen implements QualifiedObserver
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
		QualifiedObservableConnector cm = QualifiedObservableConnector.getSingleton();
		cm.registerObserver(this, DoubloonPrizeListReport.class);
		cm.registerObserver(this, DoubloonChangeReport.class);
	}

	/**
	 * When the prize report is received, ask the ShopTable to add each item to the
	 * UI
	 *
	 * @See view.screen.shop.ShopTable#addShopItem(String, String, int)
	 */
	@Override
	public void receiveReport(QualifiedObservableReport report)
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
	 * @see view.screen.OverlayingScreen#getMyWidth()
	 */
	@Override
	public float getMyWidth()
	{
		return WIDTH;
	}

	/**
	 * @see view.screen.OverlayingScreen#getMyHeight()
	 */
	@Override
	public float getMyHeight()
	{
		return HEIGHT;

	}

	/**
	 * @see view.screen.OverlayingScreen#toggleVisibility()
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
