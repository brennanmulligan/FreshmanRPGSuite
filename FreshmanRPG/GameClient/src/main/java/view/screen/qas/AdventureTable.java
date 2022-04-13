package view.screen.qas;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import dataDTO.ClientPlayerAdventureStateDTO;
import datatypes.AdventureStateEnum;
import datatypes.QuestStateEnum;
import view.screen.OverlayingScreenTable;
import view.screen.SkinPicker;

/**
 * @author TJ Renninger and Ian Keefer
 *
 */
public class AdventureTable extends OverlayingScreenTable
{
	private String blue_hex = "00FFFF";

	/**
	 * Table that displays all of the adventures of a selected quest for the player
	 * @param scrollable Whether or not the the overlaying screen table is scrollable
	 */
	public AdventureTable(boolean scrollable)
	{
		super(scrollable);
	}

	/**
	 * Updates the adventure table when a new quest is selected.
	 * @param questDesc The description of the quest
	 * @param expire The expiration date of the quest
	 * @param adventureList The list of adventures the quest has
	 */
	public void updateAdventures(String questDesc, String expire, ArrayList<ClientPlayerAdventureStateDTO> adventureList)
	{
		container.clear();
		Label l = new Label(questDesc + "\nExpires: " + expire + "\n\nAdventures:\n", SkinPicker.getSkinPicker().getCrewSkin());
		l.setWrap(true);
		container.add(l).left().top().width(container.getWidth() - getPadding() * 4f);        //Width is used to tell the label when to wrap its text.
		container.row();
		for (ClientPlayerAdventureStateDTO cpa : adventureList)
		{
			l = createAdventureLabel(cpa);
			container.add(l).left().top().width(container.getWidth() - getPadding() * 4f);    //Width is used to tell the label when to wrap its text.
			container.row();
		}
	}

	/**
	 * Creates the label for a given adventure. The colour of the label is determined by that State of the adventure.
	 * Green: Complete
	 * Red: Not Started
	 * @param cpa  The adventure to make the label for
	 * @return The created adventure label
	 * We made need to refactor this into its own class depending on how fancy we want the labels.
	 */
	private Label createAdventureLabel(ClientPlayerAdventureStateDTO cpa)
	{
		Label l;
		if (cpa.isRealLifeAdventure())
		{
			l = new Label("[P] " + cpa.getAdventureDescription(), SkinPicker.getSkinPicker().getCrewSkin());
		}
		else
		{
			l = new Label(cpa.getAdventureDescription(), SkinPicker.getSkinPicker().getCrewSkin());
		}
		l.setWrap(true);
		if (cpa.getAdventureState() == AdventureStateEnum.COMPLETED)
		{
			l.setColor(Color.GREEN);
		}
		else if (cpa.getQuestState() == QuestStateEnum.EXPIRED)
		{
			l.setColor(Color.BLACK);
		}
		else
		{
			l.setColor(Color.valueOf(blue_hex));
		}
		return l;
	}
}
