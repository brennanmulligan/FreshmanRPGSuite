package model.terminal;

import dataDTO.FriendDTO;
import datasource.DatabaseException;
import datasource.FriendTableDataGateway;
import datatypes.FriendStatusEnum;
import model.QualifiedObservableConnector;
import model.reports.updateFriendListReport;

/**
 * Class detailing the behavior of the 'friend add' command
 *
 * @author Zachary Semanco, Mina Kindo.
 */
class FriendAddBehavior extends FriendBehavior
{
    /**
     * Execute method for the AddBehavior
     */
    @Override
    protected String execute(int playerID, String[] friends)
    {
        StringBuilder result = new StringBuilder("> Friend Name - Status\n");
        FriendTableDataGateway gateway = getTheGateway();

        /*
         * Loop through the list of friends and try to add each one
         */
        for (int i = 0; i < friends.length; i++)
        {
            //Try to add friend
            try
            {
                gateway.add(playerID, friends[i], FriendStatusEnum.PENDING);
                result.append("> " + friends[i] + " " + FriendStatusEnum.PENDING + "\n");

                FriendDTO objForReportOne = new FriendDTO(playerID, gateway.getSpecificIDFromName(friends[i]), FriendStatusEnum.PENDING, gateway.getSpecificNameFromId(playerID), friends[i]);

                QualifiedObservableConnector.getSingleton().sendReport(new updateFriendListReport(objForReportOne));

                FriendDTO objForReportTwo = new FriendDTO(gateway.getSpecificIDFromName(friends[i]), playerID, FriendStatusEnum.PENDING, friends[i], gateway.getSpecificNameFromId(playerID));
                QualifiedObservableConnector.getSingleton().sendReport(new updateFriendListReport(objForReportTwo));
                System.out.println("Second report playerID: " + objForReportTwo.getPlayerID());
            }
            catch (DatabaseException e)
            {
                //Player not found
                if (e.getSimpleDescription().startsWith("Player not found or updated"))
                {
                    result.append("> " + friends[i] + " Error: Couldn't send request\n");
                }
                //Other Database Exception
                else
                {
                    e.printStackTrace();
                }
            }
            catch (NullPointerException e)
            {
                result.append("> " + friends[i] + " Error: Couldn't send request\n");
            }
        }
        return result.toString();
    }
}
