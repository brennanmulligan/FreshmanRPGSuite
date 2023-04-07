package edu.ship.engr.shipsim.model.reports;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(callSuper = true)
public class PingWatchdogReport extends SendMessageReport
{
    @Getter private final String hostName;

    public PingWatchdogReport(String hostName)
    {
        super(0, false);

        this.hostName = hostName;
    }
}
