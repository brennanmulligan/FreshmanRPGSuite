package edu.ship.engr.shipsim.view.screen.notification;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import edu.ship.engr.shipsim.view.screen.OverlayingScreen;

import java.util.PriorityQueue;
import java.util.Queue;

public class AlertContainer extends OverlayingScreen
{
    static VerticalGroup alertGroup;
    static Queue<AlertTable> queuedAlerts;

    public AlertContainer()
    {
        super();
        setVisible(true);
        setResponsive(true);

        queuedAlerts = new PriorityQueue<>();

        Table mainTable = new Table();
        mainTable.setFillParent(true);

        alertGroup = new VerticalGroup();
        alertGroup.setFillParent(true);

        mainTable.addActor(alertGroup);

        container.right().top();
        container.addActor(mainTable);
    }

    @Override
    public float getMyWidth()
    {
        return 250f;
    }

    @Override
    public float getMyHeight()
    {
        return 300f;
    }

    @Override
    public float getXPosition()
    {
        return Gdx.graphics.getWidth() - getWidth();
    }

    @Override
    public float getYPosition()
    {
        return Gdx.graphics.getHeight() - getHeight();
    }


    @Override
    public void toggleVisibility()
    {

    }

    public void addAlert(int id, String title, String message)
    {
        // Create a new AlertTable
        AlertTable alertTable = new AlertTable(id, title, message);

        // Define what happens when an alert is added to the AlertContainer
        SequenceAction sequence = new SequenceAction();
        sequence.addAction(Actions.fadeIn(0.2f));
        sequence.addAction(Actions.delay(2));
        sequence.addAction(Actions.fadeOut(0.4f));
        sequence.addAction(Actions.hide());
        sequence.addAction(Actions.run(() ->
        {
            if (queuedAlerts.size() == 0)
            {
                return;
            }

            if (alertGroup.getChildren().size < 3)
            {
                AlertTable alert = queuedAlerts.poll();
                alertGroup.addActor(alert);
            }
            else
            {
                queuedAlerts.add(alertTable);
            }
        }));
        sequence.addAction(Actions.removeActor());

        alertTable.addAction(sequence);

        // Add the alert
        if (alertGroup.getChildren().size == 0)
        {
            // Add the alert normally
            alertGroup.addActor(alertTable);
        }
        else
        {
            // Queue it to be added later
            queuedAlerts.add(alertTable);
        }
    }

}
