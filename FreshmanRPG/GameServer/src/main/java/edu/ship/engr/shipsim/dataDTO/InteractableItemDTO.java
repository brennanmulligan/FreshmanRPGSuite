package edu.ship.engr.shipsim.dataDTO;

import edu.ship.engr.shipsim.criteria.InteractableItemActionParameter;
import edu.ship.engr.shipsim.dataENUM.InteractableItemActionType;
import edu.ship.engr.shipsim.datatypes.Position;

/**
 * Interactable Item Data Transfer Object
 *
 * @author Elisabeth Ostrow, Jake Moore
 */
public class InteractableItemDTO
{
    //instance vars
    private int id;
    private String name;
    private Position position;
    private InteractableItemActionType actionType;
    private InteractableItemActionParameter actionParam;
    private String mapName;

    /**
     * Constructor for using actionType and actionParam as their correct datatypes
     *
     * @param id          - item id
     * @param name        - name of the item
     * @param pos         - position of the item
     * @param actionType  - type: InteractableItemActionType
     * @param actionParam - type: InteractbleItemActionParameter
     * @param mapName     - which map the item is located on.
     */
    public InteractableItemDTO(int id, String name, Position pos, InteractableItemActionType actionType, InteractableItemActionParameter actionParam, String mapName)
    {
        this.id = id;
        this.name = name;
        this.position = pos;
        this.actionType = actionType;
        this.actionParam = actionParam;
        this.mapName = mapName;
    }

    /**
     * Constructor for object without id.
     *
     * @param name        - name of the item
     * @param pos         - position of the item
     * @param actionType  - type: InteractableItemActionType
     * @param actionParam - type: InteractbleItemActionParameter
     * @param mapName     - which map the item is located on.
     */
    public InteractableItemDTO(String name, Position pos, InteractableItemActionType actionType, InteractableItemActionParameter actionParam, String mapName)
    {
        this.name = name;
        this.position = pos;
        this.actionType = actionType;
        this.actionParam = actionParam;
        this.mapName = mapName;
    }

    /**
     * Gets id
     *
     * @return id
     */
    public int getId()
    {
        return this.id;
    }

    /**
     * Sets id
     *
     * @param id - the id of this item
     */
    public void setId(int id)
    {
        this.id = id;
    }

    /**
     * Gets name
     *
     * @return name
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * Sets name
     *
     * @param name - the name of the item
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Gets position
     *
     * @return position
     */
    public Position getPosition()
    {
        return this.position;
    }

    /**
     * Sets position
     *
     * @param position - the position of the item
     */
    public void setPosition(Position position)
    {
        this.position = position;
    }

    /**
     * Gets map name
     *
     * @return mapName
     */
    public String getMapName()
    {
        return this.mapName;
    }

    /**
     * Sets map name
     *
     * @param mapName the map the item is located on.
     */
    public void setMapName(String mapName)
    {
        this.mapName = mapName;
    }

    /**
     * Get the actionType
     *
     * @return actionType
     */
    public InteractableItemActionType getActionType()
    {
        return actionType;
    }

    /**
     * Set the actionType
     *
     * @param actionType - Type: InteractableItemActionType
     */
    public void setActionType(InteractableItemActionType actionType)
    {
        this.actionType = actionType;
    }

    /**
     * Get the ActionParam
     *
     * @return actionParam
     */
    public InteractableItemActionParameter getActionParam()
    {
        return actionParam;
    }

    /**
     * Set the actionParam
     *
     * @param actionParam - type: InteractableItemActionParameter
     */
    public void setActionParam(InteractableItemActionParameter actionParam)
    {
        this.actionParam = actionParam;
    }

    /**
     * Override of toString method that shows name
     */
    @Override
    public String toString()
    {
        return this.name;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj)
    {
        InteractableItemDTO item = (InteractableItemDTO) obj;
        if ((item.getId() == this.getId()) &&
                (item.getActionParam().getClass().getCanonicalName().equals(this.getActionParam().getClass().getCanonicalName())) &&
                (item.getActionType().ordinal() == this.getActionType().ordinal()) &&
                (item.getMapName().equals(this.getMapName())) &&
                (item.getName().equals(this.getName())) &&
                (item.getPosition().equals(this.getPosition())))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
