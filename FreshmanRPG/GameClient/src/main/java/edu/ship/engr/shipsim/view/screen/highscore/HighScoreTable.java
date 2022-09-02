package edu.ship.engr.shipsim.view.screen.highscore;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import edu.ship.engr.shipsim.datatypes.PlayerScoreRecord;
import edu.ship.engr.shipsim.view.screen.OverlayingScreenTable;
import edu.ship.engr.shipsim.view.screen.SkinPicker;

import java.util.ArrayList;

/**
 * @author TJ Renninger and Ian Keefer
 */
public class HighScoreTable extends OverlayingScreenTable
{


    /**
     * @param scrollable Whether or not the the overlaying screen table is scrollable
     */
    public HighScoreTable(boolean scrollable)
    {
        super(scrollable);
    }

    /**
     * @param players that are on the highscore list
     */
    public void updateHighScores(ArrayList<PlayerScoreRecord> players)
    {
        container.clear();
        int i = 0;
        for (PlayerScoreRecord p : players)
        {
            addPlayerLabel(p, i + 1);
            i++;
        }
    }

    private void addPlayerLabel(PlayerScoreRecord p, int playerRank)
    {
        final float spaceBetweenNumberAndPlayer = 15f;
        final float spaceBetweenPlayerAndXp = 10f;
        Label rank = new Label(playerRank + ".", SkinPicker.getSkinPicker().getCrewSkin());
        container.add(rank).left().padRight(spaceBetweenNumberAndPlayer);
        Label player = new Label(p.getPlayerName(), SkinPicker.getSkinPicker().getCrewSkin());
        container.add(player).left().padRight(spaceBetweenPlayerAndXp);
        Label xp = new Label("" + p.getExperiencePoints(), SkinPicker.getSkinPicker().getCrewSkin());
        xp.setColor(Color.GREEN);
        container.add(xp).right().row();
    }
}
