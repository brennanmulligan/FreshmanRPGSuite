package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.dataDTO.VanityDTO;
import edu.ship.engr.shipsim.model.reports.PlayerPurchasedClothingReport;

public class CommandPlayerPurchasedClothing extends Command
{
    private final int playerID;
    private final VanityDTO vanityDTO;

    public CommandPlayerPurchasedClothing(int playerID, VanityDTO vanityDTO)
    {
        this.playerID = playerID;
        this.vanityDTO = vanityDTO;
    }

    @Override
    void execute()
    {
        PlayerPurchasedClothingReport report = new PlayerPurchasedClothingReport(playerID, vanityDTO);
        ReportObserverConnector.getSingleton().sendReport(report);

        ThisClientsPlayer player = ClientPlayerManager.getSingleton().getThisClientsPlayer();

        player.setDoubloons((player.getDoubloons() - vanityDTO.getPrice()));
        player.sendDoubloonChangeReport();

        player.addItemToInventory(vanityDTO);
    }

    public int getPlayerID()
    {
        return playerID;
    }

    public VanityDTO getVanityDTO()
    {
        return vanityDTO;
    }
}
