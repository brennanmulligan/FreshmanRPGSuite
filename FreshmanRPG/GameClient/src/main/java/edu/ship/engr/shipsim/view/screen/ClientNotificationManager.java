package edu.ship.engr.shipsim.view.screen;

import edu.ship.engr.shipsim.model.QualifiedObservableConnector;
import edu.ship.engr.shipsim.model.QualifiedObservableReport;
import edu.ship.engr.shipsim.model.QualifiedObserver;
import edu.ship.engr.shipsim.model.reports.*;
import edu.ship.engr.shipsim.view.screen.notification.AlertContainer;

public class ClientNotificationManager implements QualifiedObserver
{
    private static ClientNotificationManager manager = null;
    private static AlertContainer alertContainer;
    private int notificationId;

    private ClientNotificationManager()
    {
        setUpListening();

        alertContainer = new AlertContainer();
    }

    static public ClientNotificationManager getInstance()
    {
        if (manager == null)
        {
            manager = new ClientNotificationManager();
        }
        return manager;
    }

    @Override
    public void receiveReport(QualifiedObservableReport report)
    {
        // They all implement NotificationTrigger, so we have the notification type and text.
        // Spawn that kind of notification
        NotificationTrigger trigger = (NotificationTrigger) report;
        switch (trigger.getNotificationType())
        {
            case ALERT:
                alertContainer.addAlert(notificationId++,
                        trigger.getNotificationTitle(),
                        trigger.getNotificationBody());
                break;
            case NONE:
                break;
            default:
                System.err.println("ClientNotificationManager: Unknown NotificationType: "
                        + trigger.getNotificationType());
        }
    }

    /**
     * Sets up the QualifiedObserver for ChatReceivedReport
     */
    public void setUpListening()
    {
        QualifiedObservableConnector cm = QualifiedObservableConnector.getSingleton();
        cm.registerObserver(this, DisplayTextReport.class);
        cm.registerObserver(this, ChatReceivedReport.class);
        cm.registerObserver(this, QuestNeedingNotificationReport.class);
        cm.registerObserver(this, ObjectiveNeedingNotificationReport.class);
        cm.registerObserver(this, QuestStateChangeReport.class);
        cm.registerObserver(this, ObjectiveStateChangeReportInClient.class);
        cm.registerObserver(this, InteractionDeniedReport.class);
        cm.registerObserver(this, BuffPoolChangedReport.class);
    }

    public AlertContainer getAlertContainer()
    {
        return alertContainer;
    }
}
