package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.dataDTO.ClientPlayerQuestStateDTO;
import edu.ship.engr.shipsim.dataDTO.FriendDTO;
import edu.ship.engr.shipsim.dataDTO.LevelManagerDTO;
import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datasource.FriendTableDataGateway;
import edu.ship.engr.shipsim.datasource.LevelRecord;
import edu.ship.engr.shipsim.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Report that combines Quest descriptions and Quest states
 *
 * @author Ryan, LaVonne, Olivia
 */
public class UpdatePlayerInformationReport extends SendMessageReport
{

    private final List<ClientPlayerQuestStateDTO> clientPlayerQuestList;
    private final ArrayList<FriendDTO> friendList;
    private final int experiencePoints;
    private final int doubloons;
    private final LevelRecord level;
    private final int playerID;

    /**
     * Combine the player's quest state and quest descriptions Sets local
     * experience points equal to player's experience points
     *
     * @param player the player
     * @throws DatabaseException shouldn't
     */
    public UpdatePlayerInformationReport(Player player) throws DatabaseException
    {
        super(player.getPlayerID(), PlayerManager.getSingleton().isNPC(player.getPlayerID()));
        this.clientPlayerQuestList = QuestManager.getSingleton().getQuests(player);
        this.friendList = getFriendList(player.getPlayerID());
        this.experiencePoints = player.getExperiencePoints();
        this.doubloons = player.getDoubloons();
        this.level = LevelManagerDTO.getSingleton().getLevelForPoints(experiencePoints);
        this.playerID = player.getPlayerID();
    }

    /**
     * Return ArrayList of Client Player Quests
     *
     * @return clientPlayerQuestList
     */
    public List<ClientPlayerQuestStateDTO> getClientPlayerQuestList()
    {
        return clientPlayerQuestList;
    }

    /**
     * Return int of Player's doubloons
     *
     * @return doubloons
     */
    public int getDoubloons()
    {
        return doubloons;
    }

    /**
     * Return int of Player's experience points
     *
     * @return experiencePoints
     */
    public int getExperiencePoints()
    {
        return experiencePoints;
    }

    /**
     * @return this player's friend list
     */
    public ArrayList<FriendDTO> getFriendsList()
    {
        return friendList;
    }

    /**
     * Returns the Player's level
     *
     * @return level
     */
    public LevelRecord getLevel()
    {
        return level;
    }

    /**
     * @return this player's playerID
     */
    public int getPlayerID()
    {
        return playerID;
    }

    private ArrayList<FriendDTO> getFriendList(int playerID) throws DatabaseException
    {
        FriendTableDataGateway friendGateway =
                FriendTableDataGateway.getSingleton();
        return friendGateway.getAllFriends(playerID);

    }
}
