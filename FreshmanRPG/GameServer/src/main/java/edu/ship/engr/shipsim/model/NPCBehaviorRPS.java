package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.datatypes.ChatType;
import edu.ship.engr.shipsim.datatypes.Position;
import edu.ship.engr.shipsim.model.reports.NPCChatReport;

import java.util.ArrayList;
import java.util.Random;

public class NPCBehaviorRPS extends NPCBehavior
{

    private final int ROCK = 0;
    private final int PAPER = 1;
    private final int SCISSORS = 2;

    private int userScore = 0, computerScore = 0;
    private boolean RPSRoundStarted = false;
    private boolean RPSFinished = false;

    public NPCBehaviorRPS(int playerID)
    {
        super(playerID);
        setUpListening();
    }

    /**
     * @param playerID
     * @param noFilePath represents a nonexistent filepath, necessary for the filepath column in the database
     */
    public NPCBehaviorRPS(int playerID, String noFilePath)
    {
        this(playerID);
    }

    protected void rockPaperScissors(int input)
    {
        ChatManager chat = ChatManager.getSingleton();
        Random rand = new Random();
        int computer = rand.nextInt(3);     //computer move
        int userInput = input;
        //best two of three
        //very long if else checking for the winner of rps
        if (userInput == ROCK && computer == ROCK)
        {
            chat.sendChatToClients(this.playerID, 0,
                    "You chose rock, the computer chose rock. It's a tie!",
                    new Position(0, 0), ChatType.Zone);
            chat.sendChatToClients(playerID, 0,
                    "The score is user: " + userScore + " computer: " + computerScore,
                    new Position(0, 0), ChatType.Zone);
        }
        else if (userInput == ROCK && computer == PAPER)
        {
            computerScore++;
            chat.sendChatToClients(this.playerID, 0,
                    "You chose rock, the computer chose paper. The computer wins :(",
                    new Position(0, 0), ChatType.Zone);
            chat.sendChatToClients(playerID, 0,
                    "The score is user: " + userScore + " computer: " + computerScore,
                    new Position(0, 0), ChatType.Zone);
        }
        else if (userInput == ROCK && computer == SCISSORS)
        {
            userScore++;
            chat.sendChatToClients(this.playerID, 0,
                    "You chose rock, the computer chose scissors. You win!",
                    new Position(0, 0), ChatType.Zone);
            chat.sendChatToClients(playerID, 0,
                    "The score is user: " + userScore + " computer: " + computerScore,
                    new Position(0, 0), ChatType.Zone);
        }
        else if (userInput == PAPER && computer == PAPER)
        {
            chat.sendChatToClients(this.playerID, 0,
                    "You chose paper, the computer chose paper. It's a tie!",
                    new Position(0, 0), ChatType.Zone);
            chat.sendChatToClients(playerID, 0,
                    "The score is user: " + userScore + " computer: " + computerScore,
                    new Position(0, 0), ChatType.Zone);
        }
        else if (userInput == PAPER && computer == ROCK)
        {
            userScore++;
            chat.sendChatToClients(this.playerID, 0,
                    "You chose paper, the computer chose rock. You win!",
                    new Position(0, 0), ChatType.Zone);
            chat.sendChatToClients(playerID, 0,
                    "The score is user: " + userScore + " computer: " + computerScore,
                    new Position(0, 0), ChatType.Zone);
        }
        else if (userInput == PAPER && computer == SCISSORS)
        {
            computerScore++;
            chat.sendChatToClients(this.playerID, 0,
                    "You chose paper, the computer chose scissors. The computer wins :(",
                    new Position(0, 0), ChatType.Zone);
            chat.sendChatToClients(playerID, 0,
                    "The score is user: " + userScore + " computer: " + computerScore,
                    new Position(0, 0), ChatType.Zone);
        }
        else if (userInput == SCISSORS && computer == SCISSORS)
        {
            chat.sendChatToClients(this.playerID, 0,
                    "You chose scissors, the computer chose scissors. It's a tie!",
                    new Position(0, 0), ChatType.Zone);
            chat.sendChatToClients(playerID, 0,
                    "The score is user: " + userScore + " computer: " + computerScore,
                    new Position(0, 0), ChatType.Zone);
        }
        else if (userInput == SCISSORS && computer == PAPER)
        {
            userScore++;
            chat.sendChatToClients(this.playerID, 0,
                    "You chose scissors, the computer chose paper. You win!",
                    new Position(0, 0), ChatType.Zone);
            chat.sendChatToClients(playerID, 0,
                    "The score is user: " + userScore + " computer: " + computerScore,
                    new Position(0, 0), ChatType.Zone);
        }
        else if (userInput == SCISSORS && computer == ROCK)
        {
            computerScore++;
            chat.sendChatToClients(this.playerID, 0,
                    "You chose scissors, the computer chose rock. The computer wins :(",
                    new Position(0, 0), ChatType.Zone);
            chat.sendChatToClients(playerID, 0,
                    "The score is user: " + userScore + " computer: " + computerScore,
                    new Position(0, 0), ChatType.Zone);
        }

    }

    @Override
    protected void doTimedEvent()
    {
//        doesn't do events at any interval
    }

    @Override
    protected ArrayList<Class<? extends Report>> getReportTypes()
    {
        ArrayList<Class<? extends Report>> reportTypes = new ArrayList<>();
        reportTypes.add(NPCChatReport.class);

        return reportTypes;
    }


    @Override
    public void receiveReport(Report incomingReport)
    {

        if (incomingReport instanceof NPCChatReport && !RPSFinished)
        {
            NPCChatReport chatReport = (NPCChatReport) incomingReport;
            Player player = PlayerManager.getSingleton().getPlayerFromID(this.playerID);
            ChatManager chat = ChatManager.getSingleton();

            String userAnswer = chatReport.getChatText().toLowerCase();//.replaceAll(" ", "");

            if (userAnswer.equalsIgnoreCase("rubber ducky") && !RPSRoundStarted)
            {
                chat.sendChatToClients(playerID, 0, "Rock Paper Scissors: Choose your weapon",
                        new Position(0, 0), ChatType.Zone);

                RPSRoundStarted = true;
            }
            else if (userAnswer.equalsIgnoreCase("rock") && RPSRoundStarted)
            {
                rockPaperScissors(ROCK);
            }
            else if (userAnswer.equalsIgnoreCase("paper") && RPSRoundStarted)
            {
                rockPaperScissors(PAPER);
            }
            else if (userAnswer.equalsIgnoreCase("scissors") && RPSRoundStarted)
            {
                rockPaperScissors(SCISSORS);
            }

            if (computerScore == 2)
            {
                RPSFinished = true;
                chat.sendChatToClients(playerID, 0, "You're a loser haha",
                        new Position(0, 0), ChatType.Zone);
            }
            else if (userScore == 2)
            {
                RPSFinished = true;
                chat.sendChatToClients(playerID, 0, "You ~rock~ at rock paper scissors yeet",
                        new Position(0, 0), ChatType.Zone);
            }
        }
    }
}
