package sequencetests;

import communication.messages.VanityShopInventoryRequestMessage;
import communication.messages.VanityShopInventoryResponseMessage;
import dataDTO.VanityDTO;
import datasource.DatabaseException;
import datatypes.PlayersForTest;
import datatypes.VanityForTest;
import datatypes.VanityShopItemsForTest;
import datatypes.VanityType;
import model.*;

import java.util.ArrayList;

/**
 * Sequence test for when a client requests the shop inventory
 *
 * @author Aaron, Jake
 */
public class VanityShopGetInvSequenceTest extends SequenceTest
{
    public VanityShopGetInvSequenceTest()
    {
        serverList.add(ServerType.THIS_PLAYER_CLIENT);
        serverList.add(ServerType.AREA_SERVER);

        ArrayList<VanityDTO> response = buildResponse();
        buildInteraction(response);
    }

    private ArrayList<VanityDTO> buildResponse()
    {
        ArrayList<VanityDTO> response = new ArrayList<>();
        for (VanityShopItemsForTest items : VanityShopItemsForTest.values())
        {
            VanityForTest item = VanityForTest.values()[items.getVanityID() - 1];
            response.add(new VanityDTO(items.getVanityID(), item.getName(),
                    item.getDescription(), item.getTextureName(),
                    VanityType.fromInt(item.getVanityType()), items.getPrice()));
        }
        return response;
    }

    private void buildInteraction(ArrayList<VanityDTO> response)
    {
        MessageFlow[] sequence =
                {
                        new MessageFlow(ServerType.THIS_PLAYER_CLIENT,
                                ServerType.AREA_SERVER,
                                new VanityShopInventoryRequestMessage(), false),
                        new MessageFlow(ServerType.AREA_SERVER,
                                ServerType.THIS_PLAYER_CLIENT,
                                new VanityShopInventoryResponseMessage(response), true)};
        interaction = new Interaction(sequence,
                new CommandShopInventoryRequest(),
                PlayersForTest.MERLIN.getPlayerID(),
                ServerType.THIS_PLAYER_CLIENT);
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
        OptionsManager.getSingleton().setMapName(PlayersForTest.MERLIN.getMapName());
        PlayerManager.resetSingleton();
        PlayerManager.getSingleton()
                .addPlayerSilently(PlayersForTest.MERLIN.getPlayerID());

        ClientModelTestUtilities.setUpThisClientsPlayerForTest(PlayersForTest.MERLIN);
        PlayerManager.getSingleton().addPlayer(PlayersForTest.MERLIN.getPlayerID());

    }

    /**
     * Reset any gateways this test has changed so that more tests can be run
     */
    @Override
    public void resetNecessarySingletons()
    {
        PlayerManager.resetSingleton();
    }
}
