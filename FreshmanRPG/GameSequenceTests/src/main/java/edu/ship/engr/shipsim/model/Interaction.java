package edu.ship.engr.shipsim.model;

import java.util.ArrayList;
import java.util.Arrays;

public class Interaction
{
    private final Command command;
    private final int initiatingPlayerID;
    private final ServerType initiatingMachineType;
    ArrayList<MessageFlow> messageSequence;

    public Interaction(Command command, int initiatingPlayerID,
                       ServerType initiatingMachineType, MessageFlow[] sequence)
    {
        this.command = command;
        this.initiatingPlayerID = initiatingPlayerID;
        this.initiatingMachineType = initiatingMachineType;
        messageSequence = new ArrayList<>();
        messageSequence.addAll(Arrays.asList(sequence));
    }

    public Command getInitiatingCommand()
    {
        return command;
    }

    public int getInitiatingPlayerID()
    {
        return initiatingPlayerID;
    }

    public ServerType getInitiatingServerType()
    {
        return initiatingMachineType;
    }

    public ArrayList<MessageFlow> getMessageSequence()
    {
        return messageSequence;
    }
}
