package edu.ship.engr.shipsim.view.screen.terminal;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.ObjectFloatMap;
import edu.ship.engr.shipsim.model.ClientModelFacade;
import edu.ship.engr.shipsim.model.ClientPlayerManager;
import edu.ship.engr.shipsim.model.CommandSendTerminalText;
import edu.ship.engr.shipsim.model.terminal.CommandTerminalTextDate;
import edu.ship.engr.shipsim.model.terminal.TerminalCommand;
import edu.ship.engr.shipsim.model.terminal.TerminalManager;
import edu.ship.engr.shipsim.view.screen.OverlayingScreenTable;
import edu.ship.engr.shipsim.view.screen.SkinPicker;

import java.util.ArrayList;

/**
 * @author as3871
 */
public class TerminalTable extends OverlayingScreenTable
{

    /**
     * ui stuff
     */
    private TextField entryField;
    private ScrollPane listPane;
    private Table terminalHistoryView;
    private Table grid;
    private Skin skin;
    private TextField.TextFieldStyle tfs;
    private ArrayList<String> terminalHistory = new ArrayList<>();
    private ObjectFloatMap<Label> newLabels;


    /**
     * @param scrollable defines if the table is scrollable
     */
    public TerminalTable(boolean scrollable)
    {
        super(scrollable);
        setupUI();
    }

    /**
     * @param terminalResult a string resulting from the command sent from the terminal line
     */
    public void addTerminalResponse(String terminalResult)
    {
        boolean scrollToBottom = !(listPane.getScrollPercentY() <= 0.9f);

        terminalHistory.add(terminalResult);
        Label l = new Label(terminalResult, skin);

        l.setWrap(true);
        l.setColor(Color.GREEN);
        newLabels.put(l, 0f);

        terminalHistoryView.top().add(l).expandX().fillX();
        if (scrollToBottom)
        {
            listPane.layout();
            listPane.setScrollPercentY(1f);
        }
        terminalHistoryView.row();
        listPane.setScrollPercentX(0f);
    }

    /**
     * Sets up the table elements including the Entry field and the message area
     */
    private void setupUI()
    {
        // if the UI has already been setup, remove the old grid from the stage.
        if (grid != null)
        {
            removeActor(grid);
        }
        newLabels = new ObjectFloatMap<>();
        skin = SkinPicker.getSkinPicker().getTerminalSkin();
        tfs = skin.get(TextField.TextFieldStyle.class);

        grid = new Table();
        grid.setWidth(600f);
        grid.setHeight(475f);

        // create text box for typing messages
        entryField = new TextField("", skin);
        // create the message button
        tfs.fontColor = Color.GREEN;
        entryField.setStyle(tfs);

        // create chat log area
        terminalHistoryView = new Table();
        listPane = new ScrollPane(terminalHistoryView, skin);

        grid.add(listPane).expandX().fillX().height(412.5f).width(567.5f).colspan(9);
        grid.row();
        grid.add(entryField).expandX().fillX().height(32f).colspan(9);

        grid.bottom();
        container.add(grid);

        entryField.addListener(new InputListener()
        {
            @Override
            public boolean keyDown(InputEvent event, int keycode)
            {
                if (keycode == Keys.ENTER)
                {
                    sendMessage();
                    return true;
                }
                return super.keyDown(event, keycode);
            }
        });
    }

    private void redrawTerminalHistoryView()
    {
        terminalHistoryView.clear();
        for (String cm : terminalHistory)
        {
            Label l = new Label(cm, skin);
            l.setWrap(true);
            l.setColor(Color.GREEN);

            terminalHistoryView.top().add(l).expandX().fillX();
            terminalHistoryView.row();

        }
        listPane.setScrollPercentY(1f);
    }


    /**
     * Retrieves the appropriate command and executes.
     */
    private void sendMessage()
    {
        String command = entryField.getText().trim();
        if (command.equals("clear"))
        {
            terminalHistory.clear();
            redrawTerminalHistoryView();
            entryField.setText("");
        }
        else if (command.equals("date"))
        {
            addTerminalResponse("$~: " + command);
            CommandTerminalTextDate cmd = new CommandTerminalTextDate();
            terminalHistory.add(cmd.execute(ClientPlayerManager.getSingleton().getThisClientsPlayer().getID(), new String[]{""}));
            redrawTerminalHistoryView();
            entryField.setText("");


        }
        else
        {
            addTerminalResponse("$~: " + command);
            entryField.setText("");
            TerminalCommand tcmd = TerminalManager.getSingleton().getTerminalCommandObject(command);
            if (tcmd != null)
            {
                tcmd.execute(ClientPlayerManager.getSingleton().getThisClientsPlayer().getID(), new String[]{""});
            }
            else
            {
                CommandSendTerminalText cmd = new CommandSendTerminalText(command);
                ClientModelFacade.getSingleton().queueCommand(cmd);
            }
        }
    }
}
