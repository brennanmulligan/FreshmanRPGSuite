package edu.ship.engr.shipsim.model.reports;

/**
 * Report for when key input is given.
 *
 * @author Ian Keefer & TJ Renninger
 */
public class ClientKeyInputSentReport extends SendMessageReport
{

    private final String input;

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }

        ClientKeyInputSentReport that = (ClientKeyInputSentReport) o;

        return input.equals(that.input);
    }

    @Override
    public int hashCode()
    {
        return input.hashCode();
    }

    /**
     * @param input user key input
     */
    public ClientKeyInputSentReport(String input)
    {
        // Happens on client, thus it will always be loud
        super(0, false);

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
