package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.communication.messages.DoubloonPrizeMessage;
import edu.ship.engr.shipsim.dataDTO.DoubloonPrizeDTO;

import java.util.ArrayList;

/**
 * @author Christian C, Andrew M
 * <p>
 * This command gets all the prizes and will populate the doubloon manager
 */
public class PopulateDoubloonManagerCommand extends Command
{

    ArrayList<DoubloonPrizeDTO> dtos;

    /**
     * Constructor for the Populate Doubloons Manager Command
     *
     * @param dtos a list of the current doubloon prizes
     */
    public PopulateDoubloonManagerCommand(ArrayList<DoubloonPrizeDTO> dtos)
    {
        this.dtos = dtos;

    }

    public PopulateDoubloonManagerCommand(DoubloonPrizeMessage message)
    {
        this.dtos = message.getDtos();

    }

    /**
     * getter for name
     *
     * @param i index
     * @return name
     */
    public String getName(int i)
    {
        return dtos.get(i).getName();
    }

    /**
     * getter for price
     *
     * @param i index
     * @return price
     */
    public int getPrice(int i)
    {
        return dtos.get(i).getCost();
    }

    /**
     * getter for the description
     *
     * @param i index
     * @return description
     */
    public String getDescription(int i)
    {
        return dtos.get(i).getDescription();
    }

    /**
     * getter for the list of DTOS
     *
     * @return list of DTOs
     */
    public ArrayList<DoubloonPrizeDTO> getDtos()
    {
        return dtos;
    }

    /**
     * Executes the command
     */
    @Override
    void execute()
    {
        DoubloonManager.getSingleton().add(dtos);
    }

}