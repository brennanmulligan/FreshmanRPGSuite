package datasource;

import dataDTO.VanityDTO;

import java.util.ArrayList;

/**
 * Interface for the vanity awards gateway
 */
public interface VanityAwardsTableDataGateway
{
    /**
     * Gets all the vanity awards stored in the database
     * @return a list of all the vanity awards
     * @throws DatabaseException shouldn't
     */
    ArrayList<VanityDTO> getVanityAwards() throws DatabaseException;

    /**
     * Adds a vanity award to the vanity awards list so it can be given as a quest reward
     * @param awardID the ID of the vanity award to add
     * @throws DatabaseException shouldn't
     */
    void addVanityAward(int questID, int awardID) throws DatabaseException;

    /**
     * Removes a vanity award from the vanity awards list so it cant be given out anymore
     * @param awardID the id of the award to be removed
     * @throws DatabaseException shouldnt
     */
    void removeVanityAward(int questID, int awardID) throws DatabaseException;

    /**
     * Resets the data
     * @throws DatabaseException shouldnt
     */
    void resetData() throws DatabaseException;
}
