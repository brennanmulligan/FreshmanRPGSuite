package edu.ship.engr.shipsim.datatypes;

import edu.ship.engr.shipsim.dataDTO.InfoNPCDTO;

/**
 * enum for test database, response is what the player says to trigger a response from our npc, who gives information following.
 *
 * @author John Lang and Noah Macminn
 */
public enum InfoNPCResponsesForTest
{

    INFO_1("scheduling", "Go have some lamb, then come talk to the big person", 32),
    INFO_2("services", "\n-Course-based tuoring for most gen ed courses and many upper level courses\n" +
            "\n-Writing Tutoring\n-Learning Specialists that provide support with study tools and strategies\"", 32);


    private String response;
    private String information;
    private int npcID;

    private InfoNPCResponsesForTest(String response, String information, int npcID)
    {
        this.response = response;
        this.information = information;
        this.npcID = npcID;
    }

    public String getResponse()
    {
        return response;
    }

    public String getInformation()
    {
        return information;
    }

    public int getNpcID()
    {
        return npcID;
    }

    /**
     * @return a DTO for this response/information pairing
     */
    public InfoNPCDTO getDTO()
    {
        return new InfoNPCDTO(response, information, npcID);
    }

}
