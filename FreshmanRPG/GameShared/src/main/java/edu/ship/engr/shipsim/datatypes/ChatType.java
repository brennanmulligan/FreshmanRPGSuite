package edu.ship.engr.shipsim.datatypes;

/**
 * Types of chat messages that can be sent
 */
public enum ChatType
{
    /**
     * Chat has special style for system messages
     * null chat prefix, because client should not be able to send this type via the chat window.
     */
    System(null),
    /**
     * Chat is available to anyone in this area server
     */
    Zone("/z "),
    /**
     * Chat is only visible to nearby entities
     * if you add a new type, make sure that there is a space in the prefix string.
     */
    Local("/l "),
    /**
     * Chat sent to a specific friend
     */
    Private("/p "),
    /**
     * Chat is only visible in terminal window
     * null because we should be able to see if we're in terminal chat, otherwise commands won't happen.
     */
    Terminal(null);

    private String chatPrefix;

    ChatType(String prefix)
    {
        chatPrefix = prefix;
    }

    /**
     * @return the text which can precede a message to change the type of chat we are doing
     */
    public String getChatPrefix()
    {
        return chatPrefix;
    }
}