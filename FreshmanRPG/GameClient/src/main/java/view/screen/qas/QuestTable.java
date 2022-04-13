package view.screen.qas;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import dataDTO.ClientPlayerQuestStateDTO;
import view.screen.OverlayingScreenTable;
import view.screen.SkinPicker;

/**
 * @author TJ Renninger and Ian Keefer
 *
 */
public class QuestTable extends OverlayingScreenTable
{
	private AdventureTable adventureTable;
	private String blue_hex = "00FFFF";

	/**
	 * @param questList The list of quest that the player has
	 * @param scrollable Whether or not the the overlaying screen table is scrollable
	 */
	public QuestTable(ArrayList<ClientPlayerQuestStateDTO> questList, boolean scrollable)
	{
		super(scrollable);    //Null is passed in because the widget has not been created yet.
		updateQuests(questList);
	}

	/**
	 * Sets the adventure table so that the quest table is able to send the adventure table
	 * which quest to display adventures for.
	 * @param at The table that hold all of the adventures
	 */
	public void setAdventureTable(AdventureTable at)
	{
		adventureTable = at;
	}

	/**
	 * Creates a label for a quest to be displayed in the quest table.
	 * The label will be clickable
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
				adventureTable.updateAdventures(cpq.getQuestDescription(), cpq.getExpireDate().toString(), cpq.getAdventureList());
				super.clicked(event, x, y);
			}
		};
		l.addListener(clickListener);
		return l;
	}

	/**
	 * Update the quest table to the current quest that the player has.
	 * @param questList The list of quest that the player has
	 */
	public void updateQuests(ArrayList<ClientPlayerQuestStateDTO> questList)
	{
		container.clear();
		for (ClientPlayerQuestStateDTO cpq : questList)
		{
			Label l = createQuestLabel(cpq);
			container.add(l).top().row();
		}
	}
}
