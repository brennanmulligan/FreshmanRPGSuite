package model;

import dataDTO.VanityDTO;
import model.reports.PlayerPurchasedClothingReport;

import java.io.IOException;

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
    boolean execute() throws IOException
    {
        PlayerPurchasedClothingReport report = new PlayerPurchasedClothingReport(playerID, vanityDTO);
        QualifiedObservableConnector.getSingleton().sendReport(report);

        ThisClientsPlayer player = ClientPlayerManager.getSingleton().getThisClientsPlayer();

        player.setDoubloons((player.getDoubloons() - vanityDTO.getPrice()));
        player.sendDoubloonChangeReport();

        player.addItemToInventory(vanityDTO);
        return true;
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
