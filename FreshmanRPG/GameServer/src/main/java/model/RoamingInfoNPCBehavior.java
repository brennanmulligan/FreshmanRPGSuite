package model;



import datasource.DatabaseException;
import datasource.NPCRowDataGatewayRDS;
import datatypes.ChatType;
import datatypes.Position;
import model.reports.NPCChatReport;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
import java.util.regex.Pattern;

/**
 * @author Joshua, Ktyal, Ryan and John
 * Behavior class for NPCS that roam or have smart dialogue or both!
 */
public class RoamingInfoNPCBehavior extends NPCBehavior
{
    List<List<String>> parsedDialogueXML;
    List<NPCPath> parsedRegularPaths;
    List<NPCPath> parsedSmartPaths;
    private String currentTarget;
    static final int CHAT_DELAY_SECONDS = 20;
    static final int ROAM_DELAY_SECONDS = 2;
    protected int roamDelayCounter = 1;
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
    static final int CHAT_EXPIRE_DELAY_SECONDS = 25;

    public RoamingInfoNPCBehavior(int playerId)
    {
        super(playerId);
        sp = new SmartPath();
        parsedDialogueXML = new ArrayList<List<String>>();
        parsedRegularPaths = new ArrayList<NPCPath>();
        parsedSmartPaths = new ArrayList<NPCPath>();
        currentTarget = "start";
        try
        {
            NPCRowDataGatewayRDS gateway = new NPCRowDataGatewayRDS(playerId);
            filePath = gateway.getFilePath();
        }
        catch (DatabaseException e)
        {
            e.printStackTrace();
        }
        parseFileInfo();
        //If the NPC has no dialogue defined then it does not have to listen for anything
        if(!parsedDialogueXML.isEmpty())
        {
            setUpListening();
        }

        smartPathSetup(0);
    }

    /**
     * Basic smartPathing behavior
     * Sets the starting and target positions in smart path if the XML has them
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
            catch (NullPointerException e)
            {
                System.out.println("No viable path for NPC " + playerID);
            }
            /*
             * check to prevent popping the last position, as next time we enter method,
             * we need at least one left for our outer else condition to not cause a Null Pointer
             */
            if(smartPath != null)
            {
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
     * method to actually update the npc's position on the server
     */
    protected void roamOnPath()
    {
        NPCPath path = parsedRegularPaths.get(1);
        CommandMovePlayer cmd = new CommandMovePlayer(playerID, path.getPath().get(pathStep));
        cmd.execute();
        pathStep++;
        if (pathStep >= path.getPath().size())
        {
            pathStep = 0;
        }

    }

    /**
     * Get report types that this class listens for
      * @return ArrayList of report types
     */
    @Override
    protected ArrayList<Class<? extends QualifiedObservableReport>> getReportTypes()
    {
        ArrayList<Class<? extends QualifiedObservableReport>> reportTypes = new ArrayList<>();
        //NPCs that respond to chat messages have to listen to different reports
        //So that player messages can show up before NPC messages
        reportTypes.add(NPCChatReport.class);
        return reportTypes;
    }

    /**
     * When this NPC receives a report, if it recognizes something
     * it will respond to the player from the dialogue tree
     * @param incomingReport
     */
    @Override
    public void receiveReport(QualifiedObservableReport incomingReport)
    {
        //If this NPC has no dialogue, go no further
        if(!parsedDialogueXML.isEmpty())
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
                    //Make the players message soemthing we can read easier
                    String input = report.getChatText().toLowerCase().replaceAll(" ", "");
                    for (List<String> node : parsedDialogueXML)
                    {
                        //Gets the important info from the parsedDialogueXML
                        String id = node.get(0);
                        String pattern = node.get(1);
                        String responses = node.get(2);
                        String nodeId = node.get(3);
                        String target = node.get(4);
                        String message = node.get(5);
                        message = message.replaceAll("[ ]{2,}", "");
                        message = message.replaceAll("\\n", "");

                        if (nodeId.equals(currentTarget))
                        {
                            //If the players message matches a pattern were looking for send them the appropriate message
                            if (Pattern.matches(pattern, input) || (Pattern.matches(pattern, "")))
                            {
                                ChatManager.getSingleton()
                                        .sendChatToClients(playerID, 0, message, PlayerManager.getSingleton()
                                                        .getPlayerFromID(playerID)
                                                        .getPlayerPosition(),
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
     * @param filePath
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
                    String responses = element.getAttribute("response-ids");
                    String nodeId = element.getAttribute("node-id");
                    String target = element.getAttribute("target-node");

                    // get text
                    String message = element.getTextContent();

                    parsedDialogueXML.add(new ArrayList<String>(Arrays.asList(id, pattern, responses, nodeId, target, message)));
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
                        addPathsToList(id, message, parsedRegularPaths);
                    }
                    else if (type.equals(SMART_PATH_TYPE))
                    {
                        addPathsToList(id, message, parsedSmartPaths);
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private List<NPCPath> addPathsToList(String id, String message, List<NPCPath> list)
    {
        NPCPath path = new NPCPath(id, message);
        list.add(path);
        return list;
    }

}

/**
 * @author Ryan C and John L
 *
 * Holds info about the NPC path
 *
 * This class should be extracted to its own file if any other class end up using it
 */
class NPCPath
{

    private int pathID;
    ArrayList<Position> path;

    public NPCPath(String pathID, String sPath)
    {
        this.pathID = Integer.parseInt(pathID);
        path = parsePath(sPath);
    }

    /**
     * Parses a path read from the XML file
     * @param sPath String read from XML
     * @return ArrayList containing path
     */
    private ArrayList<Position> parsePath(String sPath)
    {
        sPath = sPath.replaceAll("[ ]{2,}", "");
        sPath = sPath.replaceAll("\\n", "");
        String[] sPositions = sPath.split("\\s+");
        path = new ArrayList<>();
        for (int i = 0; i < sPositions.length; i++)
        {
            Position pos = new Position(
                    Integer.parseInt(sPositions[i].substring(0, sPositions[i].indexOf(",")))
                    , Integer.parseInt(sPositions[i].substring(sPositions[i].indexOf(",") + 1)));
            path.add(pos);
        }
        return path;
    }

    public ArrayList<Position> getPath()
    {
        return this.path;
    }
}

