package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.model.QualifiedObservableReport;

/**
 * Report for when key input is given.
 *
 * @author Ian Keefer & TJ Renninger
 */
public class ClientKeyInputSentReport implements QualifiedObservableReport
{

    private String input;

    /**
     * @param input user key input
     */
    public ClientKeyInputSentReport(String input)
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

}
