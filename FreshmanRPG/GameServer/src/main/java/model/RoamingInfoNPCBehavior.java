package model;

import java.util.ArrayList;

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
import java.util.regex.Pattern;

/**
 * @author Joshua, Ktyal, Ryan and John
 * Behavior class for NPCS that roam or have smart dialogue or both!
 */
public class RoamingInfoNPCBehavior extends NPCBehavior
{
    List<List<String>> parsedDialogueXML;
    List<NPCPath> parsedPathXML;
    private String currentTarget;
    static final int CHAT_DELAY_SECONDS = 5;
    static final int ROAM_DELAY_SECONDS = 2;
    protected int roamDelayCounter = 1;
    protected int chatDelayCounter = 0;
    private int pathStep = 0;
    private String filePath;


    static final int CHAT_EXPIRE_DELAY_SECONDS = 25;

    public RoamingInfoNPCBehavior(int playerId)
    {
        super(playerId);
        parsedDialogueXML = new ArrayList<List<String>>();
        parsedPathXML = new ArrayList<NPCPath>();
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
    }



        @Override
        protected void doTimedEvent()
        {
            if (roamDelayCounter == 0 && parsedPathXML.size() > 0)
            {
                roamOnPath();
            }
            roamDelayCounter = (roamDelayCounter + 1) % ROAM_DELAY_SECONDS;

        }

        /**
         * method to actually update the npc's position on the server
         */
        protected void roamOnPath()
        {
            NPCPath path = parsedPathXML.get(1);
            CommandMovePlayer cmd = new CommandMovePlayer(playerID, path.getPath().get(pathStep));
            cmd.execute();
            pathStep++;
            if(pathStep >= path.getPath().size())
            {
                pathStep = 0;
            }

        }

    /**
     * Get report types that this class listens for
      * @return
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
        parsePathXML(filePath);
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
     * @param filePath
     */
    private void parsePathXML(String filePath)
    {
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();


        DocumentBuilder docBuilder = null;
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
                    String message = element.getTextContent();

                    NPCPath path = new NPCPath(id, message);
                    parsedPathXML.add(path);

                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }

    }
    class NPCPath
    {

        private int pathID;
        ArrayList<Position> path;

        public NPCPath(String pathID, String sPath)
        {
            this.pathID = Integer.parseInt(pathID);
            path = parsePath(sPath);
        }

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

