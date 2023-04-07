package edu.ship.engr.shipsim.communication.messages;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PingWatchdogMessage extends Message
{
    private static final long serialVersionUID = 1L;

    private final String hostName;
}

