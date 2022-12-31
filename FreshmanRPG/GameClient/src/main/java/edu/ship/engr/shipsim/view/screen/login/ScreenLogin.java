package edu.ship.engr.shipsim.view.screen.login;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import edu.ship.engr.shipsim.model.ClientModelFacade;
import edu.ship.engr.shipsim.model.CommandLogin;
import edu.ship.engr.shipsim.view.screen.ScreenBasic;
import edu.ship.engr.shipsim.view.screen.Screens;

import java.io.File;

/**
 * First screen the player sees to start playing.
 *
 * @author BrysonHair
 */
public class ScreenLogin extends ScreenBasic
{
    private Texture logo;
    private TextField loginField;
    private TextButton connectButton;

    private Table table;
    private Table instructionTable;

    private TextField pwField;

    private String flagMsg;

    /**
     * Create a login screen
     */
    public ScreenLogin()
    {
        flagMsg = "";
    }

    /**
     * Creates all elements of LoginScreen
     */
    private void initializeScreen()
    {
        Viewport v = new ExtendViewport(Screens.DEFAULT_RES[0], Screens.DEFAULT_RES[1]);
        stage = new Stage(v);

        File f = new File("stupid");
        String s = f.getAbsolutePath();
        final Skin skin = new Skin(Gdx.files.internal("ui-data/uiskin.json"));
        logo = new Texture("ui-data/GameLogo.jpg");

        connectButton = new TextButton("Connect", skin);

        Label nameLabel = new Label("Name:", skin);
        Label pwLabel = new Label("Password:", skin);

        Label instruction = new Label("Key Map", skin);
        instruction.setFontScale((float) 1.5);
        Label moveInstruction = new Label("Press Arrow Keys: Movement", skin);
        Label qInstruction = new Label("Press Q: Quest & Objective Screen", skin);
        Label hInstruction = new Label("Press H: High Score & Rank Screen", skin);
        Label enterInstruction = new Label("Press Enter: Activates/Deactivates Chat Window", skin);


        final Label connectingLabel = new Label("Connecting to Lobby...", skin);

        connectButtonListener(connectingLabel);

        // create login field
        loginField = new TextField("", skin);
        loginField.setMessageText("Player Name");
        stage.setKeyboardFocus(loginField);
        // create login field
        pwField = new TextField("", skin);
        pwField.setMessageText("Password");
        InputListener il = new InputListener()
        {
            @Override
            public boolean keyDown(InputEvent event, int keycode)
            {
                if (keycode == Keys.ENTER)
                {
                    performLogin(connectingLabel);
                }
                return super.keyDown(event, keycode);
            }
        };
        loginField.addListener(il);
        pwField.addListener(il);

        Label flagLabel = new Label(this.flagMsg, skin);
        initializeTableContents(nameLabel, pwLabel, connectingLabel, flagLabel);
        initializeInstructionTableContents(instruction, moveInstruction, qInstruction, hInstruction, enterInstruction);
        stage.addActor(table);
        stage.addActor(instructionTable);


        Gdx.input.setInputProcessor(stage);
    }

    /**
     * Creates a listener for the Connect Button
     *
     * @param connectingLabel connect button label
     */
    private void connectButtonListener(final Label connectingLabel)
    {
        connectButton.addListener(new InputListener()
        {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer,
                                     int button)
            {
                return performLogin(connectingLabel);
            }
        });
    }

    private boolean performLogin(final Label connectingLabel)
    {
        Gdx.app.log("Touched Connect button", "down");
        // Lock the button
        if (connectButton.isDisabled())
        {
            return false;
        }
        connectButton.setDisabled(true);
        if (loginField.getText().length() == 0)
        {
            Gdx.app.log("No name given", "NULL Player Name");

            // Set the text field to default name
            loginField.setText("Player-Name");
            loginField.setCursorPosition(loginField.getText().length());
            connectButton.setDisabled(false);
            return false;
        }
        else
        {
            // Send a message containing the player's name
            Gdx.app.log("Player's name", loginField.getText());
            connectButton.setDisabled(true);
            connectingLabel.setVisible(true);

            // Create the login command to allow the player to login
            CommandLogin lc = new CommandLogin(loginField.getText(), pwField
                    .getText());
            ClientModelFacade.getSingleton().queueCommand(lc);
            return true;
        }
    }

    /**
     * Create table and add its contents for LoginScreen
     *
     * @param connectingLabel "connecting . . ."
     * @param pwLabel         pw field's label
     * @param nameLabel       user name field's label
     * @param flagLabel       text for instructions or messages
     */
    private void initializeTableContents(Label nameLabel, Label pwLabel,
                                         Label connectingLabel, Label flagLabel)
    {
        table = new Table();
        table.setFillParent(true);

        table.row();
        table.add(new Image(logo)).colspan(4);

        table.row();
        table.add();
        table.add(nameLabel);
        table.add(loginField).fill();
        table.add();

        table.row();
        table.add();
        table.add(pwLabel);
        table.add(pwField).fill();
        table.add();

        table.row();
        table.add(connectButton).colspan(4).pad(35);

        table.row();
        connectingLabel.setVisible(false);
        table.add(connectingLabel).colspan(4);
        table.row();
        table.add(flagLabel).colspan(4);
    }

    private void initializeInstructionTableContents(Label instruction, Label moveInstruction,
                                                    Label qInstruction, Label hInstruction, Label enterInstruction)
    {
        instructionTable = new Table();
        instructionTable.setFillParent(true);
        instructionTable.bottom().right();

        instructionTable.row();
        instructionTable.add(instruction).colspan(2).top().center();

        instructionTable.row();
        instructionTable.add(moveInstruction).left();

        instructionTable.row();
        instructionTable.add(qInstruction).left();

        instructionTable.row();
        instructionTable.add(hInstruction).left();

        instructionTable.row();
        instructionTable.add(enterInstruction).left();

        instructionTable.debugTable();
    }

    /**
     * @see com.badlogic.gdx.Screen
     */
    @Override
    public void dispose()
    {

    }

    /**
     * @see com.badlogic.gdx.Screen
     */
    @Override
    public void hide()
    {

    }

    /**
     * @see com.badlogic.gdx.Screen
     */
    @Override
    public void pause()
    {

    }

    /**
     * Renders the login screen to the player
     */
    @Override
    public void render(float arg0)
    {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        stage.act();
        stage.draw();
        stage.setDebugAll(false);
    }

    /**
     * @see com.badlogic.gdx.Screen
     */
    @Override
    public void resize(int width, int height)
    {
        stage.getViewport().update(width, height, true);
        stage.act();
    }

    /**
     * @see com.badlogic.gdx.Screen
     */
    @Override
    public void resume()
    {

    }

    /**
     * @see com.badlogic.gdx.Screen
     */
    @Override
    public void show()
    {
        initializeScreen();

    }

    /**
     * Set an error message to show on the login screen
     *
     * @param msg message to show
     */
    public void showFlagMessage(String msg)
    {
        this.flagMsg = msg;
    }
}
