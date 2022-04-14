package criteria;

public class VanityAwardDTO implements ObjectiveCompletionCriteria, QuestCompletionActionParameter
{

    int vanityID;
    int playerID;
    int isWearing;

    public VanityAwardDTO(int vanityID, int playerID)
    {
        this.vanityID = vanityID;
        this.playerID = playerID;
        this.isWearing = 0;
    }

    public int getVanityID()
    {
        return vanityID;
    }

    public int getPlayerID()
    {
        return playerID;
    }
}
