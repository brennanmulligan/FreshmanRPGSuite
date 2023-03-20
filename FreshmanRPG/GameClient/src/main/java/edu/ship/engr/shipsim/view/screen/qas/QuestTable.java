package edu.ship.engr.shipsim.view.screen.qas;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import edu.ship.engr.shipsim.dataDTO.ClientPlayerQuestStateDTO;
import edu.ship.engr.shipsim.datatypes.QuestStateEnum;
import edu.ship.engr.shipsim.view.screen.OverlayingScreenTable;
import edu.ship.engr.shipsim.view.screen.SkinPicker;

import java.util.List;

/**
 * @author TJ Renninger and Ian Keefer
 */
public class QuestTable extends OverlayingScreenTable
{
    private ObjectiveTable objectiveTable;
    private String blue_hex = "00FFFF";

    /**
     * @param questList  The list of quest that the player has
     * @param scrollable Whether or not the the overlaying screen table is scrollable
     */
    public QuestTable(List<ClientPlayerQuestStateDTO> questList, boolean scrollable)
    {
        super(scrollable);    //Null is passed in because the widget has not been created yet.
        updateQuests(questList);
    }

    /**
     * Sets the objective table so that the quest table is able to send the objective table
     * which quest to display objectives for.
     *
     * @param table The table that hold all of the objectives
     */
    public void setObjectiveTable(ObjectiveTable table)
    {
        objectiveTable = table;
    }

    /**
     * Creates a label for a quest to be displayed in the quest table.
     * The label will be clickable
     *
     * @param questName
     * @return
     */
    private Label createQuestLabel(final ClientPlayerQuestStateDTO cpq)
    {
        String labelString = cpq.getQuestTitle();
        Label l = new Label(labelString, SkinPicker.getSkinPicker().getCrewSkin());
        switch (cpq.getQuestState())
        {
            case TRIGGERED:
                l.setColor(Color.valueOf(blue_hex));
                break;
            case FULFILLED:
                l.setColor(Color.YELLOW);
                break;
            case COMPLETED:
                l.setColor(Color.GREEN);
                break;
            case EXPIRED:
                l.setColor(Color.DARK_GRAY);
                break;
            default:
                l.setColor(Color.WHITE);
                break;
        }
            ClickListener clickListener = new ClickListener()
            {
                @Override
                public void clicked(InputEvent event, float x, float y)
                {
                    objectiveTable.updateObjectives(cpq.getQuestDescription(),
                            cpq.getExpireDate().toString(),
                            cpq.getObjectiveList());
                    super.clicked(event, x, y);
                }
            };
            l.addListener(clickListener);
        return l;
    }

    /**
     * Update the quest table to the current quest that the player has.
     *
     * @param questList The list of quest that the player has
     */
    public void updateQuests(List<ClientPlayerQuestStateDTO> questList)
    {
        container.clear();
        for (ClientPlayerQuestStateDTO cpq : questList)
        {
            if(shouldDisplay(cpq))
            {
                Label l = createQuestLabel(cpq);
                container.add(l).top().row();
            }
        }
    }

    /**
     * Method that checks logic for whether a quest should be visible/displayed on the client.
     * @param cpqs the DTO containing the relevant information.
     * @return true to display, false otherwise.
     */
    public boolean shouldDisplay(ClientPlayerQuestStateDTO cpqs)
    {
        if (cpqs.isEasterEgg() && !(cpqs.getQuestState() == QuestStateEnum.COMPLETED))
        {
            return false;
        }

        return true;
    }
}
