package datasource;

import model.OptionsManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

public class TableDataGatewayManager
{

    private static TableDataGatewayManager singleton;
    private final HashMap<String, TableDataGateway> factorySingletons = new HashMap<>();

    public static TableDataGatewayManager getSingleton()
    {
        if (singleton == null)
        {
            singleton = new TableDataGatewayManager();
        }
        return singleton;
    }


     static void resetSingleton()
    {
        OptionsManager.getSingleton().assertTestMode();
        singleton = null;
    }

    public TableDataGateway getTableGateway(String tableName)
    {
        TableDataGateway result =
                factorySingletons.get(getKey(tableName));
        if (result != null)
        {
            return result;
        }
        TableDataGateway gateway = null;
        Method getGatewayMethod;
        try
        {
            if (OptionsManager.getSingleton().isUsingMockDataSource())
            {
                getGatewayMethod = Class.forName(
                                        "datasource." + tableName + "TableDataGatewayMock")
                        .getDeclaredMethod(
                        "getGateway");

            }
            else
            {
                getGatewayMethod = Class.forName(
                        "datasource." + tableName + "TableDataGatewayRDS").getDeclaredMethod(
                        "getGateway");
            }
            gateway =
                    (TableDataGateway) getGatewayMethod.invoke(null);
            //   gateway = gatewayClass.getConstructor().newInstance();
            factorySingletons.put(getKey(tableName), gateway);
        }
        catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e)
        {
            e.printStackTrace();
        }
        assert (gateway != null);
        return gateway;
    }

    private String getKey(String tableName)
    {
        return tableName + OptionsManager.getSingleton().isUsingMockDataSource();
    }

    public void resetTableGateway(String tableName)
    {
        TableDataGateway gateway = getTableGateway(tableName);
        assert (gateway != null);
        gateway.resetTableGateway();
    }
}
