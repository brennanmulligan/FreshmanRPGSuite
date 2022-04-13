package view.screen.highscore;

import java.util.ArrayList;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import datatypes.PlayerScoreRecord;
import model.ClientModelFacade;
import model.CommandHighScoreRequest;
import model.QualifiedObservableConnector;
import model.QualifiedObservableReport;
import model.QualifiedObserver;
import model.reports.ClientKeyInputSentReport;
import model.reports.HighScoreResponseReport;
import view.screen.OverlayingScreen;

/**
 * @author ck4124, Scott
 * @fixedby Ian Keefer and TJ Renninger
 */
public class HighScoreUI extends OverlayingScreen implements QualifiedObserver
{
	private final float WIDTH = 200f;
	private final float HEIGHT = 300f;
	private HighScoreTable highScoreTable;

	/**
	 * sets up UI for top 10 experience points
	 */
	public HighScoreUI()
	{
		super();
		setUpListening();
		highScoreTable = new HighScoreTable(true);
		highScoreTable.setFillParent(true);
		container.addActor(highScoreTable);
	}

	/**
	 * Sets up the QualifiedObserver for HighScoreResponseReport
	 */
	public void setUpListening()
	{
		QualifiedObservableConnector cm = QualifiedObservableConnector.getSingleton();
		cm.registerObserver(this, HighScoreResponseReport.class);
		cm.registerObserver(this, ClientKeyInputSentReport.class);
	}

	/**
	 * Toggle the invisibility of the highscore list
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
			CommandHighScoreRequest cmd = new CommandHighScoreRequest();
			ClientModelFacade.getSingleton().queueCommand(cmd);
			this.addAction(Actions.show());
		}
	}

	/**
	 * @see model.QualifiedObserver#receiveReport(model.QualifiedObservableReport)
	 */
	@Override
	public void receiveReport(QualifiedObservableReport report)
	{
		if (report.getClass().equals(HighScoreResponseReport.class))
		{
			HighScoreResponseReport rep = (HighScoreResponseReport) report;
			ArrayList<PlayerScoreRecord> list = rep.getScoreList();
			highScoreTable.updateHighScores(list);
		}
		else if (report.getClass().equals(ClientKeyInputSentReport.class))
		{
			ClientKeyInputSentReport r = (ClientKeyInputSentReport) report;
			// Check for H
			if (r.getInput().equals(Keys.toString(Keys.H)))
			{
				this.toggleVisibility();
			}
		}
	}

	/**
	 * @see view.screen.OverlayingScreen#getWidth()
	 */
	@Override
	public float getMyWidth()
	{
		return WIDTH;
	}

	/**
	 * @see view.screen.OverlayingScreen#getHeight()
	 */
	@Override
	public float getMyHeight()
	{
		return HEIGHT;
	}
}
