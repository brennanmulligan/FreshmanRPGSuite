package edu.ship.engr.shipsim.view.screen.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import edu.ship.engr.shipsim.model.ReportObserver;
import edu.ship.engr.shipsim.model.ReportObserverConnector;
import edu.ship.engr.shipsim.model.Report;
import edu.ship.engr.shipsim.model.reports.DoubloonChangeReport;
import edu.ship.engr.shipsim.model.reports.ExperiencePointsChangeReport;

/**
 * @author ck4124
 * Displays ThisClientPlayer's experiencePoints and progress for the current level
 */
public class ExperienceDisplay extends Group implements ReportObserver
{
    private int experiencePoints;
    private int numPointsLvlRequires;
    private String playersLevel;

    private Table experienceDisplay;

    private final Skin skin = new Skin(Gdx.files.internal("ui-data/uiskin.json"));

    /**
     * constructor to create the player's experience points display
     */
    public ExperienceDisplay()
    {
        this.show();
        setUpListening();
    }


    private void setUpListening()
    {
        ReportObserverConnector cm = ReportObserverConnector.getSingleton();
        cm.registerObserver(this, DoubloonChangeReport.class);
    }


    private void show()
    {
        experienceDisplay = new Table();
        experienceDisplay.setFillParent(true);
        this.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        experienceDisplay.top().right();
        experienceDisplay.add(new Label("Experience Display goes here", skin)).row();
        this.addActor(experienceDisplay);
    }


    /**
     * @return experiencePoints
     */
    public int getExperiencePoints()
    {
        return experiencePoints;
    }

    /**
     * Set player's experience points
     *
     * @param experiencePoints player's experience points
     */
    public void setExperiencePoints(int experiencePoints)
    {
        this.experiencePoints = experiencePoints;
    }

    /**
     * @return numPointsLvlRequries
     */
    public int getNumPointsLvlRequries()
    {
        return numPointsLvlRequires;
    }

    /**
     * Set number of points level requires
     *
     * @param numPointsLvlRequries player's number of points level requires
     */
    public void setNumPointsLvlRequries(int numPointsLvlRequries)
    {
        this.numPointsLvlRequires = numPointsLvlRequries;
    }

    /**
     * Get player's level
     *
     * @return playersLevel
     */
    public String getPlayersLevel()
    {
        return playersLevel;
    }

    /**
     * Set the player's level
     *
     * @param playersLevel this player's level
     */
    public void setPlayersLevel(String playersLevel)
    {
        this.playersLevel = playersLevel;
    }


    /**
     * @see ReportObserver#receiveReport(Report)
     */
    @Override
    public void receiveReport(Report report)
    {
        if (report.getClass().equals(ExperiencePointsChangeReport.class))
        {
            ExperiencePointsChangeReport r = (ExperiencePointsChangeReport) report;
            setExperiencePoints(r.getExperiencePoints());
            setNumPointsLvlRequries(r.getLevelRecord().getLevelUpPoints());
            setPlayersLevel(r.getLevelRecord().getDescription());

            updateExperienceDisplay();
        }

    }

    /**
     * clears the experiencePoints UI display and sets the new values from the report
     */
    private void updateExperienceDisplay()
    {
        experienceDisplay.clear();
        Label xp_display = new Label(getPlayersLevel() + " " +
                getExperiencePoints() + " / " + getNumPointsLvlRequries(), skin);
        xp_display.setFontScale((float) 1.15);
        experienceDisplay.add(xp_display).row();
    }

}
