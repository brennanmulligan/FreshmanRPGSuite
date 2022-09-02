package edu.ship.engr.shipsim.view.screen.notification;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import edu.ship.engr.shipsim.view.screen.OverlayingScreenTable;
import edu.ship.engr.shipsim.view.screen.SkinPicker;

public class AlertTable extends OverlayingScreenTable implements Comparable<AlertTable>
{
    private final int id;

    public AlertTable(int id, String title, String message)
    {
        super(false);
        setPadding(0);
        container.right().top();
        container.setFillParent(true);

        this.id = id;

        Skin skin = SkinPicker.getSkinPicker().getDefaultSkin();

        Label alertTitle = new Label(title, skin);
        alertTitle.setFontScale(1.2f);
        alertTitle.setWidth(250f);

        Label alertMessage = new Label(message, skin);
        alertMessage.setWidth(250f);
        alertMessage.setWrap(true);

        Table alertText = new Table();
        alertText.setFillParent(true);
        alertText.padLeft(5f).padRight(5f).padTop(5f).padBottom(10f);
        alertText.add(alertTitle).expand().fill().row();
        alertText.add(alertMessage).expand().fill();

        container.row().expand().fill();
        container.add(alertText)
                .expand().fill()
                .width(250f)
                .minHeight(80f);
    }

    @Override
    public int compareTo(AlertTable o)
    {
        return id - o.id;
    }
}
