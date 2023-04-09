package edu.ship.engr.shipsim.view.screen.qas;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import edu.ship.engr.shipsim.dataDTO.ClientPlayerObjectiveStateDTO;
import edu.ship.engr.shipsim.datatypes.ObjectiveStateEnum;
import edu.ship.engr.shipsim.datatypes.QuestStateEnum;
import edu.ship.engr.shipsim.view.screen.OverlayingScreenTable;
import edu.ship.engr.shipsim.view.screen.SkinPicker;

import java.util.List;

/**
 * @author TJ Renninger and Ian Keefer
 */
public class ObjectiveTable extends OverlayingScreenTable
{
    private String blue_hex = "00FFFF";

    /**
     * Table that displays all of the objectives of a selected quest for the player
     *
     * @param scrollable Whether or not the the overlaying screen table is scrollable
     */
    public ObjectiveTable(boolean scrollable)
    {
        super(scrollable);
    }

    /**
     * Updates the objective table when a new quest is selected.
     *
     * @param questDesc     The description of the quest
     * @param expire        The expiration date of the quest
     * @param objectiveList The list of objectives the quest has
     */
    public void updateObjectives(String questDesc, String expire, List<ClientPlayerObjectiveStateDTO> objectiveList)
    {
        container.clear();
        Label l = new Label(questDesc + "\nExpires: " + expire + "\n\nObjective:\n", SkinPicker.getSkinPicker().getCrewSkin());
        l.setWrap(true);
        container.add(l).left().top().width(container.getWidth() - getPadding() * 4f);        //Width is used to tell the label when to wrap its text.
        container.row();
        for (ClientPlayerObjectiveStateDTO cpa : objectiveList)
        {
            l = createObjectiveLabel(cpa);
            container.add(l).left().top().width(container.getWidth() - getPadding() * 4f);    //Width is used to tell the label when to wrap its text.
            container.row();
        }
    }

    /**
     * Creates the label for a given objective. The colour of the label is determined by that State of the objective.
     * Green: Complete
     * Red: Not Started
     *
     * @param objective The objective to make the label for
     * @return The created objective label
     * We made need to refactor this into its own class depending on how fancy we want the labels.
     */
    private Label createObjectiveLabel(ClientPlayerObjectiveStateDTO objective)
    {
        Label l;
        if (objective.isRealLifeObjective())
        {
            l = new Label("[P] " + objective.getObjectiveDescription(), SkinPicker.getSkinPicker().getCrewSkin());
        }
        else
        {
            l = new Label(objective.getObjectiveDescription(), SkinPicker.getSkinPicker().getCrewSkin());
        }
        l.setWrap(true);
        if (objective.getObjectiveState() == ObjectiveStateEnum.COMPLETED)
        {
            l.setColor(Color.GREEN);
        }
        else if (objective.getQuestState() == QuestStateEnum.EXPIRED)
        {
            l.setColor(Color.BLACK);
        }
        else if (objective.getObjectiveState() == ObjectiveStateEnum.LATE)
        {
            l.setColor(Color.RED);
        }
        else
        {
            l.setColor(Color.valueOf(blue_hex));
        }
        return l;
    }
}
