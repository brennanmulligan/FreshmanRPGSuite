package edu.ship.engr.shipsim.model.reports;

/**
 * @author Derek
 */
public class LoginFailedReport extends SendMessageReport
{
    private final String message;

    public LoginFailedReport(String message)
    {
        super(0, true);
        this.message = message;
    }

    public String getMessage()
    {
        return message;
    }
}
