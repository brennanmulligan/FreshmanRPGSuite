package sequencetests;

import communication.messages.VanityShopInventoryRequestMessage;
import communication.messages.VanityShopInventoryResponseMessage;
import dataDTO.VanityDTO;
import datasource.DatabaseException;
import datatypes.PlayersForTest;
import datatypes.VanityForTest;
import datatypes.VanityType;
import model.*;

import java.util.ArrayList;

/**
 * Sequence test for when a client requests the shop inventory
 * @author Aaron, Jake
 */
public class VanityShopGetInvSequenceTest extends SequenceTest
{
    public VanityShopGetInvSequenceTest()
    {
        ArrayList<VanityDTO> response = new ArrayList<>();
        for (VanityForTest items : VanityForTest.values())
        {
            response.add(new VanityDTO(items.getId(), items.getName(), items.getDescription(), items.getTextureName(), VanityType.fromInt(items.getVanityType()), items.getPrice()));
        }
        MessageFlow[] sequence =
                {
                        new MessageFlow(ServerType.THIS_PLAYER_CLIENT, ServerType.AREA_SERVER,
                                new VanityShopInventoryRequestMessage(), false),
                        new MessageFlow(ServerType.AREA_SERVER, ServerType.THIS_PLAYER_CLIENT,
                                new VanityShopInventoryResponseMessage(response), true)};

        for (MessageFlow mf : sequence)
        {
            messageSequence.add(mf);
        }

        serverList.add(ServerType.THIS_PLAYER_CLIENT);
        serverList.add(ServerType.AREA_SERVER);
    }

    /**
     * @return the command that will initiate the sequence
     */
    @Override
    public Command getInitiatingCommand()
    {
        return null;
    }

    /**
     * @return the type of server where the initiating command is run
     */
    @Override
    public ServerType getInitiatingServerType()
    {
        return ServerType.THIS_PLAYER_CLIENT;
    }

    /**
     * @return the player ID of the player that is initiating this sequence
     */
    @Override
    public int getInitiatingPlayerID()
    {
        return PlayersForTest.MERLIN.getPlayerID();
    }

    /**
     * Set up anything in the singletons (like OptionsManager) that is required
     * by this test
     *
     * @throws DatabaseException if Player Manager goofs
     */
    @Override
    public void setUpMachines() throws DatabaseException
    {

    }

    /**
     * Reset any gateways this test has changed so that more tests can be run
     */
    @Override
    public void resetDataGateways()
    {
        PlayerManager.resetSingleton();
    }
}
