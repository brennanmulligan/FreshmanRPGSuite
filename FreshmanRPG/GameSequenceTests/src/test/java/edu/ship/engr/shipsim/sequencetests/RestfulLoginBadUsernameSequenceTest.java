package edu.ship.engr.shipsim.sequencetests;

import com.google.common.collect.Lists;
import edu.ship.engr.shipsim.communication.messages.*;
import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.model.Interaction;
import edu.ship.engr.shipsim.model.MessageFlow;
import edu.ship.engr.shipsim.model.SequenceTest;
import edu.ship.engr.shipsim.model.ServerType;
import edu.ship.engr.shipsim.model.CommandRestfulLogin;

import java.util.List;

public class RestfulLoginBadUsernameSequenceTest extends SequenceTest
{
    public RestfulLoginBadUsernameSequenceTest()
    {
        serverList.add(ServerType.LOGIN_SERVER);
        serverList.add(ServerType.AREA_SERVER);

        interactions.add(new Interaction(
                new CommandRestfulLogin(PlayersForTest.MERLIN_OFFLINE.getPlayerName() + "Z",
                        PlayersForTest.MERLIN_OFFLINE.getPlayerPassword()),
                PlayersForTest.MERLIN_OFFLINE.getPlayerID(),
                ServerType.RESTFUL_SERVER, 0, getMessageFlow()));
    }

    @Override
    public void resetNecessarySingletons()
    {

    }

    @Override
    public void setUpMachines() throws DatabaseException
    {

    }

    private MessageFlow[] getMessageFlow()
    {
        List<MessageFlow> messageFlow = Lists.newLinkedList();

        PlayersForTest player = PlayersForTest.MERLIN_OFFLINE;

        // Send login message from restful server to the login server
        messageFlow.add(new MessageFlow(
                ServerType.RESTFUL_SERVER, ServerType.LOGIN_SERVER,
                new RestfulLoginMessage(player.getPlayerName() + "Z", player.getPlayerPassword(), false
                        ),
                true
        ));

        // Send a login success message from login server to restful server
        messageFlow.add(new MessageFlow(
                ServerType.LOGIN_SERVER, ServerType.RESTFUL_SERVER,
                new RestfulLoginFailedMessage(false),
                true
        ));

        return messageFlow.toArray(new MessageFlow[0]);
    }
}
