package edu.ship.engr.shipsim.view.player;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import edu.ship.engr.shipsim.view.screen.SkinPicker;

public class ChatBubbleTable extends Table implements Comparable<ChatBubbleTable>
{
    private final int id;
    private final float width = 180f;
    private final float height = 80f;

    public ChatBubbleTable(int id, String message)
    {
        String msg = message;

        if (msg.length() > 60)
        {
            msg = msg.substring(0, 59).concat("...");
        }

        this.id = id;

        TextureRegionDrawable textureRegion;
        Pixmap bgPixmap = getRoundedBackground(15, Color.WHITE);
        textureRegion = new TextureRegionDrawable(new TextureRegion(new Texture(bgPixmap)));

        setBackground(textureRegion);
        setWidth(width);
        setHeight(height);

        Label chatLabel = new Label(msg, SkinPicker.getSkinPicker().getDefaultSkin());
        chatLabel.setColor(Color.BLACK);
        chatLabel.setWidth(width);
        chatLabel.setHeight(height);
        chatLabel.setWrap(true);

        add(chatLabel).pad(6f).expand().fill();
    }

    private Pixmap getRoundedBackground(int radius, Color color)
    {
        Pixmap pixmap = new Pixmap((int) width, (int) height, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);

        //triangle
        pixmap.fillTriangle(0, pixmap.getHeight(),
                0,
                pixmap.getHeight() - 2 * radius,
                2 * radius,
                pixmap.getHeight() - 2 * radius);

        // Pink rectangle
        pixmap.fillRectangle(0, radius, pixmap.getWidth(), pixmap.getHeight() - 2 * radius);

        // Green rectangle
        pixmap.fillRectangle(radius, 0, pixmap.getWidth() - 2 * radius, pixmap.getHeight());


        // Bottom-left circle
        pixmap.fillCircle(radius, radius, radius);

        // Top-left circle
        pixmap.fillCircle(radius, pixmap.getHeight() - radius, radius);

        // Bottom-right circle
        pixmap.fillCircle(pixmap.getWidth() - radius, radius, radius);

        // Top-right circle
        pixmap.fillCircle(pixmap.getWidth() - radius, pixmap.getHeight() - radius, radius);
        return pixmap;
    }

    @Override
    public int compareTo(ChatBubbleTable o)
    {
        return id - o.id;
    }
}
