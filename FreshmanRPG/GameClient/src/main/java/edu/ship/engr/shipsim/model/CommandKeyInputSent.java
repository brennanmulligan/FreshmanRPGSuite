package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.model.reports.ClientKeyInputSentReport;

/**
 * Command for user key input
 *
 * @author Ian Keefer & TJ Renninger
 */
public class CommandKeyInputSent extends Command
{

    private String input;

    /**
     * @param input user key input
     */
    public CommandKeyInputSent(String input)
    {
        this.input = input;
    }

    /**
     * @return user key input
     */
    public String getInput()
    {
        return input;
    }

    @Override
    boolean execute()
    {
        ClientKeyInputSentReport report = new ClientKeyInputSentReport(input);
        QualifiedObservableConnector.getSingleton().sendReport(report);
        return true;
    }

}
