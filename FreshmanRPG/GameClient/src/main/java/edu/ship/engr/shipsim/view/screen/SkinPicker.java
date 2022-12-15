package edu.ship.engr.shipsim.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.HashMap;

/**
 * @author Ian Keefer and TJ Renninger
 */
public class SkinPicker
{
    private static SkinPicker skinPicker;
    private HashMap<String, String> crewColorMap;
    private Skin skin;
    private String crew;

    private SkinPicker()
    {
        crewColorMap = new HashMap<>();
        crewColorMap.put("DEFAULT", "gray");
        crewColorMap.put("NULL_POINTER_EXCEPTION", "red");
        crewColorMap.put("OUT_OF_BOUNDS", "green");
        crewColorMap.put("OFF_BY_ONE", "blue");
        crewColorMap.put("TERMINAL", "terminal");
    }

    /**
     * @return the instance of the Skin Picker
     */
    public static SkinPicker getSkinPicker()
    {
        if (skinPicker == null)
        {
            skinPicker = new SkinPicker();
        }
        return skinPicker;
    }

    /**
     * @param c the crew of the current player;
     *          updates the crew name and the currently selected skin
     */
    public void setCrew(String c)
    {
        crew = c.toUpperCase();
        String color = crewColorMap.getOrDefault(crew, crewColorMap.get("DEFAULT"));
        skin = new Skin(Gdx.files.internal("ui-data/ui/screenskins/ui-" + color + ".json"));
    }

    /**
     * @return the skin that is specific to the crew of the current player
     */
    public Skin getCrewSkin()
    {
        if (skin == null)
        {
            String color = crewColorMap.getOrDefault(crew, crewColorMap.get("DEFAULT"));
            skin = new Skin(Gdx.files.internal("ui-data/ui/screenskins/ui-" + color + ".json"));
        }
        return skin;
    }

    /**
     * @return the default skin color
     */
    public Skin getDefaultSkin()
    {
        String color = crewColorMap.get("DEFAULT");
        return new Skin(Gdx.files.internal("ui-data/ui/screenskins/ui-" + color + ".json"));
    }

    /**
     * @return the terminal skin
     */
    public Skin getTerminalSkin()
    {
        String terminal = crewColorMap.get("TERMINAL");
        return new Skin(Gdx.files.internal("ui-data/ui/screenskins/ui-" + terminal + ".json"));
    }

    /**
     * @return the skin for the chat message box
     */
    public Skin getChatMessageBoxSkin()
    {
        String color = crewColorMap.get("DEFAULT");
        Skin skin = new Skin(Gdx.files.internal("ui-data/ui/screenskins/ui-" + color + ".json"));
        new Label("", skin).getStyle().fontColor.set(Color.BLACK);
        return skin;
    }
}
