package sequencetests;

import datasource.DatabaseException;
import datatypes.PlayersForTest;
import model.Command;
import model.SequenceTest;
import model.ServerType;

public class PlayerHasVanityItemSequenceTest extends SequenceTest
{

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
        return ServerType.LOGIN_SERVER;
    }

    /**
     * @return the player ID of the player that is initiating this sequence
     */
    @Override
    public int getInitiatingPlayerID()
    {
        return PlayersForTest.JOHN.getPlayerID();
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

    }
}
