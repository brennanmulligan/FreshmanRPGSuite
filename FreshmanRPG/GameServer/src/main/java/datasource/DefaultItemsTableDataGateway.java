package datasource;

import dataDTO.VanityDTO;

import java.util.ArrayList;

public interface DefaultItemsTableDataGateway
{
    ArrayList<VanityDTO> getDefaultItems() throws DatabaseException;

    void addDefaultItem(int defaultID) throws DatabaseException;

    void removeDefaultItem(int defaultID) throws DatabaseException;

    void resetData() throws DatabaseException;
}
