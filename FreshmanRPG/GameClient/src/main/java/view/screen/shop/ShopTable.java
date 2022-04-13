package view.screen.shop;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import dataDTO.KnowledgePointPrizeDTO;
import model.ClientModelFacade;
import model.ClientPlayerManager;
import model.CommandItemPurchased;
import view.screen.OverlayingScreenTable;
import view.screen.SkinPicker;

/**
 * Class to build the Table functionality for the ShopUI class
 *
 * @author Zachary Semanco, Kevin Marek
 */
public class ShopTable extends OverlayingScreenTable
{
	private Skin skin;
	private Table grid;
	private Table itemTable;
	private ScrollPane listPane;
	private Label header;
	private int knowledgePoints;
	private HashMap<Integer, TextButton> buttons = new HashMap<>();
	private List<KnowledgePointPrizeDTO> items;

	/**
	 * Constructor for the ShopTable
	 *
	 * @param scrollable
	 *            :: Defines if the table is scrollable
	 */
	public ShopTable(boolean scrollable)
	{
		super(scrollable);
		setupUI();
	}

	/**
	 * Sets the color, size, and adds the grid to the table view
	 */
	public void setupUI()
	{
		skin = SkinPicker.getSkinPicker().getDefaultSkin();
		grid = new Table();
		grid.setWidth(850f);
		grid.setHeight(475f);
		items = new List<>(skin);
		itemTable = new Table(skin);
		listPane = new ScrollPane(itemTable, skin);
		listPane.setScrollingDisabled(true, false);
		listPane.setFadeScrollBars(false);

		CharSequence name = "Knowledge Points: ";
		header = new Label(name + Integer.toString(knowledgePoints), skin);

		grid.add(header);
		grid.row();
		grid.add(listPane).expand().fillX().height(350f).width(550f).colspan(9);

		grid.bottom();
		container.add(grid);
	}

	/**
	 * Adds an item to the shop page
	 *
	 * @param dto
	 *            -
	 */
	public void addShopItem(KnowledgePointPrizeDTO[] dto)
	{
		items.setItems(dto);
		TextButton buyButton = null;
		for (int i = 0; i < items.getItems().size; i++)
		{
			buyButton = new TextButton("Buy", skin);
			if (dto[i].getCost() <= knowledgePoints)
			{
				buyButton.setColor(Color.CYAN); //Cyan is a colorblind friendly color
			}
			else
			{
				buyButton.setColor(Color.GRAY);
			}
			int k = i;
			buyButton.addListener(new ClickListener()
			{
				@Override
				public void clicked(InputEvent event, float x, float y)
				{
					//ensures that the player has enough knowledge points to make the purchase
					if (knowledgePoints >= dto[k].getCost())
					{
						JFileChooser fileChooser = openFileChooser();
						int option = fileChooser.showOpenDialog(null);
						if (option == JFileChooser.APPROVE_OPTION)
						{
							File file = fileChooser.getSelectedFile();
							String path = file.getAbsolutePath();
							if (!path.toLowerCase().endsWith(".pdf"))
							{
								path += ".pdf";
							}
							// Deduct the knowledge points and print the pdf receipt
							CommandItemPurchased cmd = new CommandItemPurchased(
									ClientPlayerManager.getSingleton().getThisClientsPlayer().getID(), dto[k].getCost(),
									path, dto[k].getName());
							ClientModelFacade.getSingleton().queueCommand(cmd);
						}
					}
				}
			});
			// Add button to the list of buttons so we can retrieve it later
			buttons.put(dto[k].getCost(), buyButton);
			itemTable.add(buyButton).width(80).pad(10);
			Label wrapAttempt = new Label(items.getItems().get(i).toString(), skin);
			wrapAttempt.setWrap(true);
			itemTable.add(wrapAttempt).width(400).pad(20);

			itemTable.row();
		}
	}

	/**
	 * Called in the ShopUI class to update this player's knowledgePoint value. Also
	 * updates the color of the buy buttons if the ability to buy has changed
	 *
	 * @param knowledgePoints
	 *            - the new amount of knowledge points a player has
	 */
	public void updateKnowledgePoints(int knowledgePoints)
	{
		this.knowledgePoints = knowledgePoints;
		header.setText("Knowledge Points: " + Integer.toString(this.knowledgePoints));
		// Change the color of each button if our ability to afford it has changed
		// either way
		if (!buttons.isEmpty())
		{
			for (Entry<Integer, TextButton> button : buttons.entrySet())
			{
				if (button.getKey() <= knowledgePoints)
				{
					button.getValue().setColor(Color.CYAN); //cyan is a colorblind friendly color
				}
				else
				{
					button.getValue().setColor(Color.GRAY);
				}
			}
		}
	}


	/**
	 * Creates a file chooser with the filters and parameters that we need for
	 * creating a pdf file for the quests and adventures.
	 *
	 * @return The file chooser that is created
	 */
	private JFileChooser openFileChooser()
	{
		JFileChooser fileChooser = new JFileChooser();
		// Sets up a new file chooser to
		fileChooser.setDialogTitle("Print Receipt");
		// Sets the tile of the of the window that pops up to Print PDF
		fileChooser.setApproveButtonText("Save");
		// Set the button text to save instead of open
		fileChooser.setApproveButtonToolTipText("Save selected file");
		// Hovering over the save button will display this text
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		// Makes it so that directories cannot be chosen
		fileChooser.setCurrentDirectory(new File("~"));
		// Sets the default save directory to the home directory
		fileChooser.setFileFilter(new FileFilter()
				// Filers the type of files allowed
		{
			@Override
			public String getDescription()
			{
				return "PDF File (*.pdf)";
				// Only PDF files are allowed
			} // This does not prevent the use of another file extension though

			@Override
			public boolean accept(File f)
			{
				return true;
			}
		});
		return fileChooser;
	}
}
