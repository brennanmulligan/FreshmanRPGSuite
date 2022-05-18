package datasource;

import model.OptionsManager;

import java.lang.reflect.InvocationTargetException;
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

    private static void resetSingleton()
    {
        OptionsManager.getSingleton().assertTestMode();
        singleton = null;
    }

    public TableDataGateway getTableGateway(String tableName)
    {
        TableDataGateway result = factorySingletons.get(tableName);
        if (result != null)
        {
            return result;
        }
        TableDataGateway gateway = null;
        Class<TableDataGateway> gatewayClass;
        try
        {
            if (OptionsManager.getSingleton().isUsingMockDataSource())
            {
                //noinspection unchecked
                gatewayClass = (Class<TableDataGateway>) Class.forName(
                        "datasource." + tableName + "TableDataGatewayMock");
                gateway = gatewayClass.getConstructor().newInstance();
                factorySingletons.put(tableName,gateway);
            } else
            {
                //noinspection unchecked
                gatewayClass = (Class<TableDataGateway>) Class.forName(
                        "datasource." + tableName + "TableDataGatewayRDS");
                gateway = gatewayClass.getConstructor().newInstance();
                factorySingletons.put(tableName,gateway);
            }
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e)
        {
            e.printStackTrace();
        }
        assert(gateway != null);
        return gateway;
    }

    public void resetTableGateway(String tableName)
    {
        TableDataGateway gateway = getTableGateway(tableName);
        assert(gateway != null);
        gateway.resetTableGateway();
    }
}
