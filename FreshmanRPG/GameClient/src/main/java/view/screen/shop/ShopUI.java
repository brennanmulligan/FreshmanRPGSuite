package view.screen.shop;

import java.util.List;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import dataDTO.KnowledgePointPrizeDTO;
import model.QualifiedObservableConnector;
import model.QualifiedObservableReport;
import model.QualifiedObserver;
import model.reports.KnowledgePointPrizeListReport;
import model.reports.KnowledgePointsChangeReport;
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
		cm.registerObserver(this, KnowledgePointPrizeListReport.class);
		cm.registerObserver(this, KnowledgePointsChangeReport.class);
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
		if (report.getClass() == KnowledgePointPrizeListReport.class)
		{
			KnowledgePointPrizeListReport rep = (KnowledgePointPrizeListReport) report;
			List<KnowledgePointPrizeDTO> temp = rep.getPrizes();
			KnowledgePointPrizeDTO[] biggerTemp = new KnowledgePointPrizeDTO[temp.size()];
			for (int i = 0; i < temp.size(); i++)
			{
				biggerTemp[i] = temp.get(i);
			}
			shopTable.addShopItem(biggerTemp);
		}

		if (report.getClass() == KnowledgePointsChangeReport.class)
		{
			KnowledgePointsChangeReport rep = (KnowledgePointsChangeReport) report;
			shopTable.updateKnowledgePoints(rep.getKnowledgePoints());
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
