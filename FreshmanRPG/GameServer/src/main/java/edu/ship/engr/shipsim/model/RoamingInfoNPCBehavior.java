package edu.ship.engr.shipsim.model;


import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datasource.NPCRowDataGateway;
import edu.ship.engr.shipsim.datatypes.ChatType;
import edu.ship.engr.shipsim.datatypes.Position;
import edu.ship.engr.shipsim.model.reports.NPCChatReport;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Pattern;

/**
 * @author Joshua, Ktyal, Ryan and John
 * Behavior class for NPCS that roam or have smart dialogue or both!
 */
public class RoamingInfoNPCBehavior extends NPCBehavior
{
    private static final int END_POSITION_LOCATION = 1;
    private static final int START_POSITION_LOCATION = 0;
    List<ResponseNode> parsedDialogueXML;
    List<NPCPath> parsedRegularPaths;
    List<NPCPath> parsedSmartPaths;
    private String currentTarget;
    static final int CHAT_DELAY_SECONDS = 25;
    protected int chatDelayCounter = 0;
    private int pathStep = 0;
    private String filePath;
    private final SmartPath sp;
    private boolean isRoamingOnSmartPath = false;
    private boolean isSmartPathEnabled;
    private Stack<Position> smartPath;
    private Position startPosition;
    private Position targetPosition;

    static final String REGULAR_PATH_TYPE = "regular";
    static final String SMART_PATH_TYPE = "smart";

    /**
     * @param playerId the Player ID of the NPC we are constructing a RoamingInfo behavior for.
     */
    public RoamingInfoNPCBehavior(int playerId)
    {
        super(playerId);
        sp = new SmartPath();
        parsedDialogueXML = new ArrayList<>();
        parsedRegularPaths = new ArrayList<>();
        parsedSmartPaths = new ArrayList<>();
        currentTarget = "start"; // beginning level of dialogue tree
        try
        {
            //grab filepath from the NPC table, this is the XML containing the dialogue and path position information (see Wiki)
            NPCRowDataGateway gateway = new NPCRowDataGateway(playerId);
            filePath = gateway.getFilePath();
        }
        catch (DatabaseException e)
        {
            e.printStackTrace();
        }
        parseFileInfo();
        //If the NPC has no dialogue defined then it does not have to listen for anything
        if (!parsedDialogueXML.isEmpty())
        {
            setUpListening();
        }

        smartPathSetup(0);
    }

    /**
     * Basic smartPathing behavior
     * Sets the starting and target positions in smart path if the XML has them
     *
     * @param pathID int path id number to use
     */
    private void smartPathSetup(int pathID)
    {
        //if we have smart paths, set our roaming behavior to smart
        if (!parsedSmartPaths.isEmpty())
        {
            isSmartPathEnabled = true;
            startPosition = parsedSmartPaths.get(pathID).getPath().get(START_POSITION_LOCATION);
            targetPosition = parsedSmartPaths.get(pathID).getPath().get(END_POSITION_LOCATION);
        }
        //no smart paths found, sets the behavior to regular
        else
        {
            isSmartPathEnabled = false;
        }
    }


    /**
     * Currently, this will try to walk on smart path if one has been read from the xml, then it looks for regular
     * paths, if none are found then no movement occurs
     */
    @Override
    protected void doTimedEvent()
    {
        if (isSmartPathEnabled)
        {
            walkSmartPath();
        }
        else if (parsedRegularPaths.size() > 0)
        {
            roamOnPath();
        }
        if (chatDelayCounter == 0)
        {
            currentTarget = "start";
        }
        chatDelayCounter = (chatDelayCounter + 1) % CHAT_DELAY_SECONDS;
    }

    /**
     * Our method for our smart roaming behavior. We get a stack returned from SmartPath.aStar() (if a viable path is found, otherwise it returns null).
     * We have a check to see if the NPC has begun his smart roam yet, if not, he will get the path steps from A*.
     */
    private void walkSmartPath()
    {
        if (!isRoamingOnSmartPath)
        {

            smartPath = sp.aStar(startPosition, targetPosition);
            /*
             * if no viable path is found, our smart path will return null.
             */
            try
            {
                smartPath.pop();
                isRoamingOnSmartPath = true;
            }
            /*
             * if SmartPath.aStar() returns null, notify developers that no viable path was found. This will only happen
             * when collision prevents a possible path from the start position to the end position given to A* for a certain map.
             */
            catch (NullPointerException e)
            {
                System.out.println("No viable path for NPC " + playerID);
            }
            /*
             * check to prevent popping the last position, as next time we enter method,
             * we need at least one left for our outer else condition to not cause a Null Pointer
             */
            if (smartPath != null)
            {
                /*
                 * check that we have more than one position left in stack, otherwise the next time we enter this
                 * method, we will have a null pointer exception. If position stack size is equal to 1,
                 * reset the smart pathing by setting isRoamingOnSmartPath to false.
                 */
                if (smartPath.size() > 1)
                {
                    CommandMovePlayer cmd = new CommandMovePlayer(playerID, smartPath.pop());
                    cmd.execute();

                }
                else
                {
                    isRoamingOnSmartPath = false;
                }
            }
        }
        /*
         * We are roaming on smart path currently, thus we simply pop and then check to make sure our stack
         * is not empty, if it is we begin a new A* path search, from the end position TO the beginning position,
         * that way our roaming npc walks back and forth between the two positions and doesn't stop when
         * he gets to his destination
         */
        else
        {
            CommandMovePlayer cmd = new CommandMovePlayer(playerID, smartPath.pop());
            cmd.execute();
            if (smartPath.isEmpty())
            {
                isRoamingOnSmartPath = false;
                Position tempPos = startPosition;
                startPosition = targetPosition;
                targetPosition = tempPos;
            }
        }
    }

    /**
     * method to actually update the regular roaming npc's position on the server, called every time doTimedEvent is called
     */
    protected void roamOnPath()
    {
        NPCPath path = parsedRegularPaths.get(1);
        CommandMovePlayer cmd = new CommandMovePlayer(playerID, path.getPath().get(pathStep));
        cmd.execute();
        pathStep++; //tracks which point in our path we are currently at.
        // reset pathStep if it exceeds the total amount of steps in the path
        if (pathStep >= path.getPath().size())
        {
            pathStep = 0;
        }

    }

    /**
     * Get report types that this class listens for
     *
     * @return ArrayList of report types
     */
    @Override
    protected ArrayList<Class<? extends Report>> getReportTypes()
    {
        ArrayList<Class<? extends Report>> reportTypes = new ArrayList<>();
        //NPCs that respond to chat messages have to listen to different reports
        //So that player messages can show up before NPC messages
        reportTypes.add(NPCChatReport.class);
        return reportTypes;
    }

    /**
     * When this NPC receives a report, if it recognizes something
     * it will respond to the player from the dialogue tree
     */
    @Override
    public void receiveReport(Report incomingReport)
    {
        //If this NPC has no dialogue, go no further
        if (!parsedDialogueXML.isEmpty())
        {
            if (incomingReport instanceof NPCChatReport)
            {
                NPCChatReport report = (NPCChatReport) incomingReport;
                Player player = PlayerManager.getSingleton().getPlayerFromID(report.getSenderID());
                //Make sure NPC is not talking to themselves
                if (player.getPlayerID() != this.playerID)
                {
                    //Reset the chat counter, whenever it successfully speaks to a player
                    chatDelayCounter = 1;
                    //Make the players message something we can read easier
                    String input = report.getChatText().toLowerCase().replaceAll("  ",
                            " ");
                    for (ResponseNode node : parsedDialogueXML)
                    {
                        //Gets the important info from the parsedDialogueXML
                        String id = node.getId();
                        String pattern = node.getPattern();
                        String nodeGroup = node.getNodeGroup();
                        String target = node.getTargetGroup();
                        String message = node.getMessage();
                        message = message.replaceAll("[ ]{2,}", " ");
                        message = message.replaceAll("\\n", "");
                        if (nodeGroup.equals(currentTarget))
                        {
                            //If the players message matches a pattern were looking for send them the appropriate message
                            if (Pattern.matches(pattern, input) || (Pattern.matches(pattern, "")))
                            {
                                ChatManager.getSingleton()
                                        .sendChatToClients(playerID, 0, message, PlayerManager.getSingleton()
                                                        .getPlayerFromID(playerID)
                                                        .getPosition(),
                                                ChatType.Local);
                                //Set current target to the target specified in the XML, so we can navigate the tree
                                currentTarget = target;
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Parses the Roaming and Dialogue info from the
     * XML and stores it locally
     */
    public void parseFileInfo()
    {
        parseDialogueXML(filePath);
        parsePathXML();
    }


    /**
     * Fills out parsedDialogueXML with info from the XML at filePath
     */
    private void parseDialogueXML(String filePath)
    {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        try
        {
            // process XML securely, avoid attacks like XML External Entities (XXE)
            builderFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

            // parse XML file
            DocumentBuilder db = builderFactory.newDocumentBuilder();

            Document doc = db.parse(new File(this.filePath));

            // http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();

            // get messsages
            NodeList list = doc.getElementsByTagName("Dialogue");

            for (int temp = 0; temp < list.getLength(); temp++)
            {

                Node node = list.item(temp);

                if (node.getNodeType() == Node.ELEMENT_NODE)
                {

                    Element element = (Element) node;

                    // get the values
                    String id = element.getAttribute("id");
                    String pattern = element.getAttribute("pattern");
                    String nodeId = element.getAttribute("node-id");
                    String target = element.getAttribute("target-node");
                    // get text
                    String message = element.getTextContent();
                    parsedDialogueXML.add(new ResponseNode(id, pattern, nodeId, target,
                            message));
                }
            }
        }
        catch (ParserConfigurationException | SAXException | IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Fills out parsedPathXML with info from the XML at filePath
     */
    private void parsePathXML()
    {
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();


        DocumentBuilder docBuilder;
        try
        {
            docBuilderFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(new File(this.filePath));
            doc.getDocumentElement().normalize();
            NodeList list = doc.getElementsByTagName("Path");
            ArrayList<Element> elements = new ArrayList<>();
            for (int temp = 0; temp < list.getLength(); temp++)
            {
                Node node = list.item(temp);
                if (node.getNodeType() == Node.ELEMENT_NODE)
                {

                    Element element = (Element) node;
                    String id = element.getAttribute("id");
                    String type = element.getAttribute("type");
                    String message = element.getTextContent();

                    if (type.equals(REGULAR_PATH_TYPE))
                    {
                        addPathsToList(message, parsedRegularPaths);
                    }
                    else if (type.equals(SMART_PATH_TYPE))
                    {
                        addPathsToList(message, parsedSmartPaths);
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * @param message the position content of the XML, either coordinate by coordinate, or a start and end position for A* smart pathing.
     * @param list    the ArrayList that contains the multiple paths for any given roaming NPC
     */

    private void addPathsToList(String message, List<NPCPath> list)
    {
        NPCPath path = new NPCPath(message);
        list.add(path);
    }

}

class ResponseNode
{

    private final String id;
    private final String pattern;
    private final String nodeGroup;
    private final String targetGroup;

    public String getMessage()
    {
        return message;
    }

    private final String message;

    public String getId()
    {
        return id;
    }

    public String getPattern()
    {
        return pattern;
    }

    public String getNodeGroup()
    {
        return nodeGroup;
    }

    public String getTargetGroup()
    {
        return targetGroup;
    }

    public ResponseNode(String id, String pattern, String nodeGroup, String targetGroup,
                        String message)
    {
        this.id = id;
        this.pattern = pattern;
        this.nodeGroup = nodeGroup;
        this.targetGroup = targetGroup;
        this.message = message;
    }
}

/**
 * @author Ryan C and John L
 * <p>
 * Holds info about the NPC path
 * <p>
 * This class should be extracted to its own file if any other class end up using it
 */
class NPCPath
{

    ArrayList<Position> path;

    public NPCPath(String sPath)
    {
        path = parsePath(sPath);
    }

    /**
     * Parses a path read from the XML file
     *
     * @param sPath String read from XML
     * @return ArrayList containing path
     */
    private ArrayList<Position> parsePath(String sPath)
    {
        sPath = sPath.replaceAll("[ ]{2,}", "");
        sPath = sPath.replaceAll("\\n", "");
        String[] sPositions = sPath.split("\\s+");
        path = new ArrayList<>();
        for (String sPosition : sPositions)
        {
            Position pos = new Position(
                    Integer.parseInt(sPosition.substring(0, sPosition.indexOf(",")))
                    , Integer.parseInt(sPosition.substring(sPosition.indexOf(",") + 1)));
            path.add(pos);
        }
        return path;
    }

    public ArrayList<Position> getPath()
    {
        return this.path;
    }
}

