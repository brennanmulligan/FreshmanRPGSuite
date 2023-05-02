package edu.ship.engr.shipsim.sequencetests;

import edu.ship.engr.shipsim.communication.messages.VanityShopInventoryRequestMessage;
import edu.ship.engr.shipsim.communication.messages.VanityShopInventoryResponseMessage;
import edu.ship.engr.shipsim.dataDTO.VanityDTO;
import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.datatypes.VanityItemsForTest;
import edu.ship.engr.shipsim.datatypes.VanityShopItemsForTest;
import edu.ship.engr.shipsim.datatypes.VanityType;
import edu.ship.engr.shipsim.model.*;

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

    /**
     * Reset any gateways this test has changed so that more tests can be run
     */
    @Override
    public void resetNecessarySingletons()
    {
        PlayerManager.resetSingleton();
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
        OptionsManager.getSingleton().setMapFileTitle(PlayersForTest.MERLIN.getMapName());
        PlayerManager.resetSingleton();
        PlayerManager.getSingleton()
                .addPlayerSilently(PlayersForTest.MERLIN.getPlayerID());

        ClientModelTestUtilities.setUpThisClientsPlayerForTest(PlayersForTest.MERLIN);
        PlayerManager.getSingleton().addPlayer(PlayersForTest.MERLIN.getPlayerID());

    }

    private void buildInteraction(ArrayList<VanityDTO> response)
    {
        MessageFlow[] sequence =
                {new MessageFlow(ServerType.THIS_PLAYER_CLIENT, ServerType.AREA_SERVER,
                        new VanityShopInventoryRequestMessage(false), true),
                        new MessageFlow(ServerType.AREA_SERVER,
                                ServerType.THIS_PLAYER_CLIENT,
                                new VanityShopInventoryResponseMessage(response, false), true)};
        interactions.add(new Interaction(new CommandShopInventoryRequest(),
                PlayersForTest.MERLIN.getPlayerID(), ServerType.THIS_PLAYER_CLIENT,
                0, sequence));
    }

    private ArrayList<VanityDTO> buildResponse()
    {
        ArrayList<VanityDTO> response = new ArrayList<>();
        for (VanityShopItemsForTest items : VanityShopItemsForTest.values())
        {
            VanityItemsForTest item = VanityItemsForTest.values()[items.getVanityID() - 1];
            response.add(new VanityDTO(items.getVanityID(), item.getName(),
                    item.getDescription(), item.getTextureName(),
                    item.getVanityType(), items.getPrice()
            ));
        }
        return response;
    }
}
