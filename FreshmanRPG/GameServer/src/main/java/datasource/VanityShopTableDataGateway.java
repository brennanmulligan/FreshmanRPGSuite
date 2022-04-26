package datasource;

import dataDTO.VanityDTO;
import java.util.ArrayList;
import model.VanityTableRow;

/**
 * Interface for vanity shop
 * @author Aaron W, Jake H
 */
public interface VanityShopTableDataGateway
{
    /**
     * @return the list of vanity show items
     */
    ArrayList<VanityDTO> getVanityShopInventory() throws DatabaseException;

    void addItem(int vanityID, int price) throws DatabaseException;

    void updateItem(int vanityID, int price) throws DatabaseException;

    void resetData();
}
