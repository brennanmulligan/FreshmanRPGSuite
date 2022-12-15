package edu.ship.engr.shipsim.view.screen.shop;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import edu.ship.engr.shipsim.dataDTO.DoubloonPrizeDTO;
import edu.ship.engr.shipsim.model.ClientModelFacade;
import edu.ship.engr.shipsim.model.ClientPlayerManager;
import edu.ship.engr.shipsim.model.CommandItemPurchased;
import edu.ship.engr.shipsim.view.screen.OverlayingScreenTable;
import edu.ship.engr.shipsim.view.screen.SkinPicker;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.util.HashMap;
import java.util.Map.Entry;

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
    private int doubloons;
    private HashMap<Integer, TextButton> buttons = new HashMap<>();
    private List<DoubloonPrizeDTO> items;

    /**
     * Constructor for the ShopTable
     *
     * @param scrollable :: Defines if the table is scrollable
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

        CharSequence name = "Doubloons: ";
        header = new Label(name + Integer.toString(doubloons), skin);

        grid.add(header);
        grid.row();
        grid.add(listPane).expand().fillX().height(350f).width(550f).colspan(9);

        grid.bottom();
        container.add(grid);
    }

    /**
     * Adds an item to the shop page
     *
     * @param dto -
     */
    public void addShopItem(DoubloonPrizeDTO[] dto)
    {
        items.setItems(dto);
        TextButton buyButton = null;
        for (int i = 0; i < items.getItems().size; i++)
        {
            buyButton = new TextButton("Buy", skin);
            if (dto[i].getCost() <= doubloons)
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
                    //ensures that the player has enough doubloons to make the purchase
                    if (doubloons >= dto[k].getCost())
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
                            // Deduct the doubloons and print the pdf receipt
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
     * Called in the ShopUI class to update this player's doubloon value. Also
     * updates the color of the buy buttons if the ability to buy has changed
     *
     * @param doubloons the new amount of doubloons a player has
     */
    public void updateDoubloons(int doubloons)
    {
        this.doubloons = doubloons;
        header.setText("Doubloons: " + Integer.toString(this.doubloons));
        // Change the color of each button if our ability to afford it has changed
        // either way
        if (!buttons.isEmpty())
        {
            for (Entry<Integer, TextButton> button : buttons.entrySet())
            {
                if (button.getKey() <= doubloons)
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
     * creating a pdf file for the quests and objectives.
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
