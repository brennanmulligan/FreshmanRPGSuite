package edu.ship.engr.shipsim.view.screen.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.IntMap.Entry;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import edu.ship.engr.shipsim.dataDTO.VanityDTO;
import edu.ship.engr.shipsim.datatypes.Position;
import edu.ship.engr.shipsim.view.player.*;
import edu.ship.engr.shipsim.view.screen.ClientNotificationManager;
import edu.ship.engr.shipsim.view.screen.OverlayingScreen;
import edu.ship.engr.shipsim.view.screen.ScreenBasic;
import edu.ship.engr.shipsim.view.screen.SkinPicker;
import edu.ship.engr.shipsim.view.screen.chat.PopUpChatUI;
import edu.ship.engr.shipsim.view.screen.closet.ClosetUI;
import edu.ship.engr.shipsim.view.screen.clothingShop.ClothingShopUI;
import edu.ship.engr.shipsim.view.screen.friends.FriendsUI;
import edu.ship.engr.shipsim.view.screen.highscore.HighScoreUI;
import edu.ship.engr.shipsim.view.screen.menu.MenuUI;
import edu.ship.engr.shipsim.view.screen.notification.AlertContainer;
import edu.ship.engr.shipsim.view.screen.qas.QuestUI;
import edu.ship.engr.shipsim.view.screen.shop.ShopUI;
import edu.ship.engr.shipsim.view.screen.terminal.TerminalUI;

import java.util.ArrayList;
import java.util.List;

import static edu.ship.engr.shipsim.view.screen.Screens.DEFAULT_RES;

/**
 * A basic screen that, for now, just displays the map
 *
 * @author Merlin
 */
public class ScreenMap extends ScreenBasic
{
    InputMultiplexer multiplexer;
    OrthogonalTiledMapRenderer mapRenderer;
    PlayerSprite mySprite;

    PlayerSpriteFactory playerFactory;
    Stage worldStage;
    IntMap<PlayerSprite> characters;

    // holds characters that need to be added until the map is loaded
    IntMap<Position> characterQueue;
    // holds list of characters that need to be removed after the render cycle
    // is complete
    IntArray characterDequeue;


    private OrthographicCamera camera;
    private final float unitScale;
    private final ScreenMapInput mapInput;
    private ScreenMapKeyInputSentProcessor keyInputProcessor;

    private MenuUI menuArea;
    private PopUpChatUI popUpChatUI;
    private ClosetUI closetUI;
    private ShopUI shopUI;
    private ClothingShopUI clothingShopUI;

    private final ArrayList<OverlayingScreen> overlayingScreens;

    // tile size that we will be moving in according to the collision masking
    // tileset
    private final Vector2 tileSize;
    private final Vector2 mapPixelSize;
    private final Vector2 mapSize;

    private int[] bgLayers, fgLayers;
    private Color clearColor;

    boolean loading;

    // these frame buffers are used to so we can apply OpenGL Gaussian blur to
    // the UI, giving
    // it an Aero Glass like appearance based on UI alpha levels
    private FrameBuffer mapBuffer;
    private Texture mapTexture;
    private FrameBuffer uiBuffer;
    private Texture uiTexture;
    private ShaderProgram aeroGlass;
    private OrthographicCamera defaultCamera;
    private boolean bufferSet;
    // batch specifically used for post shader effects
    private SpriteBatch blurBatch;
    private Group loadingLayer;
    private OrthographicCamera worldCamera;

    /**
     *
     */
    public ScreenMap()
    {
        overlayingScreens = new ArrayList<>();

        unitScale = 1f;
        characters = new IntMap<>();
        characterQueue = new IntMap<>();
        characterDequeue = new IntArray();
        mapInput = new ScreenMapInput();

        tileSize = new Vector2(16, 16);
        mapSize = new Vector2(1, 1);
        mapPixelSize = new Vector2(16, 16);

        clearColor = new Color(0.7f, 0.7f, 1.0f, 1);

        multiplexer = new InputMultiplexer();
        //	resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    /**
     * @see com.badlogic.gdx.Screen#dispose()
     */
    @Override
    public void dispose()
    {
    }

    /**
     * @see com.badlogic.gdx.Screen#hide()
     */
    @Override
    public void hide()
    {
        multiplexer.clear();
    }

    /**
     * @see com.badlogic.gdx.Screen#pause()
     */
    @Override
    public void pause()
    {
    }

    /**
     * @see com.badlogic.gdx.Screen#render(float)
     */
    @Override
    public void render(float delta)
    {
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();

        camera.update();
        stage.act();

        if (!loading)
        {
            Gdx.gl.glClearColor(clearColor.r, clearColor.g, clearColor.b, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            mapInput.update();

            // insures players will be positioned at the right location when a
            // map is set
            IntMap.Keys ids = this.characterQueue.keys();
            while (ids.hasNext)
            {
                int id = ids.next();
                Position pos = this.characterQueue.remove(id);
                float[] where = this.positionToScale(pos);
                PlayerSprite sprite = this.characters.get(id);
                if (sprite != null)
                {
                    sprite.setPosition(where[0], where[1]);
                    if (sprite == this.mySprite)
                    {
                        worldCamera.position.set(where[0], where[1], 0);
                    }
                    sprite.addActors(worldStage);
                }
            }

            this.characterQueue.clear();

            // make sure the shader effect frame buffers are properly created
            // when needed
            if (!bufferSet)
            {
                mapBuffer = new FrameBuffer(Format.RGBA8888, (int) width, (int) height, false);
                mapTexture = mapBuffer.getColorBufferTexture();
                uiBuffer = new FrameBuffer(Format.RGBA8888, (int) width, (int) height, false);
                uiTexture = uiBuffer.getColorBufferTexture();
                bufferSet = true;
            }

            // render the map buffer
            mapBuffer.begin();
            {
                // remember to clear in here else the buffer will smear the map
                // where the
                // tiled map renderer doesn't draw
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                mapRenderer.setView(worldCamera);
                mapRenderer.render(bgLayers);

                worldStage.act(delta);

                worldStage.draw();

                mapRenderer.render(fgLayers);
            }
            mapBuffer.end();

            // render the ui buffer
            uiBuffer.begin();
            {
                Gdx.gl.glClearColor(clearColor.r, clearColor.g, clearColor.b, 0);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                stage.draw();
            }
            uiBuffer.end();

            // use the ui's alpha as our blur mask multiplier
            uiTexture.bind(1);

            // reset glActiveTexture to zero since SpriteBatch doesn't do this
            // for us
            Gdx.gl.glActiveTexture(GL20.GL_TEXTURE0);

            // draw the map/blur
            blurBatch.setProjectionMatrix(camera.combined);
            blurBatch.setShader(aeroGlass);
            blurBatch.begin();
            blurBatch.draw(mapTexture, 0f, 0f);
            blurBatch.end();

            // draw the ui
            blurBatch.setShader(null);
            blurBatch.begin();
            blurBatch.draw(uiTexture, 0f, 0f);
            blurBatch.end();

            // have the camera follow the player when moving
            worldCamera.position.set(this.mySprite.getX(), this.mySprite.getY(), 0);

            // insures players are removed at the end of the render cycle to
            // prevent race conditions
            for (int i = 0; i < characterDequeue.size; i++)
            {
                int id = characterDequeue.get(i);
                this.characters.remove(id).removeActors();
            }
            characterDequeue.clear();

        }
        // draw a loading screen
        else
        {
            Gdx.input.setInputProcessor(null);
            Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            loadingLayer.setVisible(true);
            stage.act(delta);
            stage.draw();

            if (mapRenderer != null && mySprite != null)
            {
                mapInput.initialize();
                Gdx.input.setInputProcessor(multiplexer);
                loading = false;
                loadingLayer.setVisible(false);
            }
        }
    }

    /**
     * @see com.badlogic.gdx.Screen#resize(int, int)
     */
    @Override
    public void resize(int width, int height)
    {

        stage.getViewport().update(width, height, false);
        worldStage.getViewport().update(width, height, true);

        if (mapRenderer != null)
        {
            worldCamera.position.set(mySprite.getX(), mySprite.getY(), 0);
            worldCamera.update();
        }

        camera.setToOrtho(true, width, height);
        blurBatch.setProjectionMatrix(defaultCamera.combined);

        // make sure buffers are resized
        bufferSet = false;

        if (menuArea != null)
        {
            menuArea.setPosition(menuArea.getX(), (worldStage.getViewport().getWorldHeight() - menuArea.getHeight()) - ((worldStage.getViewport().getWorldHeight() - menuArea.getHeight() - 770) / 2f));
        }

        // Reposition "responsive" overlaying screens (this currently only works if they are 250x300)
        for (OverlayingScreen overlay : overlayingScreens)
        {
            if (overlay.getIsResponsive())
            {
                // TODO: understand this and replace it with something more elegant.
                float newX = worldCamera.viewportWidth - overlay.getWidth() - (worldCamera.viewportWidth - overlay.getWidth() - 550) / 2f;
                float newY = worldCamera.viewportHeight - overlay.getHeight() - (worldCamera.viewportHeight - overlay.getHeight() - 500) / 2f;
                overlay.setPosition(newX, newY);
            }
        }
    }

    /**
     * @see com.badlogic.gdx.Screen#resume()
     */
    @Override
    public void resume()
    {
    }

    /**
     * Set the TiledMap we should be drawing
     *
     * @param tiledMap the map
     */
    public void setTiledMap(TiledMap tiledMap)
    {
        loading = true;

        // clear things when changing maps
        if (tiledMap == null)
        {
            for (Entry<PlayerSprite> c : characters)
            {
                c.value.removeActors();
            }
            characters.clear();
            this.mySprite = null;
            mapInput.setSprite(null);
            mapRenderer = null;
            return;
        }

        mapRenderer = new OrthogonalTiledMapRenderer(tiledMap, unitScale);

        // determine constant sizing properties of the map
        MapProperties prop = tiledMap.getProperties();
        int mapWidth = prop.get("width", Integer.class);
        int mapHeight = prop.get("height", Integer.class);
        int tilePixelWidth = prop.get("tilewidth", Integer.class);
        int tilePixelHeight = prop.get("tileheight", Integer.class);

        tileSize.set(tilePixelWidth, tilePixelHeight);
        tileSize.scl(unitScale);
        mapSize.set(mapWidth, mapHeight);
        mapPixelSize.set(mapSize.x * tileSize.x, mapSize.y * tileSize.y);

        // figure out which layers are foreground and background layers;
        IntArray background = new IntArray();
        IntArray foreground = new IntArray();

        MapLayers layers = mapRenderer.getMap().getLayers();
        for (int i = 0; i < layers.getCount(); i++)
        {
            MapLayer layer = layers.get(i);
            if (layer.getName().startsWith("foreground"))
            {
                foreground.add(i);
            }
            else
            {
                background.add(i);
            }
        }

        bgLayers = background.toArray();
        fgLayers = foreground.toArray();

        String colProp = prop.get("color", String.class);
        if (colProp != null)
        {
            String[] col = colProp.split("[\r\b\t\n ]+");
            clearColor = new Color(Float.parseFloat(col[0]), Float.parseFloat(col[1]), Float.parseFloat(col[2]), 1.0f);
        }
        else
        {
            clearColor.set(0.7f, 0.7f, 1.0f, 1);
        }
    }

    /**
     * @see com.badlogic.gdx.Screen#show()
     */
    @Override
    public void show()
    {

        worldStage = new Stage();
        blurBatch = new SpriteBatch();
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();

        stage = new Stage(new ExtendViewport(width, height));
        stage.getRoot().addCaptureListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                if (!(event.getTarget() instanceof TextField))
                {
                    // If they click outside of the textfield, nullify the keyboard focus.
                    stage.setKeyboardFocus(null);
                    // Readd multiplexers if they click outside of a text field.
                    multiplexer.clear();
                    multiplexer.addProcessor(mapInput);
                    multiplexer.addProcessor(stage);
                    multiplexer.addProcessor(keyInputProcessor);
                }
                else
                {
                    // If they did click on a textfield, we need to modify our multiplexer.
                    // Remove all input processors, change focus to the TextField, then add back the
                    // stage which is needed for chat to work.
                    multiplexer.clear();
                    multiplexer.addProcessor(stage);
                }
            }
        });
        stage.getRoot().addCaptureListener(new InputListener()
        {
            @Override
            public boolean keyDown(InputEvent event, int keycode)
            {
                if (event.getTarget() instanceof TextField)
                {
                    // If the key is being pressed inside of a text field and the keycode is Esc or
                    // Enter
                    if (keycode == Keys.ESCAPE || keycode == Keys.ENTER)
                    {
                        // unfocus that chat
                        stage.setKeyboardFocus(null);
                        // re-add the processors that were cleared, when we pressed Enter to go into
                        // chat.
                        multiplexer.clear();
                        multiplexer.addProcessor(mapInput);
                        multiplexer.addProcessor(stage);
                        multiplexer.addProcessor(keyInputProcessor);
                    }
                    // Always return true so that the input is not sent to any other processors, so
                    // that you can't move while typing
                    return true;
                }
                return false;
            }
        });

        Gdx.input.setInputProcessor(stage);
        defaultCamera = (OrthographicCamera) stage.getCamera();
        worldStage = new Stage(new ExtendViewport(DEFAULT_RES[0], DEFAULT_RES[1]));
        worldCamera = (OrthographicCamera) worldStage.getCamera();
        camera = new OrthographicCamera();

        final Skin skin = new Skin(Gdx.files.internal("ui-data/uiskin.json"));

        PlayerHatFactory hatFactory = new PlayerHatFactory(Gdx.files.internal("ui-data/hats.atlas"));
        PlayerHairFactory hairFactory = new PlayerHairFactory(Gdx.files.internal("ui-data/hair.atlas"));
        PlayerBodyFactory bodyFactory = new PlayerBodyFactory(Gdx.files.internal("ui-data/body.atlas"));
        PlayerShoesFactory shoesFactory = new PlayerShoesFactory(Gdx.files.internal("ui-data/shoes.atlas"));
        PlayerShirtFactory shirtFactory = new PlayerShirtFactory(Gdx.files.internal("ui-data/shirts.atlas"));
        PlayerPantsFactory pantsFactory = new PlayerPantsFactory(Gdx.files.internal("ui-data/pants.atlas"));
        PlayerEyesFactory eyesFactory = new PlayerEyesFactory(Gdx.files.internal("ui-data/eyes.atlas"));
        PlayerBikeFactory bikeFactory = new PlayerBikeFactory(Gdx.files.internal("ui-data/bikes.atlas"));

        playerFactory = new PlayerSpriteFactory(hatFactory, hairFactory, bodyFactory,
                shirtFactory, pantsFactory, shoesFactory,
                eyesFactory, bikeFactory);

        loadingLayer = new Group();
        loadingLayer.setSize(stage.getWidth(), stage.getHeight());
        TextureRegion fillTex = new TextureRegion(new Texture(Gdx.files.internal("ui-data/fill.png")));
        Image fill = new Image(new TextureRegionDrawable(fillTex));
        fill.setFillParent(true);
        fill.setSize(stage.getWidth(), stage.getHeight());
        loadingLayer.addActor(fill);
        Label l = new Label("Loading...", skin, "default");
        l.setPosition(10, 10);
        loadingLayer.addActor(l);
        loadingLayer.setVisible(false);
        stage.addActor(loadingLayer);
        keyInputProcessor = new ScreenMapKeyInputSentProcessor(stage, menuArea, popUpChatUI);
        multiplexer.addProcessor(mapInput);
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(keyInputProcessor);
        Gdx.input.setInputProcessor(multiplexer);
        // prepare the shader
        // create the Blur Shader for our pretty ui
        aeroGlass = new ShaderProgram(Gdx.files.internal("util/shaders/Aero.vertex.glsl"),
                Gdx.files.internal("util/shaders/Aero.fragment.glsl"));
        if (!aeroGlass.isCompiled())
        {
            System.err.println(aeroGlass.getLog());
            System.exit(0);
        }
        aeroGlass.begin();
        aeroGlass.setUniformi("u_mask", 1);
        aeroGlass.end();
        blurBatch.setShader(aeroGlass);

        loading = true;
    }

    /**
     * Adds a new renderable instance of a player, identified by their name, to the
     * map
     *
     * @param playerID            the unique identifier of the player being added
     * @param vanities            the list of vanity items the player is wearing
     * @param pos                 location to spawn the player
     * @param isThisClientsPlayer if the player to spawn is controlled by this client
     */
    public void addPlayer(int playerID, List<VanityDTO> vanities, Position pos, boolean isThisClientsPlayer)
    {
        PlayerSprite sprite = playerFactory.create(vanities, playerID);
        characterQueue.put(playerID, pos);
        characters.put(playerID, sprite);
        // detect when the player being added is the client's player for finer
        // control
        if (isThisClientsPlayer)
        {
            mySprite = sprite;
            mapInput.setSprite(mySprite);
            mapInput.setPosition(pos);
            mapInput.setPlayerID(playerID);
        }
    }

    /**
     * Adds all the UIs that are dependent on the player type to the the map
     *
     * @param playerName the player's name
     * @param crewName   the crew the player belongs to
     * @param major      the player's declared major
     */
    public void addUIs(String playerName, String crewName, String major)
    {
        if (menuArea != null)
        {
            // close the screens, incase they are still open, before we reset the menuArea,
            // losing the screens that it managed
            menuArea.closeAllOverlayingScreens();
        }
        SkinPicker.getSkinPicker().setCrew(crewName);

        ClientNotificationManager clientNotificationManager = ClientNotificationManager.getInstance();
        menuArea = new MenuUI();

        QuestUI qaScreen = new QuestUI();
        TerminalUI terminalUI = new TerminalUI();
        popUpChatUI = new PopUpChatUI();
        HighScoreUI highScoreUI = new HighScoreUI();
        FriendsUI friendsUI = new FriendsUI();
//		CoordinatesUI coordinatesUI = new CoordinatesUI();
        closetUI = new ClosetUI();
        shopUI = new ShopUI();
        clothingShopUI = new ClothingShopUI();

        menuArea.addOverlayingScreenToggle(popUpChatUI, "Chat");
        menuArea.addOverlayingScreenToggle(friendsUI, "Friends");
        menuArea.addOverlayingScreenToggle(terminalUI, "Terminal");
        menuArea.addOverlayingScreenToggle(qaScreen, "Quest");
        menuArea.addOverlayingScreenToggle(highScoreUI, "High Scores");
        menuArea.addOverlayingScreenToggle(closetUI, null);
        menuArea.addOverlayingScreenToggle(clothingShopUI, null);
        menuArea.addOverlayingScreenToggle(shopUI, null);
        menuArea.addDropdownClothing("list", clothingDropDownChangeListener(), closeListener());

//		overlayingScreens.add(coordinatesUI);
        overlayingScreens.add(qaScreen);
        overlayingScreens.add(highScoreUI);
        overlayingScreens.add(terminalUI);
        overlayingScreens.add(shopUI);
        overlayingScreens.add(popUpChatUI);
        overlayingScreens.add(friendsUI);
        overlayingScreens.add(closetUI);
        overlayingScreens.add(clothingShopUI);
        overlayingScreens.add(clientNotificationManager.getAlertContainer());

//		stage.addActor(coordinatesUI);
        stage.addActor(qaScreen);
        stage.addActor(highScoreUI);
        stage.addActor(popUpChatUI);
        stage.addActor(terminalUI);
        stage.addActor(shopUI);
        stage.addActor(menuArea);
        stage.addActor(friendsUI);
        stage.addActor(closetUI);
        stage.addActor(clothingShopUI);
        stage.addActor(clientNotificationManager.getAlertContainer());

        multiplexer.clear();
        keyInputProcessor = new ScreenMapKeyInputSentProcessor(stage, menuArea, popUpChatUI);

        multiplexer.addProcessor(mapInput);
        multiplexer.addProcessor(keyInputProcessor);
        multiplexer.addProcessor(stage);
    }

    /**
     * @return the click Listener with the required behavior
     */
    private ChangeListener clothingDropDownChangeListener()
    {
        return new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                String buttonText = menuArea.getClothingSelectBox().getSelected();

                switch (buttonText)
                {
                    case MenuUI.CLOSET_BUTTON_TEXT:
                        boolean closetToggled = closetUI.isVisible();
                        closeAllOverlayingScreens();
                        if (!closetToggled)
                        {
                            closetUI.toggleVisibility();
                        }
                        break;
                    case MenuUI.IN_GAME_SHOP_BUTTON_TEXT:
                        boolean inGameShopToggled = clothingShopUI.isVisible();
                        closeAllOverlayingScreens();
                        if (!inGameShopToggled)
                        {
                            clothingShopUI.toggleVisibility();
                        }
                        break;
                    case MenuUI.REAL_LIFE_SHOP_BUTTON_TEXT:
                        boolean realLifeShopToggled = clothingShopUI.isVisible();
                        closeAllOverlayingScreens();
                        if (!realLifeShopToggled)
                        {
                            shopUI.toggleVisibility();
                        }
                        break;
                }
            }
        };
    }

    /**
     * click listener to close windows when dropdown is clicked
     *
     * @return listener
     */
    private ClickListener closeListener()
    {
        return new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                closeAllOverlayingScreens();
            }
        };

    }

    /**
     * Closes all open overlaying screen.
     */
    public void closeAllOverlayingScreens()
    {
        for (OverlayingScreen os : overlayingScreens)
        {
            if (os.getClass() == AlertContainer.class)
            {
                continue;
            }
            os.setVisible(false);
        }
    }

    /**
     * Moves a player on the map using the map's unit scaling
     *
     * @param id  unique player identifier
     * @param pos position to move to
     */
    public void movePlayer(int id, Position pos)
    {
        PlayerSprite sprite = this.characters.get(id);
        if (sprite != null)
        {
            float[] loc = positionToScale(pos);
            sprite.move(loc[0], loc[1]);
        }
        if (getIsPlayerCharacter(id))
        {
            mapInput.setPosition(pos);
        }
    }

    /**
     * Scales a position to the proper pixel ratio of the map
     *
     * @param pos Position to scale into the proper ratio
     * @return the scaled position as a float array
     */
    private float[] positionToScale(Position pos)
    {
        return new float[]{pos.getColumn() * tileSize.x, mapPixelSize.y - (pos.getRow() + 1) * tileSize.y};
    }

    /**
     * Removes a player from this display
     *
     * @param playerID the player's unique identifying code
     */
    public void removePlayer(int playerID)
    {
        PlayerSprite s = this.characters.remove(playerID);
        if (s != null)
        {
            s.removeActors();
        }
    }

    /**
     * Returns True if the playerID is the player character
     *
     * @param playerID the ID of the player
     * @return True if the playerID is the player character
     */
    public boolean getIsPlayerCharacter(int playerID)
    {
        return mapInput.getPlayerID() == playerID;
    }
}
