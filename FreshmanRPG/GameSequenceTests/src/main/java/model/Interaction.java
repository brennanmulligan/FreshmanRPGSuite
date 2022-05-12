package model;

import model.Command;
import model.CommandSendTerminalText;
import model.MessageFlow;
import model.ServerType;

import java.util.ArrayList;
import java.util.Arrays;

public class Interaction
{
    ArrayList<MessageFlow> messageSequence;

    public Interaction(MessageFlow[] sequence, CommandSendTerminalText command,
                       int initiatingPlayerID, ServerType initiatingMachineType)
    {
        this.command = command;
        this.initiatingPlayerID = initiatingPlayerID;
        this.initiatingMachineType = initiatingMachineType;
        messageSequence = new ArrayList<>();
        messageSequence.addAll(Arrays.asList(sequence));

    }


    private final CommandSendTerminalText command;
    private final int initiatingPlayerID;
    private final ServerType initiatingMachineType;

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
