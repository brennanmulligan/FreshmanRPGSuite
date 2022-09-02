package edu.ship.engr.shipsim.communication;

import java.util.ArrayList;

public class ConnectionManagerList
{
    private static ConnectionManagerList singleton;
    private final ArrayList<ConnectionManager> list;

    private ConnectionManagerList()
    {
        list = new ArrayList<>();
    }

    public static ConnectionManagerList getSingleton()
    {
        if (singleton == null)
        {
            singleton = new ConnectionManagerList();
        }
        return singleton;
    }

    public void add(ConnectionManager x)
    {
        ConnectionManager existing = findManagerForPlayer(x.getPlayerID());
        if (existing != null)
        {
            existing.disconnectFromPackers();
            list.remove(existing);
        }
        list.add(x);
    }

    private ConnectionManager findManagerForPlayer(int playerID)
    {
        for (ConnectionManager x : list)
        {
            if (x.getPlayerID() == playerID)
            {
                return x;
            }
        }
        return null;
    }

    public void remove(ConnectionManager x)
    {
        list.remove(x);
    }
}
