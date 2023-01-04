package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.model.reports.DisplayTextReport;

/**
 * @author Andy, Emmanuel, Adam, and Truc
 */
public class CommandDisplayText extends Command
{
    private String text;

    /**
     * constructor with text to be sent
     *
     * @param text - string
     */
    public CommandDisplayText(String text)
    {
        this.text = text;
    }


    /**
     * sends DisplayTextReport
     *
     * @return successful or not
     */
    @Override
    boolean execute()
    {
        DisplayTextReport report = new DisplayTextReport(text);
        ReportObserverConnector.getSingleton().sendReport(report);
        return true;
    }

    /**
     * @return text we want to show in screen
     */
    public String getText()
    {
        return text;
    }

}
